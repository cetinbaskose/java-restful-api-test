package uk.co.huntersix.spring.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnPersonDetails() throws Exception {
        assertThat(
            this.restTemplate.getForObject(
                "http://localhost:" + port + "/person/smith/mary",
                String.class
            )
        ).contains("Mary");
    }

    @Test
    public void shouldReturnPersonBySurname() throws Exception {
        assertThat(
            this.restTemplate.getForObject(
                "http://localhost:" + port + "/personBySurname/Archer",
                String.class
            )
        ).contains("Brian");
    }

    @Test
    public void shouldReturnNotFoundPersonBySurname() throws Exception {
        assertThat(
            this.restTemplate.getForObject(
                "http://localhost:" + port + "/personBySurname/Noone",
                String.class
            )
        ).contains("Person not found with lastname!");
    }

}