package de.npruehs.missionrunner.server.mission;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MissionRequirementListJpaConverter implements AttributeConverter<ArrayList<MissionRequirement>, String> {
	
	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ArrayList<MissionRequirement> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			return null;
		}
	}

	@Override
	public ArrayList<MissionRequirement> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<ArrayList<MissionRequirement>>() {});
		} catch (IOException ex) {
			return null;
		}
	}
}
