package com.company.electro_store.dto;

import com.company.electro_store.util.managers.GsonBuilder;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Request implements Serializable {
    private static Gson gson = GsonBuilder.getGson();
    private String requestType;
    private String requestString;
    
    public Request(String requestType, Object requestObject) {
        if (gson == null) {
            throw new IllegalArgumentException("Gson must be set (Request.setGson)");
        }
        this.requestType = requestType;
        this.requestString = gson.toJson(requestObject);
    }
    
    @Override
    public String toString() {
        return '\t' + requestType + '\n' + requestString.indent(4);
    }
}