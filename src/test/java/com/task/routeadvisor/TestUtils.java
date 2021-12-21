package com.task.routeadvisor;

import com.task.routeadvisor.entity.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TestUtils {

    default List<Country> createXCountries(final int numberOfCountries) {
        var countries = new ArrayList<Country>();
        for (int i = 0; i < numberOfCountries; i++) {
            countries.add(
                Country.builder()
                    .id(UUID.randomUUID())
                    .code("Code " + (i + 1))
                    .name("Name " + (i + 1))
                    .build());
        }
        return countries;
    }

}
