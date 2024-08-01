package vn.base.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private String email;
    private String nickname;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String phone;
    private Long birthDate;
    private String address;
    private String idCard;
    private Long idCardDate;
    private String idCardPlace;

}
