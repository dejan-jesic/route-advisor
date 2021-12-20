package com.task.routeadvisor.dto;

import com.task.routeadvisor.entity.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RouteDTO {

    private List<String> route;

    public RouteDTO(final Collection<Country> countries) {
        this.route = countries.stream()
            .map(Country::getCode)
            .collect(Collectors.toCollection(LinkedList::new));
    }

}
