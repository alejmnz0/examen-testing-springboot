package com.salesianostriana.dam.testing.examen.service;

import com.salesianostriana.dam.testing.examen.exception.ResourceNotFoundException;
import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServicioMeteorologicoTest {

    @Mock
    DatoMeteorologicoRepository datoMeteorologicoRepository;

    @InjectMocks
    ServicioMeteorologico servicioMeteorologico;

    private List<DatoMeteorologico> datosVacios;
    private List<DatoMeteorologico> datos;
    private String poblacion;

    @BeforeEach
    void setUp() {
        datosVacios = new ArrayList<>();
        datos = new ArrayList<>();
        DatoMeterologicoPK datoMeterologicoPK = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 1));
        DatoMeterologicoPK datoMeterologicoPK2 = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 2));
        DatoMeterologicoPK datoMeterologicoPK3 = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 3));
        DatoMeteorologico datoMeteorologico = new DatoMeteorologico(datoMeterologicoPK, 10);
        DatoMeteorologico datoMeteorologico2 = new DatoMeteorologico(datoMeterologicoPK2, 20);
        DatoMeteorologico datoMeteorologico3 = new DatoMeteorologico(datoMeterologicoPK3, 0);
        datos.add(datoMeteorologico);
        datos.add(datoMeteorologico2);
        datos.add(datoMeteorologico3);
        poblacion = "Sevilla";
    }

    @Test
    void mediaMensualPoblacionEmptyTest() {

        Mockito.when(datoMeteorologicoRepository.buscarPorPoblacion(Mockito.any())).thenReturn(datosVacios);

        ResourceNotFoundException exeptionErr = assertThrows(ResourceNotFoundException.class, () -> servicioMeteorologico.mediaMensual(poblacion));
    }

    @Test
    void mediaMensualTest(){
        double mediaPrecipitaciones = 10;
        Mockito.when(datoMeteorologicoRepository.buscarPorPoblacion(Mockito.any())).thenReturn(datos);
        Assertions.assertFalse(servicioMeteorologico.mediaMensual(poblacion).isEmpty());
        Assertions.assertTrue(servicioMeteorologico.mediaMensual(poblacion).containsKey("NOVIEMBRE"));
        Assertions.assertEquals(mediaPrecipitaciones,servicioMeteorologico.mediaMensual(poblacion).get("NOVIEMBRE"));
    }


}