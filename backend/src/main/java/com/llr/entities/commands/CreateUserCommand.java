package com.llr.entities.commands;

import com.llr.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class CreateUserCommand {
    private String password;
    private String email;
    private String username;
    private String phone;
    private String gender;
    private String city;
    private String name;

    private CreateUserCommand(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public static CreateUserCommand build(String email) {
        return new CreateUserCommand(email, email, UUID.randomUUID().toString().replace("-", ""));
    }

    public User build() {
        return User.builder()
            .username(username)
            .password(password)
            .email(email)
            .phone(phone)
            .city(city)
            .gender(gender)
            .name(name)
            .build();
    }
}
