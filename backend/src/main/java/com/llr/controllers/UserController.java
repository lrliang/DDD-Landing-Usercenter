package com.llr.controllers;

import com.llr.entities.User;
import com.llr.entities.commands.CreateUserCommand;
import com.llr.exceptions.BusinessException;
import com.llr.services.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserCenterService userCenterService;

    @Autowired
    public UserController(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @GetMapping("/users/token")
    public Object getUserByToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("token");
        return userCenterService.findUserByToken(tokenHeader);
    }

    @GetMapping(value = "/users", params = {"email"})
    public Object getUserByEmail(@RequestParam("email") String email) {
        return userCenterService.findUserByEmail(email);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getUserById(@PathVariable Long userId) throws BusinessException {
        return ResponseEntity.ok(userCenterService.getUserById(userId));
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody CreateUserCommand createUserCommand) throws BusinessException {
        User user = userCenterService.createUser(createUserCommand);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }

    @PostMapping("/users/token/{userId}")
    public ResponseEntity generateToken(@PathVariable Long userId) throws BusinessException, UnsupportedEncodingException {
        String token = userCenterService.generateToken(userId);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity sendGenerateToken(@RequestBody User user) throws UnsupportedEncodingException {
        try{
            HashMap<String,Object> response = userCenterService.sendGenerateToken(user.getEmail(), user.getPassword());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/password/{userId}")
    public ResponseEntity updatePassword(@PathVariable Long userId, @RequestBody Map passwordMap) throws BusinessException {

        return new ResponseEntity<>(
                userCenterService.authUserOldPasswordAndUpdatePassword(userId,
                        passwordMap.get("oldPassword").toString(),
                        passwordMap.get("newPassword").toString()),
                HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity updateUserById(@PathVariable Long userId, @RequestBody User user) throws BusinessException, UnsupportedEncodingException {
        return new ResponseEntity<>(userCenterService.updateUserById(userId, user), HttpStatus.OK);
    }

    @PutMapping("/users/email/{userId}")
    public ResponseEntity updateEmail(@PathVariable Long userId, @RequestBody Map emailMap) throws BusinessException {
        return new ResponseEntity<>(userCenterService.sendUpdateEmail(userId, emailMap.get("email").toString()), HttpStatus.OK);
    }
    @GetMapping("/users/email/{userId}/{email}")
    public ResponseEntity sureUpdateEmail(@PathVariable Long userId,@PathVariable String email)throws BusinessException{
        return new ResponseEntity<>(userCenterService.updateUserEmail(userId,email),HttpStatus.NO_CONTENT);
    }

    @PostMapping("/users/emails")
    public ResponseEntity createUsers(@RequestParam String emails) throws BusinessException {
        return new ResponseEntity(userCenterService.createUsersByEmails(emails), HttpStatus.CREATED);
    }

    @GetMapping("/users/emails")
    public ResponseEntity getUsersByEmails(@RequestParam String emails) {
        return new ResponseEntity(userCenterService.getUsersByEmails(emails), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/roles")
    public ResponseEntity updateUserRoles(
        @PathVariable Long userId,
        @RequestBody User user) throws BusinessException {
        userCenterService.updateUserRoles(userId, user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/users",params = "emails")
    public ResponseEntity searchUser(@RequestParam String emails) {
        return ResponseEntity.ok(userCenterService.findUserByEmails(emails));
    }

}
