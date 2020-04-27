package de.npruehs.missionrunner.server.character;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CharacterSkillListJpaConverter implements AttributeConverter<ArrayList<CharacterSkill>, String> {
	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ArrayList<CharacterSkill> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			return null;
		}
	}

	@Override
	public ArrayList<CharacterSkill> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<ArrayList<CharacterSkill>>() {});
		} catch (IOException ex) {
			return null;
		}
	}
}
