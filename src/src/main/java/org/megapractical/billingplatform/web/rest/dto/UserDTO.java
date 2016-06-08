package org.megapractical.billingplatform.web.rest.dto;

import org.joda.time.DateTime;
import org.megapractical.billingplatform.domain.Authority;
import org.megapractical.billingplatform.domain.User;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    //@Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?")
    @Size(min = 12, max = 13)
    private String rfc;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String first_surname;

    @Size(max = 50)
    private String second_surname;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    //@Pattern(regexp = "^[0-9]{1,15}")
    @Size(min = 1, max = 15)
    private String phone;

    @NotNull
    private LocalDate date_born;

    @NotNull
    private String gender;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(),user.getRFC(), null, user.getName(), user.getFirstSurname(),
            user.getSecondSurname(), user.getEmail(),user.getPhone(),user.getDate_born(),
            user.getGender(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String login, String rfc, String password, String name, String first_surname,
        String second_surname, String email,String phone,LocalDate date_born, String gender, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.password = password;
        this.rfc = rfc;
        this.name = name;
        this.first_surname = first_surname;
        this.second_surname = second_surname;
        this.email = email;
        this.phone = phone;
        this.date_born = date_born;
        this.gender = gender;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getRFC() {
        return rfc;
    }

    public void setRFC(String RFC) {
        this.rfc = RFC;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getFirstSurname() {
        return first_surname;
    }

    public void setFirstSurname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getSecondSurname() {
        return second_surname;
    }

    public void setSecondSurname(String second_surname) {
        this.second_surname = second_surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDate_born() {
        return date_born;
    }

    public void setDate_born(LocalDate date_born) {
        this.date_born = date_born;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            ", firstSurname='" + first_surname + '\'' +
            ", secondSurname='" + second_surname + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", dateBorn='" + date_born + '\'' +
            ", gender='" + gender + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
