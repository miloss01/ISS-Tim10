package com.ISSUberTim10.ISSUberTim10.appUser.account.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.UserActivation;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.AllPassengersDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerRequestDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IUserActivationService;
import com.ISSUberTim10.ISSUberTim10.auth.EmailService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.DepartureDestinationLocationsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideResponseDTO;
import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "api/passenger")
public class PassengerController {

    @Autowired
    private IPassengerService passengerService;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IUserActivationService userActivationService;

    @Autowired
    private EmailService emailService;

    @Value("${server.port}")
    private String port;

    @Value("${postmark.receiver}")
    private String receiver;

    // Create new passenger
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PassengerResponseDTO> savePassenger(@RequestBody PassengerRequestDTO passengerRequestDTO) {
        UserActivation userActivation = new UserActivation();
        userActivation.setName(passengerRequestDTO.getName());
        userActivation.setLastName(passengerRequestDTO.getSurname());
        userActivation.setPhone(passengerRequestDTO.getTelephoneNumber());
        userActivation.setEmail(passengerRequestDTO.getEmail());
        userActivation.setProfileImage(passengerRequestDTO.getProfilePicture());
        userActivation.setAddress(passengerRequestDTO.getAddress());
        userActivation.setName(passengerRequestDTO.getName());
        userActivation.setPassword(new BCryptPasswordEncoder().encode(passengerRequestDTO.getPassword()));
        userActivation.setDateCreated(LocalDateTime.now());
        userActivation.setDateExpiration(userActivation.getDateCreated().plusMinutes(10L));

        UserActivation saved = userActivationService.save(userActivation);

        emailService.sendEmail(receiver, "Passenger account activation", "http://localhost:" + port + "/api/passenger/activate/" + saved.getId());

        return new ResponseEntity<>(new PassengerResponseDTO(saved), HttpStatus.OK);
    }


    // Getting multiple passengers for the need of showing a list
    @GetMapping(produces = "application/json")
    public ResponseEntity<AllPassengersDTO> getPassengers(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size) {
        // parameters page and size set to Integer because primitive type int doesn't allow null

        List<PassengerResponseDTO> dummyPassengers = getDummyPassengersResponseDTO();
        AllPassengersDTO allPassengersDTO = new AllPassengersDTO(dummyPassengers.size(), dummyPassengers);

        return new ResponseEntity<>(allPassengersDTO, HttpStatus.OK);
    }


    // Activating passenger with the activation email
    @GetMapping(value="/activate/{activationId}")
    public ResponseEntity<String> activatePassenger(@PathVariable(required = true) Integer activationId) {

        Optional<UserActivation> optionalUserActivation = userActivationService.findById(((Number) activationId).longValue());

        if (!optionalUserActivation.isPresent())
            return new ResponseEntity<>("No activation for user", HttpStatus.NOT_FOUND);

        UserActivation userActivation = optionalUserActivation.get();

        if (userActivation.getDateExpiration().isBefore(LocalDateTime.now()))
            return new ResponseEntity<>("Activation is expired", HttpStatus.BAD_REQUEST);

        Passenger passenger = new Passenger();
        passenger.setName(userActivation.getName());
        passenger.setLastName(userActivation.getLastName());
        passenger.setPhone(userActivation.getPhone());
        passenger.setEmail(userActivation.getEmail());
        passenger.setProfileImage(userActivation.getProfileImage());
        passenger.setAddress(userActivation.getAddress());
        passenger.setRole(Role.PASSENGER);
        passenger.setPassword(userActivation.getPassword());

        userActivationService.deleteById(userActivation.getId());

        return new ResponseEntity(
                new PassengerResponseDTO(passengerService.savePassenger(passenger)),
                HttpStatus.OK
        );
    }


    // Returns passenger details, where the password field is always empty

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable(required = true) Integer id) {

//        Passenger passenger = passengerService.findOne(id);
//
//        if (passenger == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//        return new ResponseEntity<>(new PassengerResponseDTO(200, "Name", "LastName",
//                "https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512",
//                "06324134", "it@email.com", "Street Ul."), HttpStatus.OK);
        return appUserService.getPassenger(id);
    }


    // Update existing passenger, non required fields send only if changed
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable(required = true) Integer id,
                                                              @RequestBody PassengerRequestDTO passengerRequestDTO) {

        //        Passenger passenger = passengerService.findOne(id);
        Passenger passenger = new Passenger();
        passenger.setName(passengerRequestDTO.getName());
        passenger.setLastName(passengerRequestDTO.getSurname());
        passenger.setProfileImage(passengerRequestDTO.getProfilePicture());
        passenger.setPhone(passengerRequestDTO.getTelephoneNumber());
        passenger.setEmail(passengerRequestDTO.getEmail());
        passenger.setAddress(passengerRequestDTO.getAddress());
        passenger.setPassword(passengerRequestDTO.getPassword());
        passenger.setId(500L); // Dummy ID
        //        passengerService.save(passenger);

//        return new ResponseEntity<>(new PassengerResponseDTO(passenger), HttpStatus.OK);
        return passengerService.updatePassenger(id, passengerRequestDTO);
    }


    // Returns paginated rides that can be sorted on specific field
    @GetMapping(value = "/{id}/ride", produces = "application/json")
    public ResponseEntity<RideResponseDTO> getRides(@PathVariable Integer id,
                                                    @RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size,
                                                    @RequestParam(required = false) String sort,
                                                    @RequestParam(required = false) String from,
                                                    @RequestParam(required = false) String to) {
        return new ResponseEntity<>(getDummyPassengerRidesDTO(), HttpStatus.OK);
    }

    private List<PassengerResponseDTO> getDummyPassengersResponseDTO() {
        List<PassengerResponseDTO> dummyPassengers = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            PassengerResponseDTO passengerResponseDTO= new PassengerResponseDTO(i, "Name", "LastName", "imgURL", "06324134", "it@email.com", "Street Ul.");
            dummyPassengers.add(passengerResponseDTO);
        }
        return dummyPassengers;
    }

    private RideResponseDTO getDummyPassengerRidesDTO() {
        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Takovska 15", 10.0, 10.0), new LocationDTO("Bulevar Evrope", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        ridesDTO.add(new RideDTO(1L, locations, "14.01.2022. 20:15", "13.01.2022. 21:15", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, null, null));

        locations.remove(0);
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Andriceva 22", 10.0, 10.0), new LocationDTO("Bulevar Oslobodjenja 20", 10.0, 10.0)));
        ridesDTO.add(new RideDTO(1L, locations, "14.01.2022. 22:15", "14.01.2022. 23:30", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, null, null));
        ridesDTO.add(new RideDTO(1L, locations, "14.05.2021. 20:15", "14.05.2021. 21:15", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, null, null));
        ridesDTO.add(new RideDTO(1L, locations, "17.10.2021. 20:15", "17.10.2021. 21:15", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, null, null));


        return new RideResponseDTO(25, ridesDTO);
    }

}
