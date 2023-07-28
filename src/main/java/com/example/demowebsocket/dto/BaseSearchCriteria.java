package com.example.demowebsocket.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseSearchCriteria extends DefaultQueryCriteria {
    private String query;
}
