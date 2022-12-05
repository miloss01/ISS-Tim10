package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.domain.Passenger;
import com.ISSUberTim10.ISSUberTim10.domain.Vehicle;
import com.ISSUberTim10.ISSUberTim10.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/passenger")
public class PassengerController {

//    @Autowired
//    private PassengerService passengerService;

    // Create new passenger
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PassengerResponseDTO> savePassenger(@RequestBody PassengerRequestDTO passengerRequestDTO) {

        Passenger passenger = new Passenger();
        passenger.setName(passengerRequestDTO.getName());
        passenger.setLastName(passengerRequestDTO.getLastName());
        passenger.setProfileImage(passengerRequestDTO.getProfileImage());
        passenger.setPhone(passengerRequestDTO.getPhone());
        passenger.setEmail(passengerRequestDTO.getEmail());
        passenger.setAddress(passengerRequestDTO.getAddress());
        passenger.setPassword(passengerRequestDTO.getPassword());

        passenger.setId(100L); // Dummy ID
        return new ResponseEntity<>(new PassengerResponseDTO(passenger), HttpStatus.OK);
    }


    // Getting multiple passengers for the need of showing a list
    @GetMapping
    public ResponseEntity<AllPassengersDTO> getPassengers(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size) {
        // parameters page and size set to Integer because primitive type int doesn't allow null

        List<PassengerResponseDTO> dummyPassengers = getDummyPassengersResponseDTO();
        AllPassengersDTO allPassengersDTO = new AllPassengersDTO(dummyPassengers.size(), dummyPassengers);

        return new ResponseEntity<>(allPassengersDTO, HttpStatus.OK);
    }


    // Activating passenger with the activation email
    @PostMapping(value="/{activationId}")
    public ResponseEntity<String> activatePassenger(@PathVariable(required = true) Integer activationId) {
        return new ResponseEntity<>("Successful account activation", HttpStatus.OK);
    }


    // Returns passenger details, where the password field is always empty
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable(required = true) Integer id) {

//        Passenger passenger = passengerService.findOne(id);
//
//        if (passenger == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new PassengerResponseDTO(200, "Name", "LastName",
                "imgURL", "06324134", "it@email.com", "Street Ul."), HttpStatus.OK);
    }


    // Update existing passenger, non required fields send only if changed
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable(required = true) Integer id,
                                                              @RequestBody PassengerRequestDTO passengerRequestDTO) {

        //        Passenger passenger = passengerService.findOne(id);
        Passenger passenger = new Passenger();
        passenger.setName(passengerRequestDTO.getName());
        passenger.setLastName(passengerRequestDTO.getLastName());
        passenger.setProfileImage(passengerRequestDTO.getProfileImage());
        passenger.setPhone(passengerRequestDTO.getPhone());
        passenger.setEmail(passengerRequestDTO.getEmail());
        passenger.setAddress(passengerRequestDTO.getAddress());
        passenger.setPassword(passengerRequestDTO.getPassword());
        passenger.setId(500L); // Dummy ID
        //        passengerService.save(passenger);

        return new ResponseEntity<>(new PassengerResponseDTO(passenger), HttpStatus.OK);
    }


    // Returns paginated rides that can be sorted on specific field
    @GetMapping(value = "/{id}/ride", produces = "application/json")
    public ResponseEntity<PassengerRidesDTO> getRides(@PathVariable Integer id,
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

    private PassengerRidesDTO getDummyPassengerRidesDTO() {
        UserResponseDTO driver = new UserResponseDTO(1, "driver@c.com", "VOZAC");
        UserResponseDTO passenger = new UserResponseDTO(2, "passenger@c.com", "PUTNIK");
        List<UserResponseDTO> passengers = new ArrayList<>();
        passengers.add(passenger);
        LocationDTO location = new LocationDTO("Street Ul.", 24.32121, 43.32423);
        List<LocationDTO> locations = new ArrayList<>();
        locations.add(location);
        PassengerRideDTO ride = new PassengerRideDTO(15, LocalDateTime.now(), LocalDateTime.now(), 350,
                driver, passengers, locations, true, false, Vehicle.VEHICLE_TYPE.luxury, 23);
        List<PassengerRideDTO> rides = new ArrayList<>();
        rides.add(ride);
        return new PassengerRidesDTO(25, rides);
    }

}
