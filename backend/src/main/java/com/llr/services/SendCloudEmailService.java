package com.llr.services;

import com.llr.exceptions.BusinessException;
import com.llr.vendor.SendCloudEmailClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendCloudEmailService {

    @Autowired
    SendCloudEmailClient sendCloudEmailClient;

    @Value("${server.port}")
    private String serverPort;

    @Value("${app.domain}")
    private String address;

    public void sendSecreteInfo(String name, String email, String password) throws BusinessException {
        String content = String.format("{\n" +
                "    \"to\": [\"%s\"],\n" +
                "    \"sub\":\n" +
                "    {\n" +
                "        \"%%userName%%\": [\"%s\"],\n" +
                "        \"%%userEmail%%\": [\"%s\"],\n" +
                "        \"%%userPassword%%\":[\"%s\"]\n" +
                "    }\n" +
                "}", email, name, email, password);
        sendCloudEmailClient.sendTemplateEmail(content, EmailTemplate.USER_SECRETE_INFO.name);
    }

    public void sendAdmissionNotice(String email, String className, String classRoomUrl) throws BusinessException {
        String content = String.format("{\n" +
                "    \"to\": [\"%s\"],\n" +
                "    \"sub\":\n" +
                "    {\n" +
                "        \"%%userEmail%%\": [\"%s\"],\n" +
                "        \"%%className%%\": [\"%s\"],\n" +
                "        \"%%url%%\":[\"%s\"]\n" +
                "    }\n" +
                "}", email, email, className, classRoomUrl);
        sendCloudEmailClient.sendTemplateEmail(content, EmailTemplate.ADMISSION_NOTICE.name);
    }


    public Boolean sendUpdateEmail(Long userId, String email) throws BusinessException {

        String emailLink = address + "/api/users/email/" + userId + "/" + email;
        String content = String.format("{\n" +
                "    \"to\": [\"%s\"],\n" +
                "    \"sub\":\n" +
                "    {\n" +
                "        \"%%Link%%\": [\"%s\"],\n" +
                "    }\n" +
                "}", email, emailLink);
        return sendCloudEmailClient.sendTemplateEmail(content, "user_update_email");
    }

    @Getter
    private enum EmailTemplate {
        USER_SECRETE_INFO("user_secrete_info"),
        ADMISSION_NOTICE("student_admission_notice");

        private final String name;

        EmailTemplate(String name) {
            this.name = name;
        }
    }
}

