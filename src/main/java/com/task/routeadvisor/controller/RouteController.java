package com.task.routeadvisor.controller;

import com.task.routeadvisor.dto.RouteDTO;
import com.task.routeadvisor.usecase.FastestRouteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Fastest route API")
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RouteController {

    private final FastestRouteUseCase fastestRouteUseCase;

    /**
     * Finds the fastest route. "Fastest route" represents
     * route from origin to destination country while crossing
     * minimum number of borders.
     *
     * @param origin        Origin country code
     * @param destination   Destination country code
     * @return              Fastest route (collection of countries)
     */
    @Operation(summary = "Finds the fastest route for the provided origin and destination countries.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Finds fastest route",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RouteDTO.class)
            )),
        @ApiResponse(
            responseCode = "400",
            description = "Route does not exist",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HashMap.class)
            )),
        @ApiResponse(
            responseCode = "404",
            description = "Provided country not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = HashMap.class)
            ))
    })
    @GetMapping(value = "/v1/routing", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RouteDTO findRoute(
            @RequestParam @Pattern(regexp = "^[A-Z]{3}$") final String origin,
            @RequestParam @Pattern(regexp = "^[A-Z]{3}$") final String destination) {
        return fastestRouteUseCase.findFastestRoute(origin, destination);
    }

}
