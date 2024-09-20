package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;

public abstract class BaseObject {
    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("BaseObject: ", e);
            jsonString = "Can't build json from object";
        }
        return jsonString;
    }

}
