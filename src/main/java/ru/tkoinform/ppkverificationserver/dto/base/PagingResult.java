package ru.tkoinform.ppkverificationserver.dto.base;

import java.util.List;

public class PagingResult<T> {

    private List<T> data;

    private Long count;

    public PagingResult(List<T> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }
}

