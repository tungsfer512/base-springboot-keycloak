package vn.base.app.controller.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.base.app.exception.CustomException;
import vn.base.app.exception.EErorr;
import vn.base.app.model.base.BaseModel;
import vn.base.app.service.base.BaseService;
import vn.base.app.utils.ControllerUtils;
import vn.base.app.utils.CustomLogger;
import vn.base.app.utils.CustomPagination;
import vn.base.app.utils.Utils;
import vn.base.app.utils.constants.EComparision;

public class BaseController<T extends BaseModel> {

    @Autowired
    BaseService<T> service;

    public BaseService<T> getService() {
        return service;
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> add(
            HttpServletRequest request,
            @RequestBody @Valid T entity) {
        try {
            Map<String, String[]> params = Utils.GET_PARAMS_FROM_REQUEST(request);
            CustomLogger.info(params);
            T resData = service.save(entity);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(resData);
            return ControllerUtils.response(HttpStatus.CREATED, jsonData, null).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAll(
            HttpServletRequest request,
            @RequestParam(name = "sorts", required = false) List<String> sorts) {
        try {
            List<Pair<String, String>> sortConditions = Utils.GET_SORT_PARAMS_FROM_REQUEST(sorts);
            List<T> datas = service.findAll(sortConditions);
            String jsonDatas = Utils.OBJECT_MAPPER().writeValueAsString(datas);
            Long total = service.count();
            CustomPagination metadataPagination = new CustomPagination(1, total, total);
            JSONObject jsonMetadata = new JSONObject();
            jsonMetadata.put("pagination", metadataPagination);
            return ControllerUtils.response(HttpStatus.OK, jsonDatas, jsonMetadata).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "")
    public ResponseEntity<Object> get(
            HttpServletRequest request,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            // START ADD filter conditions
            // @RequestParam(name = "attribute", required = false) Object value,
            // End ADD filter conditions
            @RequestParam(name = "sorts", required = false) List<String> sorts) {
        try {
            List<Pair<String, String>> sortConditions = Utils.GET_SORT_PARAMS_FROM_REQUEST(sorts);
            CustomLogger.info(sorts);
            CustomPagination pagination = Utils.GET_PAGINATION_PARAMS_FROM_REQUEST(request);
            CustomLogger.info(pagination.getPage(), pagination.getPageSize(), pagination.getTotal());
            List<Triple<String, List<Object>, EComparision>> filters = new ArrayList<>();
            // START ADD filter conditions
            // filters.add(Triple.of("attribute", value, EComparision.X));
            // End ADD filter conditions
            List<T> datas = service.findAll(pagination, filters, sortConditions);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(datas);
            Long total = service.count(filters);
            CustomPagination metadataPagination = new CustomPagination(pagination.getPage(), pagination.getPageSize(),
                    total);
            JSONObject jsonMetadata = new JSONObject();
            jsonMetadata.put("pagination", metadataPagination);
            return ControllerUtils.response(HttpStatus.OK, jsonData, jsonMetadata).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getById(
            HttpServletRequest request,
            @PathVariable String id) {
        try {
            T data = service.findById(id);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(data);
            return ControllerUtils.response(HttpStatus.OK, jsonData).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteById(
            HttpServletRequest request,
            @PathVariable String id) {
        try {
            service.deleteById(id);
            return ControllerUtils.response(HttpStatus.NO_CONTENT).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> fullUpdateById(
            HttpServletRequest request,
            @PathVariable String id,
            @RequestBody @Valid T entity) {
        try {
            entity.setId(id);
            T resData = service.save(entity);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(resData);
            return ControllerUtils.response(HttpStatus.OK, jsonData).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Object> partialUpdateById(
            HttpServletRequest request,
            @PathVariable String id,
            @RequestBody @Valid T entity) {
        try {
            entity.setId(id);
            T resData = service.save(entity);
            String jsonData = Utils.OBJECT_MAPPER().writeValueAsString(resData);
            return ControllerUtils.response(HttpStatus.OK, jsonData).response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(EErorr.INTERNAL_SERVER_ERROR);
        }
    }

}
