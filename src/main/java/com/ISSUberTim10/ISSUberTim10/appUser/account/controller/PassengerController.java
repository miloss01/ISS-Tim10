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
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.auth.EmailService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.DepartureDestinationLocationsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideResponseDTO;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private IRideService rideService;

    @Autowired
    private EmailService emailService;

    @Value("${server.port}")
    private String port;

    @Value("${postmark.receiver}")
    private String receiver;

    // Create new passenger
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PassengerResponseDTO> savePassenger(@RequestBody PassengerRequestDTO passengerRequestDTO) {

        Optional<AppUser> appUser = appUserService.findByEmail(passengerRequestDTO.getEmail());

        if (appUser.isPresent())
            throw new CustomException("User with that email already exists!", HttpStatus.BAD_REQUEST);

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
    public ResponseEntity<AllPassengersDTO> getPassengers(Pageable pageable) {
        // parameters page and size set to Integer because primitive type int doesn't allow null

        List<Passenger> passengers = passengerService.getAllPassengers(pageable);

        List<PassengerResponseDTO> passengerResponseDTOs = new ArrayList<>();

        for (Passenger passenger : passengers)
            passengerResponseDTOs.add(new PassengerResponseDTO(passenger));

        return new ResponseEntity<>(
                new AllPassengersDTO(
                        passengerResponseDTOs.size(),
                        passengerResponseDTOs
                ),
                HttpStatus.OK
        );

//        List<PassengerResponseDTO> dummyPassengers = getDummyPassengersResponseDTO();
//        AllPassengersDTO allPassengersDTO = new AllPassengersDTO(dummyPassengers.size(), dummyPassengers);
//
//        return new ResponseEntity<>(allPassengersDTO, HttpStatus.OK);
    }


    // Activating passenger with the activation email
    @GetMapping(value="/activate/{activationId}")
    public ResponseEntity<String> activatePassenger(@PathVariable(required = true) Integer activationId) {

        Optional<UserActivation> optionalUserActivation = userActivationService.findById(((Number) activationId).longValue());

        if (!optionalUserActivation.isPresent())
            throw new CustomException("Activation with entered id does not exist!", HttpStatus.NOT_FOUND);

        UserActivation userActivation = optionalUserActivation.get();

        if (userActivation.getDateExpiration().isBefore(LocalDateTime.now())) {
            userActivationService.deleteById(userActivation.getId());
            throw new CustomException("Activation expired. Register again!", HttpStatus.BAD_REQUEST);
        }

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
        passengerService.savePassenger(passenger);

        return new ResponseEntity("Successful account activation!", HttpStatus.OK);
    }


    // Returns passenger details, where the password field is always empty

    @GetMapping(value = "/{id}", produces = "application/json")
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #id, 'Passenger'))")
    // mozda mora da se dopusti i vozacu da moze da vidi podatke, jer mozda negde treba da se prikazu podaci o putnicima koje je vozio
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
//    @PreAuthorize(value = "hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #id, 'Passenger')")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable(required = true) Integer id,
                                                              @RequestBody PassengerRequestDTO passengerRequestDTO) {

        Passenger passenger = passengerService.getPassenger(id.longValue());
        passenger.setName(passengerRequestDTO.getName());
        passenger.setLastName(passengerRequestDTO.getSurname());
        passenger.setProfileImage(passengerRequestDTO.getProfilePicture());
        passenger.setPhone(passengerRequestDTO.getTelephoneNumber());
        passenger.setEmail(passengerRequestDTO.getEmail());
        passenger.setAddress(passengerRequestDTO.getAddress());
        passenger = passengerService.savePassenger(passenger);

        return new ResponseEntity<>(new PassengerResponseDTO(passenger), HttpStatus.OK);
    }


    // Returns paginated rides that can be sorted on specific field
    @GetMapping(value = "/{id}/ride", produces = "application/json")
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #id, 'Working time'))")
    public ResponseEntity<RideResponseDTO> getRides(@PathVariable Integer id,
                                                    Pageable page,
                                                    @RequestParam(required = false) String from,
                                                    @RequestParam(required = false) String to) {

        Passenger passenger = passengerService.getPassenger(id.longValue());

        List<Ride> rides = rideService.getByPassenger(page, passenger);

        ArrayList<RideDTO> rideDTOs = new ArrayList<>();

        LocalDateTime fromDate;
        LocalDateTime toDate;

        if (from == null)
            fromDate = LocalDateTime.of(2000, 1, 1, 1, 1);
        else
            fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatterWithSeconds);

        if (to == null)
            toDate = LocalDateTime.of(3000, 1, 1, 1, 1);
        else
            toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatterWithSeconds);

        for (Ride ride : rides)
            if (ride.getStartTime() != null &&
                ride.getEndTime() != null &&
                ride.getStartTime().isAfter(fromDate) &&
                ride.getEndTime().isBefore(toDate))
                rideDTOs.add(new RideDTO(ride));

        return new ResponseEntity<>(
                new RideResponseDTO(rideDTOs.size(), rideDTOs),
                HttpStatus.OK
        );

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
