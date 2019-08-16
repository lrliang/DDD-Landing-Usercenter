package com.llr.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("phone")
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    @JsonProperty("city")
    private String city;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("name")
    private String name;

    public void update(User organization) {
        this.city = organization.getCity();
        this.gender = organization.getGender();
        this.name = organization.getName();
        this.phone = organization.getPhone();
        this.username = organization.getUsername();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRole",
        joinColumns = {@JoinColumn(name = "userId")},
        inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roles;

    public User hidePassword() {
        this.password = "";
        return this;
    }

    public void updateRoles(List<Role> roles) {
        this.roles = roles;
    }
}
