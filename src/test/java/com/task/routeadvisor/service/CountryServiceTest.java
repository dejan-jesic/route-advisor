package com.task.routeadvisor.service;

import com.task.routeadvisor.entity.Country;
import com.task.routeadvisor.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    void shouldFindByCode() {
        when(countryRepository.findByCode(anyString()))
            .thenReturn(Optional.of(new Country()));

        assertDoesNotThrow(() -> countryService.findCountryByCode("code"));
        verify(countryRepository).findByCode(eq("code"));
    }

    @Test
    void shouldThrow404Exception() {
        when(countryRepository.findByCode(anyString()))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> countryService.findCountryByCode("JPN"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContainingAll("404", "NOT_FOUND", "Country 'JPN' not found");
    }

}
