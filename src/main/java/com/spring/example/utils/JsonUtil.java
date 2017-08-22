package com.spring.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	 
    private static ObjectMapper mapper = new ObjectMapper(); 
    
    public static synchronized ObjectMapper getMapperInstance(boolean needNew) {   
        if (needNew) {   
            return new ObjectMapper();   
        } else if (mapper == null) {   
            mapper = new ObjectMapper();   
        }   
        return mapper;   
    }
    
    //example
    public static void loadConfigure(){
    	configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    	configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    	configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    	configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    	configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
    	configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
    	configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    private static void configure(Feature f, boolean state){
    	mapper.configure(f, state);
    }
    
    private static void configure(org.codehaus.jackson.map.DeserializationConfig.Feature f, boolean state){
    	mapper.configure(f, state);
    }
    
    /**
     * object to json
     * @param obj
     * @return
     */
    public static String encode(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("encode(Object)", e);
        } catch (JsonMappingException e) {
            logger.error("encode(Object)", e);
        } catch (IOException e) {
            logger.error("encode(Object)", e);
        }
        return null;
    }

    /**
     * json to object
     *
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, Class<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, Class<T>)", e);
        }
        return null;
    }

    /**
     * json array to json
     *
     * @param json
     * @param jsonTypeReference
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T decode(String json, TypeReference<T> jsonTypeReference) {
        try {
            return (T) mapper.readValue(json, jsonTypeReference);
        } catch (JsonParseException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            logger.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }
}
