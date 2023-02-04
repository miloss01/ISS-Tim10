package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.LoginDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.TokenResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.exceptions.ErrorMessage;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.PanicExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.ReasonDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RideControllerTest {

    private final String prefix = "http://localhost:8081/api/ride";
    private String passToken = "";
    private String driverToken = "";
    private String adminToken = "";
    private HttpHeaders passHeader = new HttpHeaders();
    private HttpHeaders driverHeader = new HttpHeaders();
    private HttpHeaders adminHeader = new HttpHeaders();
    private final String unauthorizedResponse = "Unauthorized!\r\n";
    private final String accessDeniedResponse = "Access denied!\r\n";

    @Autowired
    private TestRestTemplate restTemplate;
    private final RestTemplate templateFor401 = new RestTemplate();

    private void login() {
        templateFor401.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        templateFor401.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });
        String loginUrl = "http://localhost:8081/api/user/login";

        LoginDTO passCred = new LoginDTO("nana@DEsi.com", "333");
        LoginDTO driverCred = new LoginDTO("boki@DEsi.com", "333");
        LoginDTO adminCred = new LoginDTO("dmina@gmail.com", "333");

        HttpEntity<LoginDTO> passEntity = new HttpEntity<>(passCred);
        HttpEntity<LoginDTO> driverEntity = new HttpEntity<>(driverCred);
        HttpEntity<LoginDTO> adminEntity = new HttpEntity<>(adminCred);

        ResponseEntity<TokenResponseDTO> passResult = restTemplate.postForEntity(loginUrl, passEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> driverResult = restTemplate.postForEntity(loginUrl, driverEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> adminResult = restTemplate.postForEntity(loginUrl, adminEntity, TokenResponseDTO.class);

        passToken = passResult.getBody().getAccessToken();
        driverToken = driverResult.getBody().getAccessToken();
        adminToken = adminResult.getBody().getAccessToken();

        passHeader.set("Authorization", "Bearer " + passToken);
        driverHeader.set("Authorization", "Bearer " + driverToken);
        adminHeader.set("Authorization", "Bearer " + adminToken);
    }

    @BeforeAll
    public void setup() {
        login();
    }

    @Test
    public void shouldReturnActiveRideForProvidedDriver() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/active",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(1L);
        assertThat(rideDTO.getDriver().getId()).isEqualTo(2L);
    }

    @Test
    public void shouldNotReturnActiveRideForProvidedDriver() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/4/active",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Active ride does not exist!");
    }

    @Test
    public void shouldNotReturnActiveRideBecauseProvidedDriverDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/1/active",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Driver does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForDriverActiveRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/active",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnAccessDeniedBecauseProvidedPassengerTokenForDriverActiveRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/active",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldReturnAcceptedRideForProvidedDriver() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/accepted",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(2L);
        assertThat(rideDTO.getDriver().getId()).isEqualTo(2L);
    }

    @Test
    public void shouldNotReturnAcceptedRideForProvidedDriver() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/4/accepted",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Accepted ride does not exist!");
    }

    @Test
    public void shouldNotReturnAcceptedRideBecauseProvidedDriverDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/1/active",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Driver does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForDriverAcceptedRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/accepted",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnAccessDeniedBecauseProvidedPassengerTokenForDriverAcceptedRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/accepted",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldReturnPendingRideForProvidedDriver() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/pending",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(3L);
        assertThat(rideDTO.getDriver().getId()).isEqualTo(2L);
    }

    @Test
    public void shouldNotReturnPendingRideForProvidedDriver() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/4/pending",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("pending ride does not exist!");
    }

    @Test
    public void shouldNotReturnPendingRideBecauseProvidedDriverDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/1/pending",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Driver does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForDriverPendingRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/pending",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnAccessDeniedBecauseProvidedPassengerTokenForDriverPendingRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/driver/2/pending",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldReturnActiveRideForProvidedPassenger() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/active",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(1L);
        assertThat(rideDTO.getPassengers()).usingRecursiveFieldByFieldElementComparatorOnFields("id").contains(new UserDTO(1L, ""));
    }

    @Test
    public void shouldNotReturnActiveRideForProvidedPassenger() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/3/active",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("active ride does not exist!");
    }

    @Test
    public void shouldNotReturnActiveRideBecauseProvidedPassengerDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/2/active",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Passenger does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForPassengerActiveRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/active",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnAccessDeniedBecauseProvidedDriverTokenForPassengerActiveRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/active",
                HttpMethod.GET,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldReturnAcceptedRideForProvidedPassenger() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/accepted",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(2L);
        assertThat(rideDTO.getPassengers()).usingRecursiveFieldByFieldElementComparatorOnFields("id").contains(new UserDTO(1L, ""));
    }

    @Test
    public void shouldNotReturnAcceptedRideForProvidedPassenger() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/3/accepted",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("accepted ride does not exist!");
    }

    @Test
    public void shouldNotReturnAcceptedRideBecauseProvidedPassengerDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/2/accepted",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Passenger does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForPassengerAcceptedRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/accepted",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnPendingRideForProvidedPassenger() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/passenger/6/pending",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(5L);
        assertThat(rideDTO.getPassengers()).usingRecursiveFieldByFieldElementComparatorOnFields("id").contains(new UserDTO(6L, ""));
    }

    @Test
    public void shouldNotReturnPendingRideForProvidedPassenger() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/3/pending",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("pending ride does not exist!");
    }

    @Test
    public void shouldNotReturnPendingRideBecauseProvidedPassengerDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/2/pending",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Passenger does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForPassengerPendingRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/passenger/1/pending",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldReturnRideById() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/1",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rideDTO.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldNotReturnRideById() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100",
                HttpMethod.GET,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForRideById() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1",
                HttpMethod.GET,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldWithdrawPendingRide() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/5/withdraw",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(rideDTO.getId()).isEqualTo(5L);
        assertThat(rideDTO.getStatus()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());
    }

    @Test
    public void shouldWithdrawAcceptedRide() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/4/withdraw",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(rideDTO.getId()).isEqualTo(4L);
        assertThat(rideDTO.getStatus()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());
    }

    @Test
    public void shouldNotWithdrawRideBecauseRideDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100/withdraw",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotWithdrawRideBecauseRideIsNotPendingOrAccepted() {
        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix + "/1/withdraw",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot cancel a ride that is not in status PENDING or STARTED!");
    }

    @Test // pada iz nekog debilnog razloga, ne kontam kako da sredim
    public void shouldReturnUnauthorizedBecauseNoTokenForWithdrawRide() {
        ResponseEntity<String> responseEntity = templateFor401.exchange(
                prefix + "/1/withdraw",
                HttpMethod.PUT,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldNotWithdrawBecauseAccessDenied() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/withdraw",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldActivatePanicPassenger() {
        ResponseEntity<PanicExpandedDTO> response = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO("aaaaaaaaaa"), passHeader),
                PanicExpandedDTO.class
        );
        PanicExpandedDTO panicExpandedDTO = response.getBody();
        assert panicExpandedDTO != null;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(panicExpandedDTO.getReason()).isEqualTo("aaaaaaaaaa");
        assertThat(panicExpandedDTO.getRide().getId()).isEqualTo(1);
        assertThat(panicExpandedDTO.getUser().getId()).isEqualTo(1);
    }

    @Test
    public void shouldActivatePanicDriver() {
        ResponseEntity<PanicExpandedDTO> response = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO("aaaaaaaaaa"), driverHeader),
                PanicExpandedDTO.class
        );
        PanicExpandedDTO panicExpandedDTO = response.getBody();
        assert panicExpandedDTO != null;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(panicExpandedDTO.getReason()).isEqualTo("aaaaaaaaaa");
        assertThat(panicExpandedDTO.getRide().getId()).isEqualTo(1);
        assertThat(panicExpandedDTO.getUser().getId()).isEqualTo(2);
    }

    @Test
    public void shouldNotActivatePanicNoRideFound() {
        ResponseEntity<String> response = restTemplate.exchange(
                prefix + "/1111/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO("aaaaaaaaaa"), driverHeader),
                String.class
        );
        String text = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotActivatePanicNoRequestBody() {
        ResponseEntity<String> response = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(null, adminHeader),
                String.class
        );
        String text = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void shouldNotActivatePanicWrongRequestBody() {
        ResponseEntity<String> response = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO(), adminHeader),
                String.class
        );
        String text = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(text).isEqualTo("Field (reason) is required!\n");
    }

    @Test
    public void shouldNotActivatePanicWrongAuthorisation() {
        ResponseEntity<String> response = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO("aaaaaaaaaa"), adminHeader),
                String.class
        );
        String text = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldNotActivatePanicNoAuthorisation() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/panic",
                HttpMethod.PUT,
                new HttpEntity<>(new ReasonDTO("aaaaaaaaaa"), null),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldAcceptRide() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/9/accept",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );
        RideDTO rideDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert rideDTO != null;
        assertThat(rideDTO.getStatus()).isEqualTo("accepted");
    }

    @Test
    public void shouldNotAcceptRideBecauseRideDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100/accept",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotAcceptRideBecauseRideIsNotAccepted() {
        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix + "/1/accept",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assert errorMessage != null;
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot accept a ride that is not in status PENDING!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForAcceptRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/accept",
                HttpMethod.PUT,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldNotAcceptBecauseAccessDenied() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/accept",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldStartRide() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/10/start",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );
        RideDTO rideDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert rideDTO != null;
        assertThat(rideDTO.getStatus()).isEqualTo("active");
    }

    @Test
    public void shouldNotStartRideBecauseRideDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100/start",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotStartRideBecauseRideIsNotAccepted() {
        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix + "/1/start",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assert errorMessage != null;
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot start a ride that is not in status ACCEPTED!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForStartRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/start",
                HttpMethod.PUT,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldNotStartBecauseAccessDenied() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/start",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldEndRide() {
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/11/end",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                RideDTO.class
        );
        RideDTO rideDTO = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert rideDTO != null;
        assertThat(rideDTO.getStatus()).isEqualTo("finished");
    }

    @Test
    public void shouldNotEndRideBecauseRideDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100/end",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotEndRideBecauseRideIsNotAccepted() {
        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix + "/2/end",
                HttpMethod.PUT,
                new HttpEntity<>(driverHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assert errorMessage != null;
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForEndRide() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/end",
                HttpMethod.PUT,
                null,
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(text).isEqualTo(unauthorizedResponse);
    }

    @Test
    public void shouldNotEndBecauseAccessDenied() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/1/end",
                HttpMethod.PUT,
                new HttpEntity<>(passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

//    @Test
//    public void shouldGetAllRides() {
//        Map<String, String> params = new HashMap<>();
//        params.put("from", "11.11.2002. 12:00:00");
//        params.put("to", "11.11.2040. 12:00:00");
//        org.springframework.data.domain.Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));
//        String urlTemplate = UriComponentsBuilder.fromHttpUrl(prefix + "/getAllRides")
//                .queryParam("from", "11.11.2002. 12:00:00")
//                .queryParam("to", "11.11.2040. 12:00:00")
//                .encode()
//                .toUriString();
//        ResponseEntity<RideResponseDTO> response = restTemplate.exchange(
//                prefix + "/getAllRides",
//                HttpMethod.GET,
//                new HttpEntity<>(pageable),
//                RideResponseDTO.class
//        );
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//    }
}
