package br.ufscar.dc.dsw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AbstractRestController {
    public Long toLong(Object id){
        if (id != null){
            if (id instanceof Integer) {
                return ((Integer) id).longValue();
            } else {
                return (Long) id;
            }
        }
        return null;
    }
    public boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }
}
