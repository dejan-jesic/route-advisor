package com.task.routeadvisor.config;

import com.task.routeadvisor.TestUtils;
import com.task.routeadvisor.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteInitializerTest implements TestUtils {

    @InjectMocks
    private RouteInitializer routeInitializer;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(routeInitializer, "numberOfCountries", 250);
        ReflectionTestUtils.setField(routeInitializer, "repositoryUrl", "https://repo-url.com");
    }

    @Test
    void shouldSkipPersistingSinceDbIsPopulated() throws Exception {
        // given
        var countries = createXCountries(250);

        // mock
        when(countryRepository.findAll()).thenReturn(countries);

        // when
        routeInitializer.initializeCountries();

        // then
        verify(countryRepository, never()).saveAll(anyCollection());
        verify(countryRepository, never()).deleteAll();
    }

}
