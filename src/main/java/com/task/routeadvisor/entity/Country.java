package com.task.routeadvisor.entity;

import com.task.routeadvisor.dto.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@ToString(of = {"name", "code"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String code;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinTable(
        name = "country_border",
        joinColumns = @JoinColumn(name = "country_id"),
        inverseJoinColumns = @JoinColumn(name = "border_id")
    )
    private List<Country> borders;

    // used only for routing algorithm
    @Transient
    private Integer distance = Integer.MAX_VALUE;

    // used only for routing algorithm
    @Transient
    private Set<Country> fastestRoute = new LinkedHashSet<>();

    public Country(CountryDTO countryDTO) {
        this.name = countryDTO.getName().getCommon();
        this.code = countryDTO.getCca3();
    }

    public void setBorders(final List<Country> borders) {
        this.borders = borders;
    }

    public void setDistance(final Integer distance) {
        this.distance = distance;
    }

    public void setFastestRoute(final Set<Country> fastestRoute) {
        this.fastestRoute = fastestRoute;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Country country = (Country) o;

        return getCode().equals(country.getCode());
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

}
