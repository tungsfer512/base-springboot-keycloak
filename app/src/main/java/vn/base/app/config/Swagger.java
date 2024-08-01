package vn.base.app.config;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import vn.base.app.exception.EErorr;
import vn.base.app.utils.CustomResponse;

@OpenAPIDefinition
@Configuration
public class Swagger {

    private String devUrl = Env.OPENAPI_DEV_URL;
    private String prodUrl = Env.OPENAPI_PROD_URL;
    @Value("${base.app.keycloak.url}")
    String KEYCLOAK_SERVER_URL;
    @Value("${base.app.keycloak.realm}")
    String KEYCLOAK_REALM;

    private OAuthFlows newOAuthFlows() {
        String url = KEYCLOAK_SERVER_URL + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect";
        final var oauthFlow = new OAuthFlow()
                .authorizationUrl(url + "/auth")
                .refreshUrl(url + "/token")
                .tokenUrl(url + "/token")
                .scopes(new Scopes());
        return new OAuthFlows().authorizationCode(oauthFlow);
    }

    @Bean
    public OpenAPI myOpenAPI() {

        CustomResponse<Object> OK_RESPONSE_LIST = new CustomResponse<>(HttpStatus.OK, new ArrayList<>());
        CustomResponse<Object> OK_RESPONSE_OBJECT = new CustomResponse<>(HttpStatus.OK, new JSONObject());
        CustomResponse<Object> CREATED_RESPONSE = new CustomResponse<>(HttpStatus.CREATED, new JSONObject());
        CustomResponse<Object> BAD_REQUEST_RESPONSE = new CustomResponse<>(EErorr.BAD_REQUEST.getStatus(),
                EErorr.BAD_REQUEST.getMessage());
        CustomResponse<Object> FORBIDDEN_RESPONSE = new CustomResponse<>(EErorr.FORBIDDEN.getStatus(),
                EErorr.FORBIDDEN.getMessage());
        CustomResponse<Object> NOT_FOUND_RESPONSE = new CustomResponse<>(EErorr.NOT_FOUND.getStatus(),
                EErorr.NOT_FOUND.getMessage());
        CustomResponse<Object> CONFLICT_RESPONSE = new CustomResponse<>(EErorr.CONFLICT.getStatus(),
                EErorr.CONFLICT.getMessage());
        CustomResponse<Object> INTERNAL_SERVER_ERROR_RESPONSE = new CustomResponse<>(
                EErorr.INTERNAL_SERVER_ERROR.getStatus(), EErorr.INTERNAL_SERVER_ERROR.getMessage());

        ApiResponse OK_LIST = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(OK_RESPONSE_LIST.response()
                                                .getBody()))));
        ApiResponse OK_OBJECT = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(OK_RESPONSE_OBJECT.response()
                                                .getBody()))));
        ApiResponse CREATED = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(CREATED_RESPONSE.response()
                                                .getBody()))));
        ApiResponse NO_CONTENT = new ApiResponse().content(null);
        ApiResponse BAD_REQUEST = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(BAD_REQUEST_RESPONSE.response()
                                                .getBody()))));
        ApiResponse UNAUTHORIZED = new ApiResponse().content(null);
        ApiResponse FORBIDDEN = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(FORBIDDEN_RESPONSE.response()
                                                .getBody()))));
        ApiResponse NOT_FOUND = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(NOT_FOUND_RESPONSE.response()
                                                .getBody()))));
        ApiResponse CONFLICT = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(CONFLICT_RESPONSE.response()
                                                .getBody()))));
        ApiResponse INTERNAL_SERVER_ERROR = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType()
                                .addExamples("default", new Example()
                                        .value(INTERNAL_SERVER_ERROR_RESPONSE
                                                .response()
                                                .getBody()))));
        Components components = new Components();
        components.addResponses("OK_LIST", OK_LIST);
        components.addResponses("OK_OBJECT", OK_OBJECT);
        components.addResponses("CREATED", CREATED);
        components.addResponses("NO_CONTENT", NO_CONTENT);
        components.addResponses("UNAUTHORIZED", UNAUTHORIZED);
        components.addResponses("BAD_REQUEST", BAD_REQUEST);
        components.addResponses("FORBIDDEN", FORBIDDEN);
        components.addResponses("NOT_FOUND", NOT_FOUND);
        components.addResponses("CONFLICT", CONFLICT);
        components.addResponses("INTERNAL_SERVER_ERROR", INTERNAL_SERVER_ERROR);

        components.addSecuritySchemes("X-API-Key", new SecurityScheme()
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY)
                .scheme("X-API-Key")
                .name("X-API-Key"));
        components.addSecuritySchemes("OAuth2", new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2).flows(newOAuthFlows()));

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        Contact contact = new Contact();
        contact.setEmail("ript@ript.vn");
        contact.setName("RIPT");
        contact.setUrl("https://ript.vn");
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        Info info = new Info()
                .title("RIPT Base API Swagger")
                .version("1.0")
                .contact(contact)
                .description("This Swagger exposes endpoints to manage RIPT Base API.")
                .license(mitLicense);
        // return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("X-API-Key"))
                .addSecurityItem(new SecurityRequirement().addList("OAuth2"))
                .components(components)
                .info(info)
                .servers(null);
    }
}