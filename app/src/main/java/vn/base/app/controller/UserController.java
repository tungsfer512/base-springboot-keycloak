package vn.base.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status.Family;
import vn.base.app.controller.base.BaseController;
import vn.base.app.exception.CustomException;
import vn.base.app.exception.EErorr;
import vn.base.app.model.User;
import vn.base.app.service.UserService;
import vn.base.app.utils.ControllerUtils;
import vn.base.app.utils.Utils;

@RestController
@RequestMapping("/api/internal/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "[User]", description = "User API")
public class UserController extends BaseController<User> {

    @Value("${base.app.keycloak.realm}")
    String KEYCLOAK_REALM;

    @Autowired
    private Keycloak keycloak;

    @Override
    @Deprecated
    public ResponseEntity<Object> add(HttpServletRequest request, @Valid User entity) {
        try {
            List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(entity.getPassword());
            credentialRepresentations.add(passwordCred);
            UserRepresentation newUser = new UserRepresentation();
            newUser.setEnabled(true);
            newUser.setUsername(entity.getUsername());
            newUser.setFirstName(entity.getFirstName());
            newUser.setLastName(entity.getLastName());
            newUser.setEmail(entity.getEmail());
            newUser.setCredentials(credentialRepresentations);
            newUser.setClientRoles(null);
            newUser.setRealmRoles(null);
            Response response = keycloak.realm(KEYCLOAK_REALM).users().create(newUser);
            if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
                return super.add(request, entity);
            }
            throw new CustomException(response.getStatusInfo().getStatusCode(),
                    response.getStatusInfo().getReasonPhrase());
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> getAll(HttpServletRequest request, List<String> sorts) {
        try {
            List<UserRepresentation> users = keycloak.realm(KEYCLOAK_REALM).users().list();
            List<User> localUsers = super.getService().findAll();
            List<String> localUserKeycloakIds = localUsers.stream().map(user -> user.getKeycloakId()).toList();
            users.forEach(user -> {
                if (!localUserKeycloakIds.contains(user.getId())) {
                    User newUser = new User();
                    newUser.setKeycloakId(user.getId());
                    newUser.setUsername(user.getUsername());
                    newUser.setEmail(user.getEmail());
                    newUser.setNickname(user.getUsername());
                    newUser.setFirstName(user.getFirstName());
                    newUser.setLastName(user.getLastName());
                    newUser.setFullName(user.getLastName() + " " + user.getFirstName());
                    newUser.setCreatedAt(user.getCreatedTimestamp());
                    newUser.setCreatedAt(user.getCreatedTimestamp());
                    super.getService().save(newUser);
                }
            });
            return super.getAll(request, sorts);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> get(HttpServletRequest request, int page, int pageSize, Map<String, Object> values,
            List<String> sorts) {
        try {
            List<UserRepresentation> users = keycloak.realm(KEYCLOAK_REALM).users().list();
            List<User> localUsers = super.getService().findAll();
            List<String> localUserKeycloakIds = localUsers.stream().map(user -> user.getKeycloakId()).toList();
            users.forEach(user -> {
                if (!localUserKeycloakIds.contains(user.getId())) {
                    User newUser = new User();
                    newUser.setKeycloakId(user.getId());
                    newUser.setUsername(user.getUsername());
                    newUser.setEmail(user.getEmail());
                    newUser.setNickname(user.getUsername());
                    newUser.setFirstName(user.getFirstName());
                    newUser.setLastName(user.getLastName());
                    newUser.setFullName(user.getLastName() + " " + user.getFirstName());
                    newUser.setCreatedAt(user.getCreatedTimestamp());
                    newUser.setCreatedAt(user.getCreatedTimestamp());
                    super.getService().save(newUser);
                }
            });
            return super.get(request, page, pageSize, values, sorts);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> getById(HttpServletRequest request, Long id) {
        try {
            return super.getById(request, id);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> deleteById(HttpServletRequest request, Long id) {
        try {
            User user = super.getService().findById(id);
            Response response = keycloak.realm(KEYCLOAK_REALM).users().delete(user.getKeycloakId());
            if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
                return super.deleteById(request, id);
            }
            throw new CustomException(response.getStatusInfo().getStatusCode(),
                    response.getStatusInfo().getReasonPhrase());
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> fullUpdateById(HttpServletRequest request, Long id, @Valid User entity) {
        try {
            User user = super.getService().findById(id);
            UserResource userResource = keycloak.realm(KEYCLOAK_REALM).users().get(user.getKeycloakId());
            UserRepresentation userRepresentation = userResource.toRepresentation();
            List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(entity.getPassword());
            credentialRepresentations.add(passwordCred);
            userRepresentation.setEnabled(true);
            userRepresentation.setFirstName(entity.getFirstName());
            userRepresentation.setLastName(entity.getLastName());
            userRepresentation.setEmail(entity.getEmail());
            userRepresentation.setCredentials(credentialRepresentations);
            userResource.update(userRepresentation);
            return super.fullUpdateById(request, id, entity);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Deprecated
    public ResponseEntity<Object> partialUpdateById(HttpServletRequest request, Long id, User entity) {
        try {
            User user = super.getService().findById(id);
            UserResource userResource = keycloak.realm(KEYCLOAK_REALM).users().get(user.getKeycloakId());
            UserRepresentation userRepresentation = userResource.toRepresentation();
            if (entity.getPassword() != null) {
                List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(entity.getPassword());
                credentialRepresentations.add(passwordCred);
                userRepresentation.setCredentials(credentialRepresentations);
            }
            if (entity.getFirstName() != null) {
                userRepresentation.setFirstName(entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                userRepresentation.setLastName(entity.getLastName());
            }
            userResource.update(userRepresentation);
            return super.partialUpdateById(request, id, entity);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", ref = "OK_OBJECT"),
            @ApiResponse(responseCode = "400", ref = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", ref = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", ref = "FORBIDDEN"),
            @ApiResponse(responseCode = "500", ref = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("me")
    @Deprecated
    public ResponseEntity<Object> getMe(HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        try {
            String id = jwt.getClaim("sub");
            User user = ((UserService) super.getService()).findbyKeycloakId(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(KEYCLOAK_REALM).users().get(id).toRepresentation();
                user = new User();
                user.setKeycloakId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " + keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp());
                super.getService().save(user);
            }
            User resUser = ((UserService) super.getService()).findbyKeycloakId(id);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(resUser);
            return ControllerUtils.response(HttpStatus.OK, jsonData).response();
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", ref = "OK_LIST"),
            @ApiResponse(responseCode = "400", ref = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", ref = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", ref = "FORBIDDEN"),
            @ApiResponse(responseCode = "500", ref = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("me/permissions")
    @Deprecated
    public ResponseEntity<Object> getMePermissions(HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        try {
            String id = jwt.getClaim("sub");
            User user = ((UserService) super.getService()).findbyKeycloakId(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(KEYCLOAK_REALM).users().get(id).toRepresentation();
                user = new User();
                user.setKeycloakId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " + keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp());
                super.getService().save(user);
            }
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(jwt.getClaims());
            return ControllerUtils.response(HttpStatus.OK, jsonData).response();
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", ref = "OK_OBJECT"),
            @ApiResponse(responseCode = "400", ref = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", ref = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", ref = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", ref = "NOT_FOUND"),
            @ApiResponse(responseCode = "500", ref = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("me")
    @Deprecated
    public ResponseEntity<Object> fullUpdateMe(
            HttpServletRequest request,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid User entity) {
        try {
            String id = jwt.getClaim("sub");
            User user = ((UserService) super.getService()).findbyKeycloakId(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(KEYCLOAK_REALM).users().get(id).toRepresentation();
                user = new User();
                user.setKeycloakId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " + keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp());
                super.getService().save(user);
            }
            User resUser = ((UserService) super.getService()).findbyKeycloakId(id);
            return super.fullUpdateById(request, resUser.getId(), entity);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", ref = "OK_OBJECT"),
            @ApiResponse(responseCode = "400", ref = "BAD_REQUEST"),
            @ApiResponse(responseCode = "401", ref = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", ref = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", ref = "NOT_FOUND"),
            @ApiResponse(responseCode = "500", ref = "INTERNAL_SERVER_ERROR")
    })
    @PatchMapping("me")
    @Deprecated
    public ResponseEntity<Object> partialUpdateMe(
            HttpServletRequest request,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody(required = false) User entity) {
        try {
            String id = jwt.getClaim("sub");
            User user = ((UserService) super.getService()).findbyKeycloakId(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(KEYCLOAK_REALM).users().get(id).toRepresentation();
                user = new User();
                user.setKeycloakId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " + keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp());
                super.getService().save(user);
            }
            User resUser = ((UserService) super.getService()).findbyKeycloakId(id);
            return super.partialUpdateById(request, resUser.getId(), entity);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

}
