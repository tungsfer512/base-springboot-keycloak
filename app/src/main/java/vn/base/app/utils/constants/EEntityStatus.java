package vn.base.app.utils.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum EEntityStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    ALLOWED("ALLOWED"),
    BLOCKED("BLOCKED"),
    READ("READ"),
    UNREAD("UNREAD"),
    ;

    final String code;

    EEntityStatus(final String code) {
        this.code = code;
    }

}
