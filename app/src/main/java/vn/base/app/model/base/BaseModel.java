package vn.base.app.model.base;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.base.app.utils.Utils;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "_id")
    @Hidden
    String id;
    
    @Column(name = "_created_at")
    @Hidden
    Long createdAt = Utils.TIMESTAMP_NOW();
    
    @Column(name = "_updated_at")
    @Hidden
    Long updatedAt = Utils.TIMESTAMP_NOW();

}
