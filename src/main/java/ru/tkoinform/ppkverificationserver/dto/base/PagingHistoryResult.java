package ru.tkoinform.ppkverificationserver.dto.base;

import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;

import java.util.List;

public class PagingHistoryResult {
    private List<InfrastructureObjectDto> data;
    private Long count;


    public PagingHistoryResult(List<InfrastructureObjectDto> data, Long count) {
        this.data = data;
        this.count = count;
    }

    public List<InfrastructureObjectDto> getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }
}
