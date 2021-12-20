package com.task.routeadvisor.service;

import com.task.routeadvisor.entity.Country;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RouteSearchService {

    private static final int BORDER_DISTANCE = 1;

    public Set<Country> findRoute(final Country origin, final Country destination) {
        origin.setDistance(0);

        final Set<Country> processedCountries = new LinkedHashSet<>();
        final Set<Country> unprocessedCountries = new LinkedHashSet<>();

        unprocessedCountries.add(origin);

        while (!processedCountries.contains(destination) && unprocessedCountries.iterator().hasNext()) {
            final Country processingCountry = unprocessedCountries.iterator().next();
            unprocessedCountries.remove(processingCountry);

            for (final Country borderCountry: processingCountry.getBorders()) {
                if (!processedCountries.contains(borderCountry)) {
                    calculateFastestRoute(processingCountry, borderCountry);
                    unprocessedCountries.add(borderCountry);
                }
            }
            processedCountries.add(processingCountry);
        }

        if (!processedCountries.contains(destination)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Travelling by land from: " + origin + " to " + destination + " is not possible. Try to travel by airplane instead ;)"
            );
        }

        destination.getFastestRoute().add(destination);

        return destination.getFastestRoute();
    }

    private void calculateFastestRoute(final Country processingCountry, final Country borderCountry) {
        final Integer originRouteDistance = processingCountry.getDistance();

        if (originRouteDistance + BORDER_DISTANCE < borderCountry.getDistance()) {
            borderCountry.setDistance(originRouteDistance + BORDER_DISTANCE);

            final Set<Country> fastestRoute = new LinkedHashSet<>(processingCountry.getFastestRoute());
            fastestRoute.add(processingCountry);

            borderCountry.setFastestRoute(fastestRoute);
        }
    }

}
