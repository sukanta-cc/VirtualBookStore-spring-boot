package com.virtualbookstore.VirtualBookStore.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ApiResponse<T> {
    private boolean error;
    @JsonProperty(required = true)
    private String code;
    @JsonProperty(required = true)
    private String message;
    private T data;


}