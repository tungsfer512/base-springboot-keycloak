package vn.base.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition
@Configuration
public class Swagger {

    private String devUrl = Env.OPENAPI_DEV_URL;
    private String prodUrl = Env.OPENAPI_PROD_URL;

    @Bean
    public OpenAPI myOpenAPI() {

        ApiResponse badRequest = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType().addExamples("default",
                                new Example().value(
                                        "{\"code\" : 400, \"status\" : \"Bad Request\", \"Message\" : \"Bad Request\"}"))));
        ApiResponse internalServerError = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType().addExamples("default",
                                new Example().value(
                                        "{\"code\" : 500, \"status\" : \"internalServerError\", \"Message\" : \"internalServerError\"}"))));
        ApiResponse successfulResponse = new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new MediaType().addExamples("default",
                                new Example().value("{\"name\":\"string\",\"surname\":\"string\",\"age\":0}"))));
        Components components = new Components();
        components.addResponses("badRequest", badRequest);
        components.addResponses("internalServerError", internalServerError);
        components.addResponses("successfulResponse", successfulResponse);

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
        return new OpenAPI().components(components).info(info).servers(null);
    }
}