package com.club.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponseWriter {
	public void writeAsUTF_8(HttpServletResponse response,Object result) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
        try {
            objectMapper.writeValue(jsonGenerator, result);
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
	}
}
