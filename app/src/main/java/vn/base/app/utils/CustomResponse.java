package vn.base.app.utils;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CustomResponse<T> {

    T data;
    HttpStatus status;
    JSONObject metadata;

    public CustomResponse(HttpStatus status) {
        this.status = status;
        this.data = null;
    }

    public CustomResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public CustomResponse(HttpStatus status, T data, JSONObject metadata) {
        this.status = status;
        this.data = data;
        this.metadata = metadata;
    }

    public ResponseEntity<Object> response() {
        JSONObject responseData = new JSONObject();
        responseData.put("message", this.status.getReasonPhrase());
        if (this.status.is2xxSuccessful()) {
            responseData.put("success", true);
            if (this.data != null) {
                if (this.data instanceof String) {
                    if (this.data.toString().startsWith("{")) {
                        JSONObject jsonData = new JSONObject(this.data.toString());
                        if (jsonData.has("message") && jsonData.has("success")) {
                            return new ResponseEntity<Object>(jsonData.toMap(), this.status);
                        } else {
                            responseData.put("data", jsonData.toMap());
                        }
                    } else if (this.data.toString().startsWith("[")) {
                        JSONArray jsonDatas = new JSONArray(this.data.toString());
                        responseData.put("data", jsonDatas.toList());
                    } else {
                        responseData.put("data", this.data.toString());
                    }
                } else if (this.data instanceof ByteArrayResource) {
                    try {
                        CustomLogger.info("DOWNLOAD FILE AS BYTEARRAY");
                        ByteArrayResource byteArrayResource = (ByteArrayResource) this.data;
                        String filename = byteArrayResource.getFilename();
                        if (filename == null || filename.equals("")) {
                            TikaConfig config = TikaConfig.getDefaultConfig();
                            Metadata metadata = new Metadata();
                            InputStream stream = TikaInputStream.get(byteArrayResource.getInputStream());
                            org.apache.tika.mime.MediaType mediaType = config.getMimeRepository().detect(stream,
                                    metadata);
                            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
                            String extension = mimeType.getExtension();
                            filename = Utils.FILENAME_DATETIME_NOW() + extension;
                            stream.close();
                        }
                        return ResponseEntity.status(this.status.value())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(byteArrayResource);
                    } catch (Exception e) {
                        responseData.put("success", false);
                        responseData.put("error", "Lỗi khi thao tác với file!");
                        return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else if (this.data != null && this.data instanceof File) {
                    try {
                        CustomLogger.info("DOWNLOAD FILE");
                        File file = (File) this.data;
                        InputStream inputStream = new FileInputStream(file);
                        ByteArrayResource byteArrayResource = new ByteArrayResource(inputStream.readAllBytes());
                        String filename = file.getName();
                        if (filename == null || filename.equals("")) {
                            TikaConfig config = TikaConfig.getDefaultConfig();
                            Metadata metadata = new Metadata();
                            org.apache.tika.mime.MediaType mediaType = config.getMimeRepository().detect(inputStream,
                                    metadata);
                            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
                            String extension = mimeType.getExtension();
                            filename = Utils.FILENAME_DATETIME_NOW() + "." + extension;
                        }
                        inputStream.close();
                        return ResponseEntity.status(this.status.value())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(byteArrayResource);
                    } catch (Exception e) {
                        responseData.put("success", false);
                        responseData.put("error", "Lỗi khi thao tác với file!");
                        return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    responseData.put("data", this.data);
                }
            }
        } else {
            responseData.put("success", false);
            if (this.data != null) {
                if (this.data instanceof String) {
                    if (this.data.toString().startsWith("{")) {
                        JSONObject jsonData = new JSONObject(this.data.toString());
                        if (jsonData.has("success") && jsonData.has("message")) {
                            return new ResponseEntity<Object>(jsonData.toMap(), this.status);
                        } else {
                            responseData.put("error", jsonData.toMap());
                        }
                    } else if (this.data.toString().startsWith("[")) {
                        JSONArray jsonDatas = new JSONArray(this.data.toString());
                        responseData.put("error", jsonDatas.toList());
                    } else {
                        responseData.put("error", this.data.toString());
                    }
                } else {
                    responseData.put("error", this.data);
                }
            }
        }
        if (this.metadata != null) {
            responseData.put("metadata", this.metadata);
        }
        return new ResponseEntity<Object>(responseData.toMap(), this.status);
    }

    public ResponseEntity<Object> response(String filename) {
        JSONObject responseData = new JSONObject();
        responseData.put("message", this.status.getReasonPhrase());
        if (this.status.is2xxSuccessful()) {
            responseData.put("success", true);
            if (this.data != null) {
                if (this.data instanceof String) {
                    if (this.data.toString().startsWith("{")) {
                        JSONObject jsonData = new JSONObject(this.data.toString());
                        if (jsonData.has("message") && jsonData.has("success")) {
                            return new ResponseEntity<Object>(jsonData.toMap(), this.status);
                        } else {
                            responseData.put("data", jsonData.toMap());
                        }
                    } else if (this.data.toString().startsWith("[")) {
                        JSONArray jsonDatas = new JSONArray(this.data.toString());
                        responseData.put("data", jsonDatas.toList());
                    } else {
                        responseData.put("data", this.data.toString());
                    }
                } else if (this.data instanceof ByteArrayResource) {
                    try {
                        CustomLogger.info("DOWNLOAD FILE AS BYTEARRAY");
                        ByteArrayResource byteArrayResource = (ByteArrayResource) this.data;
                        return ResponseEntity.status(this.status.value())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(byteArrayResource);
                    } catch (Exception e) {
                        responseData.put("success", false);
                        responseData.put("error", "Lỗi khi thao tác với file!");
                        return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else if (this.data != null && this.data instanceof File) {
                    try {
                        CustomLogger.info("DOWNLOAD FILE");
                        File file = (File) this.data;
                        InputStream inputStream = new FileInputStream(file);
                        ByteArrayResource byteArrayResource = new ByteArrayResource(inputStream.readAllBytes());
                        inputStream.close();
                        return ResponseEntity.status(this.status.value())
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(byteArrayResource);
                    } catch (Exception e) {
                        responseData.put("success", false);
                        responseData.put("error", "Lỗi khi thao tác với file!");
                        return new ResponseEntity<Object>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    responseData.put("data", this.data);
                }
            }
        } else {
            responseData.put("success", false);
            if (this.data != null) {
                if (this.data instanceof String) {
                    if (this.data.toString().startsWith("{")) {
                        JSONObject jsonData = new JSONObject(this.data.toString());
                        if (jsonData.has("success") && jsonData.has("message")) {
                            return new ResponseEntity<Object>(jsonData.toMap(), this.status);
                        } else {
                            responseData.put("error", jsonData.toMap());
                        }
                    } else if (this.data.toString().startsWith("[")) {
                        JSONArray jsonDatas = new JSONArray(this.data.toString());
                        responseData.put("error", jsonDatas.toList());
                    } else {
                        responseData.put("error", this.data.toString());
                    }
                } else {
                    responseData.put("error", this.data);
                }
            }
        }
        if (this.metadata != null) {
            responseData.put("metadata", this.metadata);
        }
        return new ResponseEntity<Object>(responseData.toMap(), this.status);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
