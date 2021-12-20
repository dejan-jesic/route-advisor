package com.task.routeadvisor.repository;

import com.task.routeadvisor.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAllByCodeIn(List<String> codes);

    Optional<Country> findByCode(String code);

}
