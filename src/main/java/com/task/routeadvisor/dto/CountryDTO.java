package com.task.routeadvisor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "cca3")
public class CountryDTO {

    private CountryNameDTO name;
    private String cca3;
    private String[] borders;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryNameDTO {

        private String official;

    }

}
