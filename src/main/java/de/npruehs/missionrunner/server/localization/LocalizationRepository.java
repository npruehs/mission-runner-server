package de.npruehs.missionrunner.server.localization;

import org.springframework.data.repository.CrudRepository;

public interface LocalizationRepository extends CrudRepository<LocalizedString, String> {
}
