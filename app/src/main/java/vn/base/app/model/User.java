package vn.base.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.base.app.model.base.BaseModel;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class User extends BaseModel {

    private String keycloakId;
    private String username;
    @Email(message = "Invalid email")
    private String email;
    private String nickname;
    @Transient
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", flags = Flag.UNICODE_CASE, message = "Password must contains at least one digit, one UPPERCASE letter, one lowercase letter, one special character, no whitespace and have at least 8 characters")
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    @Digits(fraction = 0, integer = 10, message = "Phone number must be valid and have at least 10 digits")
    private String phone;
    private Long birthDate;
    private String address;
    private String idCard;
    private Long idCardDate;
    private String idCardPlace;

}
