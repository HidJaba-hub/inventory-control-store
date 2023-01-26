package com.company.electro_store.dto;

import com.company.electro_store.util.managers.GsonBuilder;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Type;

@NoArgsConstructor
@Getter
@Setter
public class Response implements Serializable {
    private static Gson gson = GsonBuilder.getGson();
    private ResponseType responseType;
    private String responseString;
    
    public Response(ResponseType responseType, Object responseObject) {
        if (gson == null) {
            throw new IllegalArgumentException("Gson must be set (Response.setGson)");
        }
        this.responseType = responseType;
        this.responseString = gson.toJson(responseObject);
    }
    
    @Override
    public String toString() {
        return '\t' + responseType.toString() + '\n' + responseString.indent(4);
    }
    
    public Object getResponseObject(Type type) {
        return gson.fromJson(responseString, type);
    }
    
    public boolean isError() {
        return responseType.equals(ResponseType.ERROR);
    }
    
    public enum ResponseType implements Serializable {
        OK,
        ERROR
    }
}