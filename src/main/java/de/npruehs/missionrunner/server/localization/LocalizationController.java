package de.npruehs.missionrunner.server.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterables;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.localization.LocalizedString.LocalizedStringFunnel;

@RestController
public class LocalizationController {
	@Autowired
	private LocalizationRepository repository;
	
	@GetMapping("/localization/get")
	public NetworkResponse<LocalizationData> get(@RequestParam(value = "hash", required = false) String hash) {
		// Get strings.
		Iterable<LocalizedString> strings = repository.findAll();
		
		// Calculate hash.
		HashFunction hf = Hashing.murmur3_128();
		
		Hasher hasher = hf.newHasher();
		LocalizedStringFunnel funnel = new LocalizedString.LocalizedStringFunnel();
		
		for (LocalizedString string : strings) {
			hasher.putObject(string, funnel);
		}
		
		HashCode hashCode = hasher.hash();
		
		// Compare hashes.
		if (hashCode.toString().equals(hash)) {
			return NetworkResponse.newSuccessResponse(
					new LocalizationData(hash, null));
		}
		
		// Return new strings.
		return NetworkResponse.newSuccessResponse(
				new LocalizationData(hashCode.toString(), Iterables.toArray(strings, LocalizedString.class)));
	}
}
