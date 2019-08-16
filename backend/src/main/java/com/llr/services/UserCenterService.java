package com.llr.services;

import com.llr.entities.User;
import com.llr.entities.commands.CreateUserCommand;
import com.llr.exceptions.BusinessException;
import com.llr.repositories.UserRepository;
import com.llr.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserCenterService {

    private final UserRepository userRepository;
    private final SendCloudEmailService sendCloudEmailService;


    @Autowired
    public UserCenterService(UserRepository userRepository, SendCloudEmailService sendCloudEmailService) {
        this.userRepository = userRepository;
        this.sendCloudEmailService = sendCloudEmailService;
    }

    public User getUserById(Long userId) throws BusinessException {
        User user = findUserOrThrow(userId);
        user.hidePassword();
        return user;
    }

    public User authUserOldPasswordAndUpdatePassword(Long userId, String oldPassword, String newPassword) throws BusinessException {
        User foundUser = userRepository.findByIdAndPassword(userId, oldPassword).orElseThrow(
            () -> new BusinessException("旧密码错误！")
        );
        foundUser.updatePassword(newPassword);
        return userRepository.save(foundUser);
    }

    public HashMap<String, Object> updateUserById(Long userId, User user) throws BusinessException, UnsupportedEncodingException {
        User foundUser = userRepository.findById(userId).orElseThrow(
            () -> new BusinessException("Current user is not exist.")
        );
        foundUser.update(user);
        User userNew = userRepository.save(foundUser);
        userNew.hidePassword();
        return new HashMap<String, Object>() {{
            put("token", JwtUtil.build(userNew));
            put("user", userNew);
        }};
    }

    public Boolean sendUpdateEmail(Long userId, String email) throws BusinessException {
        return sendCloudEmailService.sendUpdateEmail(userId, email);
    }

    public User updateUserEmail(Long userId, String email) throws BusinessException {
        User foundUser = userRepository.findById(userId).orElseThrow(
            () -> new BusinessException("Current user is not exist.")
        );
        foundUser.updateEmail(email);
        return userRepository.save(foundUser);
    }

    private User findUserOrThrow(Long userId) throws BusinessException {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("该用户不存在"));
    }

    private User findUserByEmailAndPasswordOrThrow(String email, String password) throws BusinessException {
        return userRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new BusinessException("账户或密码错误"));
    }

    public User createUser(CreateUserCommand createUserCommand) throws BusinessException {
        User result = userRepository.findByEmail(createUserCommand.getEmail());
        if (ObjectUtils.isEmpty(result)) {
            result = userRepository.save(createUserCommand.build());
            sendCloudEmailService.sendSecreteInfo(
                createUserCommand.getUsername(),
                createUserCommand.getEmail(),
                createUserCommand.getPassword()
            );
        }
        return result;
    }

    public String generateToken(Long userId) throws BusinessException, UnsupportedEncodingException {
        User user = findUserOrThrow(userId);
        user.hidePassword();
        return JwtUtil.build(user);
    }

    public Object findUserByToken(String token) {
        return JwtUtil.parseTokenToMap(token);
    }

    public HashMap<String, Object> sendGenerateToken(String email, String password) throws BusinessException, UnsupportedEncodingException {
        User user = findUserByEmailAndPasswordOrThrow(email, password);
        if (user != null) {
            user.hidePassword();
            return new HashMap<String, Object>() {{
                put("token", JwtUtil.build(user));
                put("user", user);
            }};
        } else {
            return null;
        }
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (!ObjectUtils.isEmpty(user)) {
            user.hidePassword();
        }
        return user;
    }

    public List<String> createUsersByEmails(String emailsStr) throws BusinessException {
        List<String> emails = Arrays.asList(emailsStr.split(","));
        List<User> foundUsers = userRepository.findUserByEmailIn(emails);
        List<String> needCreateEmails = getNeedCreateEmails(emails, foundUsers);
        for (String email : needCreateEmails) {
            createUser(CreateUserCommand.build(email));
        }
        return foundUsers.stream().map(User::getEmail).collect(Collectors.toList());
    }

    private List<String> getNeedCreateEmails(List<String> emails, List<User> foundUsers) {
        List<String> usersEmail = foundUsers.stream().map(User::getEmail).collect(Collectors.toList());
        return emails.stream()
            .filter(email -> !usersEmail.contains(email))
            .collect(Collectors.toList());
    }

    public List<User> getUsersByEmails(String emailsStr) {
        if ("".equals(emailsStr)) {
            return userRepository.findAll().stream()
                .map(User::hidePassword)
                .collect(Collectors.toList());
        }
        List<String> emails = Arrays.asList(emailsStr.split(","));
        return userRepository.findUserByEmailIn(emails)
            .stream().map(User::hidePassword)
            .collect(Collectors.toList());
    }

    public void updateUserRoles(Long userId, User user) throws BusinessException {
        User foundUser = findUserOrThrow(userId);
        foundUser.updateRoles(user.getRoles());
        userRepository.save(foundUser);
    }

    public List<User> findUserByEmails(String emails) {
        return userRepository.findUserByEmailIn(Arrays.asList(emails.split(",")));
    }
}
