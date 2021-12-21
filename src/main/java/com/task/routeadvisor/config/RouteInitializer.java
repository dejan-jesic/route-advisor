package com.task.routeadvisor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.routeadvisor.dto.CountryDTO;
import com.task.routeadvisor.entity.Country;
import com.task.routeadvisor.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RouteInitializer {

    @Value("${route-advisor.countries.repository-url}")
    private String repositoryUrl;

    @Value("${route-advisor.countries.exact-number}")
    private Integer numberOfCountries;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CountryRepository countryRepository;

    @PostConstruct
    public void initializeCountries() throws Exception {
        if (dbIsAlreadyPopulated()) {
            return;
        }

        final Set<CountryDTO> countries = fetchCountriesFromAPI();

        persistCountriesToDB(countries);
    }

    private boolean dbIsAlreadyPopulated() {
        if (countryRepository.findAll().size() == numberOfCountries) {
            log.info("DB already populated.");
            return true;
        } else {
            log.info("Wiping DB.");
            countryRepository.deleteAll();
            return false;
        }
    }

    private Set<CountryDTO> fetchCountriesFromAPI() throws JsonProcessingException {
        final ResponseEntity<String> countriesResponseAsJson = restTemplate.getForEntity(URI.create(repositoryUrl), String.class);
        final CountryDTO[] fetchedCountries = objectMapper.readValue(countriesResponseAsJson.getBody(), CountryDTO[].class);

        final Set<CountryDTO> countries = Arrays.stream(fetchedCountries)
            .filter(this::filterOutInvalidCountry)
            .collect(Collectors.toSet());

        if (didNotFetchExpectedCountries(countries)) {
            log.error("Cannot fetch countries from the API: {}", repositoryUrl);
            throw new IllegalStateException();
        }
        return countries;
    }

    private boolean filterOutInvalidCountry(final CountryDTO countryDTO) {
        return nonNull(countryDTO.getName())
            && hasText(countryDTO.getName().getCommon())
            && hasText(countryDTO.getCca3());
    }

    private void persistCountriesToDB(final Set<CountryDTO> countriesDTOs) {
        countryRepository.saveAll(convertToEntities(countriesDTOs));

        countriesDTOs.forEach(countryDTO -> countryRepository.findByCode(countryDTO.getCca3())
            .ifPresentOrElse(
                country -> setBorders(countryDTO, country),
                () -> { throw new IllegalStateException("Cannot find country by code: " + countryDTO.getCca3()); }
            ));
    }

    private void setBorders(final CountryDTO countryDTO, final Country country) {
        country.setBorders(countryRepository.findAllByCodeIn(Arrays.asList(countryDTO.getBorders())));
        countryRepository.save(country);
    }

    private List<Country> convertToEntities(final Set<CountryDTO> countries) {
        return countries.stream()
            .map(Country::new)
            .collect(Collectors.toList());
    }

    private boolean didNotFetchExpectedCountries(final Collection<CountryDTO> countries) {
        return isNull(countries) || countries.size() != numberOfCountries;
    }

}
