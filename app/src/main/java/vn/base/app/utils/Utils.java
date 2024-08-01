package vn.base.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import vn.base.app.config.Env;

public class Utils {

    public static void SET_CUSTOM_PROPERTY(String key, String value) {
        try {
            CustomLogger.info("SET CUSTOM PROPERTY " + key + " : " + value);
            Properties props = new Properties();
            File file = new File(Env.CUSTOM_PROPERTIES_PATH);
            InputStream inputStream = new FileInputStream(file);
            props.load(inputStream);
            inputStream.close();
            props.setProperty(key, value);
            OutputStream outputStream = new FileOutputStream(file);
            DefaultPropertiesPersister p = new DefaultPropertiesPersister();
            p.store(props, outputStream, null);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GET_CUSTOM_PROPERTY(String key, String defaultValue) {
        try {
            CustomLogger.info("GET CUSTOM PROPERTY " + key + " with default value : " + defaultValue);
            Properties props = new Properties();
            File file = new File(Env.CUSTOM_PROPERTIES_PATH);
            InputStream inputStream = new FileInputStream(file);
            props.load(inputStream);
            inputStream.close();
            String value = props.getProperty(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String FILE_TO_BASE64(String filePath) {
        try {
            File file = ResourceUtils.getFile(filePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String FILE_TO_BASE64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String FILE_TO_BASE64(byte[] fileContent) {
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String DATE_NOW() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DATETIME_NOW() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long TIMESTAMP_NOW() {
        try {
            Date date = new Date();
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DATE_TO_STRING(Date date) {
        try {
            String pattern = "yyyy/MM/dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DATETIME_TO_STRING(Date date) {
        try {
            String pattern = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TIMESTAMP_TO_DATETIME_STRING(long timestamp) {
        Date date = TIMESTAMP_TO_DATE(timestamp);
        return DATETIME_TO_STRING(date);
    }

    public static Long DATETIME_TO_TIMESTAMP(Date date) {
        try {
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date STRING_TO_DATE(String date_text) {
        try {
            String pattern = "yyyy/MM/dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(date_text);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date STRING_TO_DATETIME(String date_text) {
        try {
            String pattern = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(date_text);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date TIMESTAMP_TO_DATE(long timestamp) {
        try {
            return new Date(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String SHA256_HASH(String text) {
        String sha256hex = Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

    public static JSONObject EXEC_SHELL_COMMAND(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        builder.redirectError(ProcessBuilder.Redirect.PIPE);
        Process process;
        JSONObject jsonObject = new JSONObject();
        try {
            process = builder.start();
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            reader.close();
            stdout.close();
            InputStream stderr = process.getErrorStream();
            reader = new BufferedReader(new InputStreamReader(stderr));
            StringBuilder error = new StringBuilder();
            line = null;
            while ((line = reader.readLine()) != null) {
                error.append(line);
            }
            reader.close();
            stderr.close();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                jsonObject.put("status", HttpStatus.OK);
                jsonObject.put("data", output.toString());
            } else {
                jsonObject.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
                jsonObject.put("data", error.toString());
            }
        } catch (Exception e) {
            jsonObject.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            jsonObject.put("data", e.toString());
        }
        return jsonObject;
    }

    public static File MULTIPART_FILE_TO_FILE(MultipartFile multipartFile) {
        String file_dir = Env.FILE_DIR;
        String[] originalFileNames = multipartFile.getOriginalFilename().split("\\.");
        String fileExt = originalFileNames[originalFileNames.length - 1].toLowerCase();
        String fileName = String.join("_", originalFileNames).toLowerCase() + "_" + UUID() + "." + fileExt;
        Path filePath = Paths.get(file_dir, fileName);
        try {
            Files.write(filePath, multipartFile.getBytes());
            return filePath.toFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File BYTES_TO_FILE(byte[] bytes) {
        String file_dir = Env.FILE_DIR;
        String fileName = UUID();
        Path filePath = Paths.get(file_dir, fileName);
        try {
            Files.write(filePath, bytes);
            return filePath.toFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File BYTES_TO_FILE(byte[] bytes, String filename) {
        String file_dir = Env.FILE_DIR;
        String[] originalFileNames = filename.split("\\.");
        String fileExt = originalFileNames[originalFileNames.length - 1].toLowerCase();
        String fileName = String.join("_", originalFileNames).toLowerCase() + "_" + UUID() + "." + fileExt;
        Path filePath = Paths.get(file_dir, fileName);
        try {
            Files.write(filePath, bytes);
            return filePath.toFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File GET_FILE_FROM_FILENAME(String filename) {
        String file_dir = Env.FILE_DIR;
        File file = new File(file_dir + "/" + filename);
        return file;
    }

    public static String FILENAME_DATETIME_NOW() {
        String datetime_now = DATETIME_NOW();
        datetime_now = datetime_now.replace("\\:", "_");
        datetime_now = datetime_now.replace("\\-", "_");
        datetime_now = datetime_now.replace(" ", "_");
        return datetime_now;
    }

    public static ObjectMapper OBJECT_MAPPER() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static List<Header> GET_HEADERS_FROM_REQUEST(HttpServletRequest request) {
        try {
            List<Header> headers = new ArrayList<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(key);
                if (key.equalsIgnoreCase("Content-Length")) {
                    CustomLogger.info("REMOVE CONTENT-LENGTH");
                    continue;
                }
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    headers.add(new BasicHeader(key, value));
                }
            }
            return headers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Pair<String, String>> GET_SORT_PARAMS_FROM_REQUEST(HttpServletRequest request) {
        try {
            List<Pair<String, String>> res = new ArrayList<>();
            if (request.getContentType() == null
                    || request.getContentType().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    String[] sortConditionValues = request.getParameterValues(param);
                    for (String sortConditionValue : sortConditionValues) {
                        String[] sortConditions = sortConditionValue.split(":");
                        if (param.equalsIgnoreCase("sorts")) {
                            res.add(Pair.of(sortConditions[0], sortConditions[1]));
                        }
                    }
                }
                return res;
            } else if (request.getContentType().contains(ContentType.MULTIPART_FORM_DATA.getMimeType())) {
                MultipartHttpServletRequest request2 = (MultipartHttpServletRequest) request;
                Set<String> textPartNames = new HashSet<>();
                for (Part part : request2.getParts()) {
                    if (part.getSubmittedFileName() == null && part.getContentType() == null) {
                        textPartNames.add(part.getName());
                    }
                }
                Enumeration<String> params = request2.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    if (!textPartNames.contains(param)) {
                        String[] sortConditionValues = request.getParameterValues(param);
                        for (String sortConditionValue : sortConditionValues) {
                            String[] sortConditions = sortConditionValue.split(":");
                            if (param.equalsIgnoreCase("sorts")) {
                                res.add(Pair.of(sortConditions[0], sortConditions[1]));
                            }
                        }
                    }
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Pair<String, String>> GET_SORT_PARAMS_FROM_REQUEST(List<String> sortConditions) {
        try {
            List<Pair<String, String>> res = new ArrayList<>();
            for (String sortCondition : sortConditions) {
                String[] tmpSortConditions = sortCondition.split(":");
                res.add(Pair.of(tmpSortConditions[0], tmpSortConditions[1]));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CustomPagination GET_PAGINATION_PARAMS_FROM_REQUEST(HttpServletRequest request) {
        try {
            CustomPagination pagination = new CustomPagination();
            if (request.getContentType() == null
                    || request.getContentType().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    String value = request.getParameter(param);
                    if (param.equalsIgnoreCase("page")) {
                        pagination.setPage(Long.parseLong(value));
                    } else if (param.equalsIgnoreCase("pageSize")) {
                        pagination.setPageSize(Long.parseLong(value));
                    }
                }
            } else if (request.getContentType().contains(ContentType.MULTIPART_FORM_DATA.getMimeType())) {
                MultipartHttpServletRequest request2 = (MultipartHttpServletRequest) request;
                Set<String> textPartNames = new HashSet<>();
                for (Part part : request2.getParts()) {
                    if (part.getSubmittedFileName() == null && part.getContentType() == null) {
                        textPartNames.add(part.getName());
                    }
                }
                Enumeration<String> params = request2.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    if (!textPartNames.contains(param)) {
                        String value = request.getParameter(param);
                        if (param.equalsIgnoreCase("page")) {
                            pagination.setPage(Long.parseLong(value));
                        } else if (param.equalsIgnoreCase("pageSize")) {
                            pagination.setPageSize(Long.parseLong(value));
                        }
                    }
                }
            }
            return pagination;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String[]> GET_PARAMS_FROM_REQUEST(HttpServletRequest request) {
        try {
            Map<String, String[]> res = new HashMap<>();
            if (request.getContentType() == null
                    || request.getContentType().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    if (!param.equalsIgnoreCase("sorts") &&
                            !param.equalsIgnoreCase("page") &&
                            !param.equalsIgnoreCase("pageSize")) {
                        res.put(param, request.getParameterValues(param));
                    }
                }
                return res;
            } else if (request.getContentType().contains(ContentType.MULTIPART_FORM_DATA.getMimeType())) {
                MultipartHttpServletRequest request2 = (MultipartHttpServletRequest) request;
                Set<String> textPartNames = new HashSet<>();
                for (Part part : request2.getParts()) {
                    if (part.getSubmittedFileName() == null && part.getContentType() == null) {
                        textPartNames.add(part.getName());
                    }
                }
                Enumeration<String> params = request2.getParameterNames();
                while (params.hasMoreElements()) {
                    String param = params.nextElement();
                    if (!textPartNames.contains(param)) {
                        if (!param.equalsIgnoreCase("sorts") &&
                                !param.equalsIgnoreCase("page") &&
                                !param.equalsIgnoreCase("pageSize")) {
                            res.put(param, request2.getParameterValues(param));
                        }
                    }
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] GET_PARAM_FROM_REQUEST(HttpServletRequest request, String param) {
        try {
            return request.getParameterValues(param);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpEntity GET_BODY_FROM_REQUEST(HttpServletRequest request) {
        try {
            if (request.getContentType() == null
                    || request.getContentType().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                String jsonStr = request.getReader().lines().collect(Collectors.joining());
                if (jsonStr != null && !jsonStr.equals("")) {
                    JSONObject res = new JSONObject(jsonStr);
                    return new StringEntity(res.toString(), ContentType.APPLICATION_JSON);
                } else {
                    return null;
                }
            } else if (request.getContentType().contains(ContentType.MULTIPART_FORM_DATA.getMimeType())) {
                MultipartHttpServletRequest request2 = (MultipartHttpServletRequest) request;
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                Set<String> textPartNames = new HashSet<>();
                if (request2.getParts().isEmpty() || request2.getParts().size() == 0) {
                    return null;
                }
                for (Part part : request2.getParts()) {
                    if (part.getSubmittedFileName() == null && part.getContentType() == null) {
                        textPartNames.add(part.getName());
                    } else {
                        File file = new File(Env.FILE_DIR + "/" + part.getSubmittedFileName());
                        FileUtils.copyInputStreamToFile(part.getInputStream(), file);
                        multipartEntityBuilder.addBinaryBody(part.getName(), file);
                        file.deleteOnExit();
                    }
                }
                for (String textPartName : textPartNames) {
                    for (String x : request2.getParameterValues(textPartName)) {
                        multipartEntityBuilder.addTextBody(textPartName, x);
                    }
                }
                return multipartEntityBuilder.build();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Field> GET_ALL_FIELD_OF_OBJECT(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public static List<String> GET_ALL_FIELD_NAME_OF_OBJECT(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        List<String> fieldNames = new ArrayList<String>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public static void SET_OBJECT_FIELD_VALUE(Object object, String fieldName, Object value) throws Exception {
        List<Field> fields = GET_ALL_FIELD_OF_OBJECT(object);
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                field.set(object, value);
                field.setAccessible(false);
            }
        }
    }

    public static Object GET_OBJECT_FIELD_VALUE(Object object, String fieldName) throws Exception {
        List<Field> fields = GET_ALL_FIELD_OF_OBJECT(object);
        Object res = null;
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                res = field.get(object);
                field.setAccessible(false);
            }
        }
        return res;
    }

}
