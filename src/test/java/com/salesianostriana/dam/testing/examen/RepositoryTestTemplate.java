package com.salesianostriana.dam.testing.examen;

import com.salesianostriana.dam.testing.examen.model.DatoMeteorologico;
import com.salesianostriana.dam.testing.examen.model.DatoMeterologicoPK;
import com.salesianostriana.dam.testing.examen.repo.DatoMeteorologicoRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
//@Sql(value = "classpath:cargar_datos_meteorologicos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RepositoryTestTemplate {

	@Container
	@ServiceConnection
	static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
			.withUsername("testUser")
			.withPassword("testSecret")
			.withDatabaseName("testDatabase");

	@Autowired
	DatoMeteorologicoRepository datoMeteorologicoRepository;

	@BeforeEach
	void setUp() {

		DatoMeterologicoPK datoMeterologicoPK = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 1));
		DatoMeterologicoPK datoMeterologicoPK2 = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 2));
		DatoMeterologicoPK datoMeterologicoPK3 = new DatoMeterologicoPK("Sevilla", LocalDate.of(2023, 11, 3));
		DatoMeteorologico datoMeteorologico = new DatoMeteorologico(datoMeterologicoPK, 10);
		DatoMeteorologico datoMeteorologico2 = new DatoMeteorologico(datoMeterologicoPK2, 20);
		DatoMeteorologico datoMeteorologico3 = new DatoMeteorologico(datoMeterologicoPK3, 0);
		datoMeteorologicoRepository.save(datoMeteorologico);
		datoMeteorologicoRepository.save(datoMeteorologico2);
		datoMeteorologicoRepository.save(datoMeteorologico3);
	}

	@Test
	void existePorFechaPoblacionTest() {
		assertTrue(datoMeteorologicoRepository.existePorFechaPoblacion(LocalDate.of(2023, 11, 1), "Sevilla"));
		assertFalse(datoMeteorologicoRepository.existePorFechaPoblacion(LocalDate.of(2023, 11, 1), "Cordoba"));
	}

}
