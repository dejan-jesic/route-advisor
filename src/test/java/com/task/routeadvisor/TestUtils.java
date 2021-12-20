package com.task.routeadvisor;

import com.task.routeadvisor.entity.Country;

import java.util.ArrayList;
import java.util.List;

public interface TestUtils {

    default List<Country> createXCountries(final int numberOfCountries) {
        var countries = new ArrayList<Country>();
        for (int i = 0; i < numberOfCountries; i++) {
            countries.add(
                Country.builder()
                    .id((long) (i + 1))
                    .code("" + (i + 1))
                    .name("Name")
                    .build());
        }
        return countries;
    }

}
