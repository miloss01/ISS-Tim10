package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.LoginDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.TokenResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.exceptions.ErrorMessage;
import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.DepartureDestinationLocationsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideCreationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RideControllerTest {

    private String prefix = "http://localhost:8080/api/ride";
    private String passToken = "";
    private String driverToken = "";
    private String adminToken = "";
    private String passHasOnePendingToken = "";

    private String passHasNoRidesToken = "";
    private HttpHeaders passHeader = new HttpHeaders();
    private HttpHeaders driverHeader = new HttpHeaders();
    private HttpHeaders adminHeader = new HttpHeaders();
    private HttpHeaders passHasOnePendingHeader = new HttpHeaders();
    private HttpHeaders passHasNoRidesHeader = new HttpHeaders();

    private String unauthorizedResponse = "Unauthorized!\r\n";
    private String accessDeniedResponse = "Access denied!\r\n";

    @Autowired
    private TestRestTemplate restTemplate;

    private void login() {
        String loginUrl = "http://localhost:8080/api/user/login";

        LoginDTO passCred = new LoginDTO("nana@DEsi.com", "333");
        LoginDTO driverCred = new LoginDTO("boki@DEsi.com", "333");
        LoginDTO adminCred = new LoginDTO("dmina@gmail.com", "333");
        LoginDTO passHasOnePendingCred = new LoginDTO("testHasOnePending@DEsi.com", "333");
        LoginDTO passHasNoRidesCred = new LoginDTO("testHasNoRidesPassenger@DEsi.com", "333");

        HttpEntity<LoginDTO> passEntity = new HttpEntity<>(passCred);
        HttpEntity<LoginDTO> driverEntity = new HttpEntity<>(driverCred);
        HttpEntity<LoginDTO> adminEntity = new HttpEntity<>(adminCred);
        HttpEntity<LoginDTO> passHasOnePendingEntity = new HttpEntity<>(passHasOnePendingCred);
        HttpEntity<LoginDTO> passHasNoRidesEntity = new HttpEntity<>(passHasNoRidesCred);

        ResponseEntity<TokenResponseDTO> passResult = restTemplate.postForEntity(loginUrl, passEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> driverResult = restTemplate.postForEntity(loginUrl, driverEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> adminResult = restTemplate.postForEntity(loginUrl, adminEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> passHasOnePendingResult = restTemplate.postForEntity(loginUrl, passHasOnePendingEntity, TokenResponseDTO.class);
        ResponseEntity<TokenResponseDTO> passHasNoRidesResult = restTemplate.postForEntity(loginUrl, passHasNoRidesEntity, TokenResponseDTO.class);

        passToken = passResult.getBody().getAccessToken();
        driverToken = driverResult.getBody().getAccessToken();
        adminToken = adminResult.getBody().getAccessToken();
        passHasOnePendingToken = passHasOnePendingResult.getBody().getAccessToken();
        passHasNoRidesToken = passHasNoRidesResult.getBody().getAccessToken();

        passHeader.set("Authorization", "Bearer " + passToken);
        driverHeader.set("Authorization", "Bearer " + driverToken);
        adminHeader.set("Authorization", "Bearer " + adminToken);
        passHasOnePendingHeader.set("Authorization", "Bearer " + passHasOnePendingToken);
        passHasNoRidesHeader.set("Authorization", "Bearer " + passHasNoRidesToken);
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
    public void shouldAddRide() {
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
        userDTO.setId(11L);
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

}
