package com.task.routeadvisor.usecase;

import com.task.routeadvisor.dto.RouteDTO;
import com.task.routeadvisor.entity.Country;
import com.task.routeadvisor.service.CountryService;
import com.task.routeadvisor.service.RouteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FastestRouteUseCase {

    private final CountryService countryService;
    private final RouteSearchService routeSearchService;

    public RouteDTO findFastestRoute(final String originCountryCode, final String destinationCountryCode) {
        final Country originCountry = countryService.findCountryByCode(originCountryCode);
        final Country destinationCountry = countryService.findCountryByCode(destinationCountryCode);

        return new RouteDTO(routeSearchService.findRoute(originCountry, destinationCountry));
    }

}
