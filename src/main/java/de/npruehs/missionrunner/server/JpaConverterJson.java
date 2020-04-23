package de.npruehs.missionrunner.server;

import java.io.IOException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JpaConverterJson<T> implements AttributeConverter<T, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(T attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			return null;
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<T>() {});
		} catch (IOException ex) {
			return null;
		}
	}
}
