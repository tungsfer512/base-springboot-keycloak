package vn.base.app.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {

    public static CustomResponse<Object> testResponseForCallback(
            @Nullable Function<String, String> callbackSuccess,
            @Nullable Function<String, String> callbackFail) {
        List<String> messages = new ArrayList<>();
        if (callbackSuccess != null) {
            String tmp = callbackSuccess.apply("Callback Success OK");
            messages.add(tmp);
        }
        if (callbackFail != null) {
            String tmp = callbackFail.apply("Callback Fail OK");
            messages.add(tmp);
        }
        if (messages.size() > 0) {
            CustomResponse<Object> response = new CustomResponse<>(HttpStatus.OK, messages);
            return response;
        }
        CustomResponse<Object> response = new CustomResponse<>(HttpStatus.OK, "No Callback Function Was Executed");
        return response;
    }

    public static CustomResponse<Object> requestToExternal(
            HttpServletRequest request,
            @Nullable Function<HttpResponse, CustomResponse<Object>> callbackSuccess,
            @Nullable Function<HttpResponse, CustomResponse<Object>> callbackFail)
            throws Exception {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String url = request.getRequestURI();
        List<Header> headers = Utils.GET_HEADERS_FROM_REQUEST(request);
        Map<String, String[]> params = Utils.GET_PARAMS_FROM_REQUEST(request);
        HttpEntity body = Utils.GET_BODY_FROM_REQUEST(request);
        CustomHttpClientRequest httpRequest = new CustomHttpClientRequest(method, url, headers, params);
        HttpResponse httpResponse = null;
        if (body != null) {
            httpResponse = httpRequest.request(body);
        } else {
            httpResponse = httpRequest.request();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        ContentType contentType = ContentType.get(httpEntity);
        Boolean responseTypeFile = false;
        if (contentType != null &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.APPLICATION_JSON.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_PLAIN.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_HTML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_XML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.DEFAULT_TEXT.getMimeType().toLowerCase())) {
            responseTypeFile = true;
        }
        String jsonResponse = null;
        ByteArrayResource byteArrayResource = null;
        CustomResponse<Object> response = null;
        HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
        if (callbackSuccess != null && status.is2xxSuccessful()) {
            return callbackSuccess.apply(httpResponse);
        }
        if (callbackFail != null && !status.is2xxSuccessful()) {
            return callbackFail.apply(httpResponse);
        }
        if (httpEntity != null) {
            if (responseTypeFile) {
                CustomLogger.info("RESPONSE FILE");
                byteArrayResource = new ByteArrayResource(httpEntity.getContent().readAllBytes());
                response = new CustomResponse<>(status, byteArrayResource);
            } else {
                CustomLogger.info("RESPONSE JSON/TEXT");
                jsonResponse = EntityUtils.toString(httpEntity);
                response = new CustomResponse<>(status, jsonResponse);
            }
        } else {
            response = new CustomResponse<>(status);
        }
        return response;
    }

    public static CustomResponse<Object> requestToExternal(
            HttpMethod method,
            String url,
            List<Header> headers,
            Map<String, String[]> params,
            HttpEntity body,
            @Nullable Function<HttpResponse, CustomResponse<Object>> callbackSuccess,
            @Nullable Function<HttpResponse, CustomResponse<Object>> callbackFail)
            throws Exception {
        CustomHttpClientRequest httpRequest = new CustomHttpClientRequest(method, url, headers, params);
        HttpResponse httpResponse = null;
        if (body != null) {
            httpResponse = httpRequest.request(body);
        } else {
            httpResponse = httpRequest.request();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        ContentType contentType = ContentType.get(httpEntity);
        Boolean responseTypeFile = false;
        if (contentType != null &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.APPLICATION_JSON.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_PLAIN.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_HTML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_XML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.DEFAULT_TEXT.getMimeType().toLowerCase())) {
            responseTypeFile = true;
        }
        String jsonResponse = null;
        ByteArrayResource byteArrayResource = null;
        CustomResponse<Object> response = null;
        HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
        if (callbackSuccess != null && status.is2xxSuccessful()) {
            return callbackSuccess.apply(httpResponse);
        }
        if (callbackFail != null && !status.is2xxSuccessful()) {
            return callbackFail.apply(httpResponse);
        }
        if (httpEntity != null) {
            if (responseTypeFile) {
                CustomLogger.info("RESPONSE FILE");
                byteArrayResource = new ByteArrayResource(httpEntity.getContent().readAllBytes());
                response = new CustomResponse<>(status, byteArrayResource);
            } else {
                CustomLogger.info("RESPONSE JSON/TEXT");
                jsonResponse = EntityUtils.toString(httpEntity);
                response = new CustomResponse<>(status, jsonResponse);
            }
        } else {
            response = new CustomResponse<>(status);
        }
        return response;
    }

    public static CustomResponse<Object> response(HttpResponse httpResponse, @Nullable JSONObject metadata)
            throws Exception {
        HttpEntity httpEntity = httpResponse.getEntity();
        ContentType contentType = ContentType.get(httpEntity);
        Boolean responseTypeFile = false;
        if (contentType != null &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.APPLICATION_JSON.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_PLAIN.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_HTML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_XML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.DEFAULT_TEXT.getMimeType().toLowerCase())) {
            responseTypeFile = true;
        }
        String jsonResponse = null;
        ByteArrayResource byteArrayResource = null;
        CustomResponse<Object> response = null;
        HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
        if (httpEntity != null) {
            if (responseTypeFile) {
                CustomLogger.info("RESPONSE FILE");
                byteArrayResource = new ByteArrayResource(httpEntity.getContent().readAllBytes());
                response = new CustomResponse<>(status, byteArrayResource);
            } else {
                CustomLogger.info("RESPONSE JSON/TEXT");
                jsonResponse = EntityUtils.toString(httpEntity);
                response = new CustomResponse<>(status, jsonResponse, metadata);
            }
        } else {
            response = new CustomResponse<>(status, null, metadata);
        }
        return response;
    }

    public static CustomResponse<Object> response(HttpResponse httpResponse)
            throws Exception {
        HttpEntity httpEntity = httpResponse.getEntity();
        ContentType contentType = ContentType.get(httpEntity);
        Boolean responseTypeFile = false;
        if (contentType != null &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.APPLICATION_JSON.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_PLAIN.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_HTML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.TEXT_XML.getMimeType().toLowerCase())
                &&
                !contentType.getMimeType().toLowerCase()
                        .contains(ContentType.DEFAULT_TEXT.getMimeType().toLowerCase())) {
            responseTypeFile = true;
        }
        String jsonResponse = null;
        ByteArrayResource byteArrayResource = null;
        CustomResponse<Object> response = null;
        HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
        if (httpEntity != null) {
            if (responseTypeFile) {
                CustomLogger.info("RESPONSE FILE");
                byteArrayResource = new ByteArrayResource(httpEntity.getContent().readAllBytes());
                response = new CustomResponse<>(status, byteArrayResource);
            } else {
                CustomLogger.info("RESPONSE JSON/TEXT");
                jsonResponse = EntityUtils.toString(httpEntity);
                response = new CustomResponse<>(status, jsonResponse);
            }
        } else {
            response = new CustomResponse<>(status);
        }
        return response;
    }

    public static CustomResponse<Object> response(HttpStatus status) {
        return new CustomResponse<>(status);
    }

    public static CustomResponse<Object> response(int statusCode) {
        HttpStatus status = HttpStatus.valueOf(statusCode);
        return new CustomResponse<>(status);
    }

    public static CustomResponse<Object> response(HttpStatus status, @Nullable Object obj) {
        if (obj != null) {
            return new CustomResponse<>(status, obj.toString());
        } else {
            return new CustomResponse<>(status);
        }
    }

    public static CustomResponse<Object> response(int statusCode, @Nullable Object obj) {
        HttpStatus status = HttpStatus.valueOf(statusCode);
        if (obj != null) {
            return new CustomResponse<>(status, obj.toString());
        } else {
            return new CustomResponse<>(status);
        }
    }

    public static CustomResponse<Object> response(HttpStatus status, @Nullable Object obj,
            @Nullable JSONObject metadata) {
        if (obj != null) {
            if (metadata != null) {
                return new CustomResponse<>(status, obj.toString(), metadata);
            } else {
                return new CustomResponse<>(status, obj.toString());
            }
        } else {
            return new CustomResponse<>(status);
        }
    }

    public static CustomResponse<Object> response(int statusCode, @Nullable Object obj,
            @Nullable JSONObject metadata) {
        HttpStatus status = HttpStatus.valueOf(statusCode);
        if (obj != null) {
            if (metadata != null) {
                return new CustomResponse<>(status, obj.toString(), metadata);
            } else {
                return new CustomResponse<>(status, obj.toString());
            }
        } else {
            return new CustomResponse<>(status);
        }
    }

}
