package com.club.framework.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ChineseEnumJsonSerializer extends JsonSerializer<Enum<?>>{

	@Override
	public void serialize(Enum<?> value, JsonGenerator generator, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
        generator.writeNumberField("ordinal",value.ordinal());
        generator.writeStringField("name",value.name());
        try {
			generator.writeStringField("chineseName",(String)value.getClass().getDeclaredMethod("getChineseName").invoke(value));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
        generator.writeEndObject();
	}
}