package com.task.routeadvisor.dto;

import com.task.routeadvisor.TestUtils;
import com.task.routeadvisor.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RouteDTOTest implements TestUtils {

    @Test
    void shouldMapRoutes() {
        // given
        var countries = createXCountries(5);

        var expectedRoutes = countries.stream()
            .map(Country::getCode)
            .collect(Collectors.toList());

        // when
        var routeDTO = new RouteDTO(countries);

        // then
        assertThat(routeDTO.getRoute())
            .hasSize(countries.size())
            .isEqualTo(expectedRoutes);
    }

}