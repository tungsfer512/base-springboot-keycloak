package vn.base.app.controller;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import vn.base.app.utils.ControllerUtils;

@RestController
@RequestMapping("api")
@Tag(name = "Test", description = "Test API")
public class TestController {

    @Operation(summary = "Test API", description = "Test API")
    @GetMapping("public/test")
    public ResponseEntity<Object> test() {
        Function<String, String> callbackSuccess = null;
        Function<String, String> callbackFail = null;
        return ControllerUtils.testResponseForCallback(callbackSuccess, callbackFail).response();
    }

    @Operation(summary = "Test API no calback", description = "Test API with no callback action executed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("public/test/no-callback")
    public ResponseEntity<Object> testNoCallback() {
        Function<String, String> callbackSuccess = null;
        Function<String, String> callbackFail = null;
        return ControllerUtils.testResponseForCallback(callbackSuccess, callbackFail).response();
    }

    @Operation(summary = "Test API calback", description = "Test API with callback success and fail actions executed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("public/test/callback")
    public ResponseEntity<Object> testCallback() {
        Function<String, String> callbackSuccess = (arg) -> {
            return arg;
        };
        Function<String, String> callbackFail = (arg) -> {
            return arg;
        };
        return ControllerUtils.testResponseForCallback(callbackSuccess, callbackFail).response();
    }

    @Operation(summary = "Test API calback success", description = "Test API with callback success action executed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("public/test/callback-success")
    public ResponseEntity<Object> testCallbackSuccess() {
        Function<String, String> callbackSuccess = (arg) -> {
            return arg;
        };
        Function<String, String> callbackFail = null;
        return ControllerUtils.testResponseForCallback(callbackSuccess, callbackFail).response();
    }

    @Operation(summary = "Test API calback fail", description = "Test API with callback fail action executed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("public/test/callback-fail")
    public ResponseEntity<Object> testCallbackFail() {
        Function<String, String> callbackSuccess = null;
        Function<String, String> callbackFail = (arg) -> {
            return arg;
        };
        return ControllerUtils.testResponseForCallback(callbackSuccess, callbackFail).response();
    }

    @Operation(summary = "Test API no auth", description = "Test API with no authentication and authorization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("public/test/no-auth")
    public ResponseEntity<Object> testNoAuth() {
        return ControllerUtils.response(HttpStatus.OK, "Test No Auth").response();
    }

    @Operation(summary = "Test API auth", description = "Test API with authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("internal/test/auth")
    public ResponseEntity<Object> testAuth() {
        return ControllerUtils.response(HttpStatus.OK, "Test Auth").response();
    }

    @Operation(summary = "Test API auth user", description = "Test API with authentication and authorization role USER")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("internal/test/auth-user")
    public ResponseEntity<Object> testAuthUser() {
        return ControllerUtils.response(HttpStatus.OK, "Test Auth User").response();
    }

    @Operation(summary = "Test API auth admin", description = "Test API with authentication and authorization role ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject()) }) })
    @GetMapping("internal/test/auth-admin")
    public ResponseEntity<Object> testAuthAdmin() {
        return ControllerUtils.response(HttpStatus.OK, "Test Auth Admin").response();
    }

}
