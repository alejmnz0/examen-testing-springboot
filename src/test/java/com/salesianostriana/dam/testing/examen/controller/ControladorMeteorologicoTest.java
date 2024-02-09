package com.salesianostriana.dam.testing.examen.controller;

import com.jayway.jsonpath.JsonPath;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.service.ServicioMeteorologico;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ControladorMeteorologicoTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServicioMeteorologico servicioMeteorologico;

    HttpHeaders headers;

    Map<String, Double> mediaMensual;

    @BeforeEach
    void setUp() {
        mediaMensual = new HashMap<>();
        mediaMensual.keySet().add("Enero");
        mediaMensual.values().add(23.5);
    }


    @Test
    void mediaMensualPorPoblacion() {
        Mockito.when(servicioMeteorologico.mediaMensual(Mockito.any())).thenReturn(mediaMensual);
        mockMvc.perform(get("/meteo/media/mes/{ciudad}").contentType(MediaType.APPLICATION_JSON).content(headers))
                .andExpect(jsonPath("$", isNotEmpty()));
    }



}