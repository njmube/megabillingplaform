package org.megapractical.billingplatform.web.rest.dto;


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
    @Pattern(regexp = "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?")
    @Size(min = 12, max = 13)
    private String rfc;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    private String firtsurname;

    @NotNull
    @Size(min = 3, max = 50)
    private String secondsurname;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Pattern(regexp = "^[0-9]{1,15}")
    @Size(min = 1, max = 15)
    private String phone;

    @NotNull
    private String gender;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(),user.getRFC(), null, user.getName(), user.getFirtsurname(),user.getSecondsurname(),
            user.getEmail(),user.getPhone(),
            user.getGender(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String login, String rfc, String password, String name,String firtsurname,String secondsurname,
                   String email, String phone, String gender, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.password = password;
        this.rfc = rfc;
        this.name = name;
        this.firtsurname = firtsurname;
        this.secondsurname = secondsurname;
        this.email = email;
        this.phone = phone;
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

    public String getFirtsurname() {
        return firtsurname;
    }

    public void setFirtsurname(String firtsurname) {
        this.firtsurname = firtsurname;
    }

    public String getSecondsurname() {
        return secondsurname;
    }

    public void setSecondsurname(String secondsurname) {
        this.secondsurname = secondsurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
            ", firtsurname='" + firtsurname + '\'' +
            ", secondsurname='" + secondsurname + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", gender='" + gender + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
