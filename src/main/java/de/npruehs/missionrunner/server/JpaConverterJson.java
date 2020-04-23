package de.npruehs.missionrunner.server;

import java.io.IOException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JpaConverterJson implements AttributeConverter<Object, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			return null;
		}
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, Object.class);
		} catch (IOException ex) {
			return null;
		}
	}
}
