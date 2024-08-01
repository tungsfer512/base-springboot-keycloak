package vn.base.app.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomPagination {

    long page;
    long pageSize;
    long total;

    public CustomPagination() {
        this.page = 1;
        this.pageSize = 10;
        this.total = 0L;
    }

    public CustomPagination(long page, long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = 0L;
    }

    public CustomPagination(long page, long pageSize, long total) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

}
