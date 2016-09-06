package com.club.web.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LongJsonSerializer extends JsonSerializer<Long>{

	@Override
	public void serialize(Long value, JsonGenerator generator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		generator.writeString(value+"");
	}
}