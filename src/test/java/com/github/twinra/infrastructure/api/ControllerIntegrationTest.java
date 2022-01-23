package com.github.twinra.infrastructure.api;

import com.github.twinra.infrastructure.api.dto.DataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + "/" + path;
    }

    protected <T> T get(String path, Class<T> clazz) {
        return restTemplate.getForObject(url(path), clazz);
    }

    protected  <T> DataDto<T> getAll(String path, ParameterizedTypeReference<DataDto<T>> ptr) {
        return restTemplate.exchange(url(path), HttpMethod.GET, null, ptr).getBody();
//        return (DataDto<T>) restTemplate.getForObject(url(path), DataDto.class);
    }

    protected <T> ResponseEntity<T> post(String path, Object request, Class<T> clazz) {
        return restTemplate.postForEntity(url(path), request, clazz);
    }

    protected void put(String path, Object request) {
        restTemplate.put(url(path), request);
    }

    protected void delete(String path) {
        restTemplate.delete(url(path));
    }
}
