package vn.base.app.dto.base;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOUtils<T, DTO> {

    @Autowired
    ModelMapper modelMapper;

    public T toEntity(DTO source, Type type) {
        return modelMapper.map(source, type);
    }

    public List<T> toListEntity(List<DTO> sources, Type type) {
        List<T> results = new ArrayList<>();
        for (DTO source : sources) {
            T result = modelMapper.map(source, type);
            results.add(result);
        }
        return results;
    }

    public DTO toDTO(T source, Type type) {
        return modelMapper.map(source, type);
    }

    public List<DTO> toListDTO(List<T> sources, Type type) {
        List<DTO> results = new ArrayList<>();
        for (T source : sources) {
            DTO result = modelMapper.map(source, type);
            results.add(result);
        }
        return results;
    }

}
