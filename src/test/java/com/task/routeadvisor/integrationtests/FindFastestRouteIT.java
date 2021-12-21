package com.task.routeadvisor.integrationtests;

import com.task.routeadvisor.dto.RouteDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FindFastestRouteIT {

    private static String ROOT_URL;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int localPort;

    @BeforeAll
    public void setup() {
        ROOT_URL = "http://localhost:" + localPort + "/api/v1";
    }

    @Test
    void shouldFindFastestRouteFromSerbiaToCzechRepublic() {
        var response = restTemplate
            .getForEntity(ROOT_URL + "/routing?origin=SRB&destination=CZE", RouteDTO.class)
            .getBody();

        assertThat(response.getRoute()).isNotNull()
            .isIn(
                List.of("SRB", "HUN", "AUT", "CZE"),
                List.of("SRB", "HUN", "SVK", "CZE")
        );
    }

    @Test
    void shouldFindFastestRouteFromGreeceToSpain() {
        var response = restTemplate
            .getForEntity(ROOT_URL + "/routing?origin=GRC&destination=ESP", RouteDTO.class)
            .getBody();

        assertThat(response.getRoute())
            .isNotNull()
            .isEqualTo(List.of("GRC", "TUR", "AZE", "RUS", "POL", "DEU", "FRA", "ESP"));
    }

    @Test
    void shouldThrow400ErrorSinceRouteCannotBeCalculated() {
        var statusCode = restTemplate
            .getForEntity(ROOT_URL + "/routing?origin=ESP&destination=CUB", Map.class)
            .getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrow400ErrorSinceCountryCodeIsNotValid() {
        var statusCode = restTemplate
            .getForEntity(ROOT_URL + "/routing?origin=ESP&destination=invalid_country_code", Map.class)
            .getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    void shouldThrow404ErrorSinceProvidedCountryDoesNotExist() {
        var statusCode = restTemplate
            .getForEntity(ROOT_URL + "/routing?origin=ESP&destination=PPP", Map.class)
            .getStatusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
