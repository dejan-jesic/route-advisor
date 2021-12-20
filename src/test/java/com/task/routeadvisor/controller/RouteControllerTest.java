package com.task.routeadvisor.controller;

import com.task.routeadvisor.usecase.FastestRouteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FastestRouteUseCase fastestRouteUseCase;

    @Test
    void shouldMapRequestAndParametersProperly() throws Exception {
        mockMvc.perform(get("/api/routing")
                .accept(APPLICATION_JSON)
                .param("origin", "ESP")
                .param("destination", "CZE"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

        verify(fastestRouteUseCase).findFastestRoute(eq("ESP"), eq("CZE"));
    }

    @Test
    void shouldReturn400SinceParameterIsMissing() throws Exception {
        mockMvc.perform(get("/api/routing")
                .accept(APPLICATION_JSON)
                .param("destination", "CZE"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest())
            .andReturn();

        verify(fastestRouteUseCase, never()).findFastestRoute(anyString(), anyString());
    }

    @Test
    void shouldReturn404SinceRouteDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/invalid")
                .accept(APPLICATION_JSON)
                .param("origin", "ESP")
                .param("destination", "CZE"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound())
            .andReturn();

        verify(fastestRouteUseCase, never()).findFastestRoute(anyString(), anyString());
    }

}
