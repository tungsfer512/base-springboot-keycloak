package vn.base.app.utils.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum EComparision {
    EQUAL("EQUAL", "Lọc các đối tượng có giá trị của trường A bằng/giống với X"),
    NOT_EQUAL("NOT_EQUAL", "Lọc các đối tượng có giá trị của trường A khác với X"),
    CONTAINS("CONTAINS", "Lọc các đối tượng có giá trị của trường A nằm trong mảng Xs"),
    NOT_CONTAINS("NOT_CONTAINS", "Lọc các đối tượng có giá trị của trường A không nằm trong mảng Xs"),
    LIKE("LIKE", "Lọc các đối tượng có giá trị của trường A giống với X"),
    NOT_LIKE("NOT_LIKE", "Lọc các đối tượng có giá trị của trường A khác với X"),
    START_WITH("START_WITH", "Lọc các đối tượng có giá trị của trường A bắt đầu bằng X"),
    NOT_START_WITH("NOT_START_WITH", "Lọc các đối tượng có giá trị của trường A không bắt đầu bằng X"),
    END_WITH("END_WITH", "Lọc các đối tượng có giá trị của trường A kết thúc bằng X"),
    NOT_END_WITH("NOT_END_WITH", "Lọc các đối tượng có giá trị của trường A không kết thúc bằng X"),
    LESS_THAN("LESS_THAN", "Lọc các đối tượng có giá trị của trường A nhỏ hơn X"),
    GREATER_THAN("GREATER_THAN", "Lọc các đối tượng có giá trị của trường A lớn hơn X"),
    LESS_EQUAL_THAN("LESS_EQUAL_THAN", "Lọc các đối tượng có giá trị của trường A nhỏ hơn hoặc bằng X"),
    GREATER_EQUAL_THAN("GREATER_EQUAL_THAN", "Lọc các đối tượng có giá trị của trường A lớn hơn hoặc bằng X"),
    ;

    final String code;
    final String description;

    EComparision(final String code, final String description) {
        this.code = code;
        this.description = description;
    }
}
