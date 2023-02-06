package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.LoginDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.TokenResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.exceptions.ErrorMessage;
import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.beust.ah.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RideControllerTest {

    private String prefix = "http://localhost:8081/api/ride";
    private String passToken = "";
    private String driverToken = "";
    private String adminToken = "";
    private String passHasOnePendingToken = "";
    private String passHasNoRidesToken = "";

    private String driverHasOneAcceptedToken = "";
    private String driverHasOnePendingToken = "";
    private HttpHeaders passHeader = new HttpHeaders();
    private HttpHeaders driverHeader = new HttpHeaders();
    private HttpHeaders adminHeader = new HttpHeaders();
    private HttpHeaders passHasOnePendingHeader = new HttpHeaders();
    private HttpHeaders passHasNoRidesHeader = new HttpHeaders();
    private HttpHeaders driverHasOneAcceptedHeader = new HttpHeaders();
    private HttpHeaders driverHasOnePendingHeader = new HttpHeaders();
    private String unauthorizedResponse = "Unauthorized!\r\n";
    private String accessDeniedResponse = "Access denied!\r\n";

    @Autowired
    private TestRestTemplate restTemplate;

    private void login() {
        String loginUrl = "http://localhost:8081/api/user/login";

        LoginDTO passCred = new LoginDTO("nana@DEsi.com", "333");
        LoginDTO driverCred = new LoginDTO("boki@DEsi.com", "333");
        LoginDTO adminCred = new LoginDTO("dmina@gmail.com", "333");
        LoginDTO passHasOnePendingCred = new LoginDTO("testHasOnePending@DEsi.com", "333");
        LoginDTO passHasNoRidesCred = new LoginDTO("testHasNoRidesPassenger@DEsi.com", "333");
        LoginDTO driverHasOneAcceptedCred = new LoginDTO("testCancel@DEsi.com", "333");
        LoginDTO driverHasOnePendingCred = new LoginDTO("testExecute@DEsi.com", "333");

        HttpEntity<LoginDTO> passEntity = new HttpEntity<>(passCred);
        HttpEntity<LoginDTO> driverEntity = new HttpEntity<>(driverCred);
        HttpEntity<LoginDTO> adminEntity = new HttpEntity<>(adminCred);
        HttpEntity<LoginDTO> passHasOnePendingEntity = new HttpEntity<>(passHasOnePendingCred);
        HttpEntity<LoginDTO> passHasNoRidesEntity = new HttpEntity<>(passHasNoRidesCred);
        HttpEntity<LoginDTO> driverHasOneAcceptedEntity = new HttpEntity<>(driverHasOneAcceptedCred);
        HttpEntity<LoginDTO> driverHasOnePendingEntity = new HttpEntity<>(driverHasOnePendingCred);

        ResponseEntity<TokenResponseDTO> passResult = restTemplate.postForEntity(loginUrl, passEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> driverResult = restTemplate.postForEntity(loginUrl, driverEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> adminResult = restTemplate.postForEntity(loginUrl, adminEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> passHasOnePendingResult = restTemplate.postForEntity(loginUrl, passHasOnePendingEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> passHasNoRidesResult = restTemplate.postForEntity(loginUrl, passHasNoRidesEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> driverHasOneAcceptedResult = restTemplate.postForEntity(loginUrl, driverHasOneAcceptedEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> driverHasOnePendingResult = restTemplate.postForEntity(loginUrl, driverHasOnePendingEntity, TokenResponseDTO.class);

        passToken = passResult.getBody().getAccessToken();
        driverToken = driverResult.getBody().getAccessToken();
        adminToken = adminResult.getBody().getAccessToken();
        passHasOnePendingToken = passHasOnePendingResult.getBody().getAccessToken();
        passHasNoRidesToken = passHasNoRidesResult.getBody().getAccessToken();
        driverHasOneAcceptedToken = driverHasOneAcceptedResult.getBody().getAccessToken();
        driverHasOnePendingToken = driverHasOnePendingResult.getBody().getAccessToken();

        passHeader.set("Authorization", "Bearer " + passToken);
        driverHeader.set("Authorization", "Bearer " + driverToken);
        adminHeader.set("Authorization", "Bearer " + adminToken);
        passHasOnePendingHeader.set("Authorization", "Bearer " + passHasOnePendingToken);
        passHasNoRidesHeader.set("Authorization", "Bearer " + passHasNoRidesToken);
        driverHasOneAcceptedHeader.set("Authorization", "Bearer " + driverHasOneAcceptedToken);
        driverHasOnePendingHeader.set("Authorization", "Bearer " + driverHasOnePendingToken);
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

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForWithdrawRide() {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix + "/1/withdraw",
                HttpMethod.PUT,
                null,
                Void.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
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
    public void shouldNotAddRideBecausePassengerAlreadyInRide() {
        RideCreationDTO rideCreationDTO = this.createRideCreationDTOWithPassengerAlreadyInRide();

        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasOnePendingHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot create a ride while you have one already pending!");
    }

    @Test
    public void shouldNotAddRideBecauseNullLocations() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.setLocations(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (locations) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecauseInvalidLongitudeCoordinateBiggerThan180() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.getLocations().get(0).getDeparture().setLongitude(250.0);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (longitude) cannot be bigger than 180!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecauseInvalidLongitudeCoordinateSmallerThanMinus180() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.getLocations().get(0).getDeparture().setLongitude(-250.0);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (longitude) cannot be smaller than -180!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecauseNullPassengers() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.setPassengers(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (passengers) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecausePassengerWithNullId() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.getPassengers().get(0).setId(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (id) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecausePassengerWithInvalidEmail() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.getPassengers().get(0).setEmail("nullnullnullnullnullnullnullsdfsdfsdfsfnullnullnullnullnullnullnull@llnull.com");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (email) does not have valid format.\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecauseInvalidVehicleType() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.setVehicleType("non existent vehicle type");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (vehicle type) has incorrect value!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotAddRideBecauseNullVehicleType() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.setVehicleType(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (vehicleType) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldAddRideWithAllValidParameters() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();

        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldNotAddRideBecauseNoVehiclesAvailable() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();
        rideCreationDTO.setVehicleType(Vehicle.VEHICLE_TYPE.van.toString());

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, passHasNoRidesHeader),
                String.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForAddingRide() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, null),
                Void.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldReturnAccessDeniedBecauseProvidedDriverTokenForAddingRide() {
        RideCreationDTO rideCreationDTO = this.createValidRideCreationDTO();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix,
                HttpMethod.POST,
                new HttpEntity<>(rideCreationDTO, driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldCancelPendingRide() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/9/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOnePendingHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(rideDTO.getId()).isEqualTo(9L);
        assertThat(rideDTO.getStatus()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());
    }

    @Test
    public void shouldCancelAcceptedRide() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<RideDTO> responseEntity = restTemplate.exchange(
                prefix + "/15/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),
                RideDTO.class
        );

        RideDTO rideDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(rideDTO.getId()).isEqualTo(15L);
        assertThat(rideDTO.getStatus()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());
    }

    @Test
    public void shouldNotCancelRideBecauseRideDoesNotExist() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/100/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Ride does not exist!");
    }

    @Test
    public void shouldNotCancelRideBecauseRideIsNotPendingOrAccepted() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<ErrorMessage> responseEntity = restTemplate.exchange(
                prefix + "/11/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),
                ErrorMessage.class
        );

        ErrorMessage errorMessage = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorMessage.getMessage()).isEqualTo("Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForCancelRide() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix + "/9/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, null),
                Void.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldNotCancelRideBecauseAccessDenied() {
        String reasonForCancelling = "Here is the reason.";
        ReasonDTO reasonDTO = new ReasonDTO(reasonForCancelling);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/9/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, passHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldNotCancelRideBecauseNullReason() {
        ReasonDTO reasonDTO = new ReasonDTO();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/9/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (reason) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void shouldNotCancelRideBecauseReasonLongerThan250Characters() {
        ReasonDTO reasonDTO = new ReasonDTO();
        reasonDTO.setReason("ResponseEntity<String> responseEntity = restTemplate.exchange(\n" +
                "                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),\n" +
                "        String errorMessage = responseEntity.getBody();\n" +
                "        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);\nResponseEntity<String> responseEntity = restTemplate.exchang");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/9/cancel",
                HttpMethod.PUT,
                new HttpEntity<>(reasonDTO, driverHasOneAcceptedHeader),
                String.class
        );

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (reason) cannot be longer than 250 characters!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void shouldSaveFavoriteLocation() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<FavoriteLocationResponseDTO> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                FavoriteLocationResponseDTO.class
        );

        FavoriteLocationResponseDTO responseDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseDTO.getFavoriteName()).isEqualTo(requestDTO.getFavoriteName());
        assertThat(responseDTO.getVehicleType()).isEqualTo(requestDTO.getVehicleType());
        assertThat(responseDTO.getLocations().get(0).getDeparture().getAddress()).isEqualTo(requestDTO.getLocations().get(0).getDeparture().getAddress());
        assertThat(responseDTO.getLocations().get(0).getDeparture().getLongitude()).isEqualTo(requestDTO.getLocations().get(0).getDeparture().getLongitude());
        assertThat(responseDTO.getLocations().get(0).getDeparture().getLatitude()).isEqualTo(requestDTO.getLocations().get(0).getDeparture().getLatitude());
        assertThat(responseDTO.getLocations().get(0).getDestination().getAddress()).isEqualTo(requestDTO.getLocations().get(0).getDestination().getAddress());
        assertThat(responseDTO.getLocations().get(0).getDestination().getLongitude()).isEqualTo(requestDTO.getLocations().get(0).getDestination().getLongitude());
        assertThat(responseDTO.getLocations().get(0).getDestination().getLatitude()).isEqualTo(requestDTO.getLocations().get(0).getDestination().getLatitude());
        assertThat(responseDTO.getPassengers().get(0).getEmail()).isEqualTo("nana@DEsi.com");
    }

    @Test
    public void shouldNotSaveFavoriteLocationBecauseNullName() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        requestDTO.setFavoriteName(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                String.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (favorite name) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotSaveFavoriteLocationBecauseNameLongerThan40Characters() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        requestDTO.setFavoriteName("nullnullnullnullnullnullnullnullnullnullnull");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                String.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (favorite name) cannot be longer than 40 characters!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotSaveFavoriteLocationBecauseNullLocations() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        requestDTO.setLocations(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                String.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (locations) is required!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void shouldNotSaveFavoriteLocationBecauseInvalidVehicleType() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        requestDTO.setVehicleType("This is not a valid vehicle type string");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                String.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String errorMessage = responseEntity.getBody();
        assertThat(errorMessage).isEqualTo("Field (vehicle type) has incorrect value!\n");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldNotSaveFavoriteLocationBecauseAnyOfPassengersDoesNotExist() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ArrayList<UserResponseDTO> passengers = (ArrayList<UserResponseDTO>) requestDTO.getPassengers();
        passengers.add(new UserResponseDTO(150L, "nonexistent@email.com"));
        requestDTO.setPassengers(passengers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, passHeader),
                String.class
        );


        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Passenger does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForSaveFavoriteLocation() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO),
                Void.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldNotSaveFavoriteLocationBecauseAccessDenied() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.POST,
                new HttpEntity<>(requestDTO, driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldGetFavoriteLocations() {
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.GET,
                new HttpEntity<>(passHasNoRidesHeader),
                List.class
        );

        List<FavoriteLocationResponseDTO> responseDTO = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDTO.size()).isEqualTo(1);
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForGetFavoriteLocations() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.GET,
                new HttpEntity<>(requestDTO),
                Void.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldNotGetFavoriteLocationsBecauseAccessDenied() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites",
                HttpMethod.GET,
                new HttpEntity<>(requestDTO, driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }

    @Test
    public void shouldDeleteFavoriteLocation() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites/1",
                HttpMethod.DELETE,
                new HttpEntity<>(passHasNoRidesHeader),
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldNotDeleteFavoriteLocationBecauseItDoesNotExist() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites/1123",
                HttpMethod.DELETE,
                new HttpEntity<>(passHasNoRidesHeader),
                String.class
        );

        String text = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(text).isEqualTo("Favorite location does not exist!");
    }

    @Test
    public void shouldReturnUnauthorizedBecauseNoTokenForDeleteFavoriteLocations() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                prefix + "/favorites/1",
                HttpMethod.DELETE,
                new HttpEntity<>(requestDTO),
                Void.class
        );


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldNotDeleteFavoriteLocationsBecauseAccessDenied() {
        FavoriteLocationRequestDTO requestDTO = createValidFavoriteLocationRequestDTO();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                prefix + "/favorites/1",
                HttpMethod.DELETE,
                new HttpEntity<>(requestDTO, driverHeader),
                String.class
        );

        String text = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(text).isEqualTo(accessDeniedResponse);
    }


    private RideCreationDTO createValidRideCreationDTO() {
        Coordinates departureCoordinates = new Coordinates();
        departureCoordinates.setId(1L);
        departureCoordinates.setLatitude(14);
        departureCoordinates.setLongitude(15);
        departureCoordinates.setAddress("Some address");

        Coordinates destinationCoordinates = new Coordinates();
        destinationCoordinates.setId(1L);
        destinationCoordinates.setLatitude(14);
        destinationCoordinates.setLongitude(15);
        destinationCoordinates.setAddress("Some address");

        ArrayList<DepartureDestinationLocationsDTO> locationDTOS = new ArrayList<>();
        DepartureDestinationLocationsDTO ddDTO = new DepartureDestinationLocationsDTO();
        ddDTO.setDeparture(new LocationDTO(departureCoordinates));
        ddDTO.setDestination(new LocationDTO(destinationCoordinates));
        locationDTOS.add(ddDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(12L);
        userDTO.setEmail("testHasNoRides@DEsi.com");
        ArrayList<UserDTO> passengers = new ArrayList<>();
        passengers.add(userDTO);

        RideCreationDTO rideCreationDTO = new RideCreationDTO();
        rideCreationDTO.setLocations(locationDTOS);
        rideCreationDTO.setPassengers(passengers);
        rideCreationDTO.setVehicleType("standard");
        rideCreationDTO.setBabyTransport(false);
        rideCreationDTO.setPetTransport(false);
        rideCreationDTO.setDistance(10.0);
        rideCreationDTO.setPrice(100.0);
        rideCreationDTO.setStartTime(LocalDateTime.now().plusMinutes(10).toString());

        return rideCreationDTO;
    }

    private RideCreationDTO createRideCreationDTOWithPassengerAlreadyInRide() {
        Coordinates departureCoordinates = new Coordinates();
        departureCoordinates.setId(1L);
        departureCoordinates.setLatitude(14);
        departureCoordinates.setLongitude(15);
        departureCoordinates.setAddress("Some address");

        Coordinates destinationCoordinates = new Coordinates();
        destinationCoordinates.setId(1L);
        destinationCoordinates.setLatitude(14);
        destinationCoordinates.setLongitude(15);
        destinationCoordinates.setAddress("Some address");

        ArrayList<DepartureDestinationLocationsDTO> locationDTOS = new ArrayList<>();
        DepartureDestinationLocationsDTO ddDTO = new DepartureDestinationLocationsDTO();
        ddDTO.setDeparture(new LocationDTO(departureCoordinates));
        ddDTO.setDestination(new LocationDTO(destinationCoordinates));
        locationDTOS.add(ddDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(13L);
        userDTO.setEmail("testHasOnePending@DEsi.com");
        ArrayList<UserDTO> passengers = new ArrayList<>();
        passengers.add(userDTO);

        RideCreationDTO rideCreationDTO = new RideCreationDTO();
        rideCreationDTO.setLocations(locationDTOS);
        rideCreationDTO.setPassengers(passengers);
        rideCreationDTO.setVehicleType("standard");
        rideCreationDTO.setBabyTransport(false);
        rideCreationDTO.setPetTransport(false);
        rideCreationDTO.setDistance(10.0);
        rideCreationDTO.setPrice(100.0);

        return rideCreationDTO;
    }

    private FavoriteLocationRequestDTO createValidFavoriteLocationRequestDTO() {
        RideCreationDTO rideCreationDTO = createValidRideCreationDTO();
        FavoriteLocationRequestDTO requestDTO = new FavoriteLocationRequestDTO();
        requestDTO.setFavoriteName("Home - work");
        requestDTO.setLocations(rideCreationDTO.getLocations());
        ArrayList<UserResponseDTO> passengers = new ArrayList<>();
        passengers.add(new UserResponseDTO(1L, "nana@DEsi.com"));
        requestDTO.setPassengers(passengers);
        requestDTO.setVehicleType("standard");
        requestDTO.setBabyTransport(true);
        requestDTO.setPetTransport(false);
        return requestDTO;
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
