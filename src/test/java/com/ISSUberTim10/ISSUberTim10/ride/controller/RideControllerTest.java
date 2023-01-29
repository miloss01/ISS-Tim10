package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RideControllerTest {

    private String prefix = "http://localhost:8080/api/ride";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnActiveRideForProvidedPassenger() {
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
////Add the Jackson Message converter
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//// Note: here we are making this converter to process any kind of response,
//// not only application/*json, which is the default behaviour
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RideDTO>() {});


        RideDTO rideDTO = responseEntity.getBody();

        System.out.println(rideDTO.getId());
    }

}
