package com.ISSUberTim10.ISSUberTim10.appUser.driver.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.*;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.*;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;

import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/driver")
@Validated
public class DriverController {

    @Autowired
    private IDriverService driverService;
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IVehicleTypeService vehicleTypeService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IWorkingTimeService workingTimeService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IRideService rideService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<DriverDTO> saveDriver(@Valid @RequestBody DriverDTO driverDTO) {

        Optional<AppUser> user = appUserService.findByEmailOpt(driverDTO.getEmail());

        if (user.isPresent())
            throw new CustomException("User with that email already exists!", HttpStatus.BAD_REQUEST);

        Driver driver = new Driver();
        driver.setName(driverDTO.getName());
        driver.setLastName(driverDTO.getSurname());
        driver.setEmail(driverDTO.getEmail());
        driver.setAddress(driverDTO.getAddress());
        driver.setPhone(driverDTO.getTelephoneNumber());
        driver.setProfileImage(driverDTO.getProfilePicture());
        driver.setRole(Role.DRIVER);
        driver.setPassword(new BCryptPasswordEncoder().encode(driverDTO.getPassword()));

        Driver saved = driverService.saveDriver(driver);

        driverDTO.setId((int) (long) saved.getId());

        return new ResponseEntity<>(driverDTO, HttpStatus.OK);

    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<DriversDTO> getDrivers(Pageable page) {

        List<Driver> drivers = driverService.getAllDrivers(page);
        ArrayList<DriverDTO> driverDTOs = new ArrayList<>();

        for (Driver driver : drivers)
            driverDTOs.add(new DriverDTO(driver));

        return new ResponseEntity<>(
                new DriversDTO(driverDTOs.size(), driverDTOs),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('PASSENGER') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Driver'))")
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Driver'))")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Integer id) {

        Driver driver = driverService.getById(id.longValue());

        return new ResponseEntity<>(new DriverDTO(driver), HttpStatus.OK);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Driver'))")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Integer id,
                                                  @Valid @RequestBody DriverDTO driverDTO) {

        Driver driver = driverService.getById(id.longValue());

        driver.setName(driverDTO.getName());
        driver.setLastName(driverDTO.getSurname());
        driver.setProfileImage(driverDTO.getProfilePicture());
        driver.setPhone(driverDTO.getTelephoneNumber());
        driver.setAddress(driverDTO.getAddress());
        driver.setEmail(driverDTO.getEmail());

        if (driverDTO.getPassword() != null)
            driver.setPassword(new BCryptPasswordEncoder().encode(driverDTO.getPassword()));

        Driver saved = driverService.saveDriver(driver);

        return new ResponseEntity<>(
                new DriverDTO(saved),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Document'))")
    public ResponseEntity<List<DocumentDTO>> getDocuments(@PathVariable Integer id) {

        ArrayList<DocumentDTO> documentDTOS = new ArrayList<>();
        ArrayList<Document> documents = driverService.findDocumentsByDriverId(Long.valueOf(id));
        for (Document document: documents) {
            documentDTOS.add(new DocumentDTO(document));
        }
        return new ResponseEntity<>(documentDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
    @PreAuthorize(value = "hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable(name = "document-id") Integer documentId) {
        documentService.deleteById(documentId.longValue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Document'))")
    public ResponseEntity<DocumentDTO> postDocument(@Min(value = 3) @PathVariable Integer id,
                                                    @Valid @RequestBody DocumentDTO documentDTO) {

        Driver driver = driverService.getById(id.longValue());

        Document document = new Document();
        document.setDriver(driver);
        document.setImage(documentDTO.getDocumentImage());
        document.setTitle(documentDTO.getName());

        Document saved = documentService.save(document);

        return new ResponseEntity<>(
                new DocumentDTO(saved),
                HttpStatus.OK
        );

    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('PASSENGER') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Vehicle'))")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Integer id) {

        Driver driver = driverService.findDriverById(id);
        Vehicle vehicle = driver.getVehicle();
        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Vehicle'))")
    public ResponseEntity<VehicleDTO> saveVehicle(@PathVariable Integer id,
                                                  @Valid @RequestBody VehicleDTO vehicleDTO) {

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setRegistrationPlate(vehicleDTO.getLicenseNumber());
        vehicle.setBabyFlag(vehicleDTO.getBabyTransport());
        vehicle.setNumOfSeats(vehicleDTO.getPassengerSeats());
        vehicle.setPetsFlag(vehicleDTO.getPetTransport());
        vehicle.setCurrentCoordinates(new Coordinates(vehicleDTO.getCurrentLocation()));
        vehicle.setVehicleType(vehicleTypeService.getByName(Vehicle.VEHICLE_TYPE.valueOf(vehicleDTO.getVehicleType().toLowerCase())));

        Driver driver = driverService.getById(id.longValue());

        vehicle.setDriver(driver);
        vehicleDTO.setDriverId(id);

        Driver savedDriver = driverService.setVehicle(vehicle);

        Vehicle saved = vehicleService.saveVehicle(vehicle);
        vehicleDTO.setId((int) (long) saved.getId());

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Vehicle'))")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Integer id,
                                                    @Valid @RequestBody VehicleDTO vehicleDTO) {

        Driver driver = driverService.getById(id.longValue());

        Vehicle vehicle = vehicleService.getById(driver.getVehicle().getId());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setRegistrationPlate(vehicleDTO.getLicenseNumber());
        vehicle.setBabyFlag(vehicleDTO.getBabyTransport());
        vehicle.setNumOfSeats(vehicleDTO.getPassengerSeats());
        vehicle.setPetsFlag(vehicleDTO.getPetTransport());
        vehicle.setCurrentCoordinates(new Coordinates(vehicleDTO.getCurrentLocation(), vehicle.getCurrentCoordinates().getId()));
        vehicle.setVehicleType(vehicleTypeService.getByName(Vehicle.VEHICLE_TYPE.valueOf(vehicleDTO.getVehicleType().toLowerCase())));

        Vehicle saved = vehicleService.saveVehicle(vehicle);

        return new ResponseEntity<>(
                new VehicleDTO(saved),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Working time'))")
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable Integer id,
                                                           Pageable page,
                                                           @RequestParam(required = false) String from,
                                                           @RequestParam(required = false) String to) {

        Driver driver = driverService.getById(id.longValue());
        List<WorkingTime> workingTimes = workingTimeService.getByDriver(page, driver, from ,to);

        ArrayList<WorkingHourDTO> workingHourDTOs = new ArrayList<>();

        for (WorkingTime workingTime : workingTimes)
            workingHourDTOs.add(new WorkingHourDTO(workingTime));

        return new ResponseEntity<>(
                new WorkingHoursDTO(workingHourDTOs.size(), workingHourDTOs),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Working time')")
    public ResponseEntity<WorkingHourDTO> saveWorkingHour(@PathVariable Integer id,
                                                          @Valid @RequestBody WorkingHourDTO workingHourDTO) {

        Driver driver = driverService.getById(id.longValue());
        WorkingTime saved = new WorkingTime();
        WorkingTime workingTime = new WorkingTime();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (workingHourDTO.getStart() != null) {
            try {
                workingTime.setDriver(driver);
                workingTime.setStartTime(LocalDateTime.now());
                if (workingHourDTO.getEnd() != null ) workingTime.setEndTime(LocalDateTime.now());
            } catch (DateTimeParseException ex) {
                throw new CustomException("Wrong date format!", HttpStatus.BAD_REQUEST);
            }

            saved = workingTimeService.save(workingTime);

        }else {
            workingTime.setDriver(driver);
            try {
                if (workingHourDTO.getStart() != null ) workingTime.setStartTime(LocalDateTime.now());
                if (workingHourDTO.getEnd() != null ) workingTime.setEndTime(LocalDateTime.now());
                saved = workingTimeService.update(workingTime);
            } catch (DateTimeParseException ex) {
                throw new CustomException("Wrong date format!", HttpStatus.BAD_REQUEST);
            }
        }

        WorkingHourDTO ret = new WorkingHourDTO();
        ret.setId(saved.getId().intValue());
        if (workingHourDTO.getStart() != null ) ret.setStart(saved.getStartTime().toString());
        if (workingHourDTO.getEnd() != null ) ret.setEnd(saved.getEndTime().toString());

        if (workingHourDTO.getEnd() == null && workingHourDTO.getStart() == null ) throw new CustomException("Either field start or end time is required!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Driver'))")
    public ResponseEntity<RideResponseDTO> getDriversRide(@PathVariable Integer id,
                                                          Pageable page,
                                                          @RequestParam(required = false) String from,
                                                          @RequestParam(required = false) String to) {

        Driver driver = driverService.getById(id.longValue());

        List<Ride> rides = rideService.getByDriver(page, driver);

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

    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Working time'))")
    public ResponseEntity<WorkingHourDTO> getWorkingHour(@PathVariable(name = "working-hour-id") Integer workingHourId) {

        WorkingTime workingTime = workingTimeService.getById(workingHourId.longValue());

        WorkingHourDTO workingHourDTO = new WorkingHourDTO();
        workingHourDTO.setId(workingTime.getId().intValue());
        workingHourDTO.setStart(workingTime.getStartTime().toString());
        if (workingTime.getEndTime() != null)
            workingHourDTO.setEnd(workingTime.getEndTime().toString());

        return new ResponseEntity<>(workingHourDTO, HttpStatus.OK);

//        return new ResponseEntity<>(
//                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
//                HttpStatus.OK
//        );
    }

    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Working time')")
    public ResponseEntity<WorkingHourDTO> updateWorkingHour(@PathVariable(name = "working-hour-id") Integer workingHourId,
                                                            @Valid @RequestBody WorkingHourDTO workingHourDTO) {

        WorkingTime workingTime = workingTimeService.getById(workingHourId.longValue());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            workingTime.setEndTime(LocalDateTime.parse(workingHourDTO.getEnd(), formatter));
        } catch (DateTimeParseException ex) {
            throw new CustomException("Wrong date format!", HttpStatus.BAD_REQUEST);
        }

        WorkingTime saved = workingTimeService.saveUpdated(workingTime);

        return new ResponseEntity<>(new WorkingHourDTO(saved), HttpStatus.OK);
    }

    @GetMapping(value = "/change-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<ChangeRequestResponseDTO> getChangeRequests(){
        return driverService.getChangeRequests();
    }

    @PutMapping(value = "/change-request/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Change request'))")
    public ResponseEntity<ChangeRequestDTO> updateChangeRequest(@PathVariable Integer driverId,
                                                                @Valid @RequestBody ChangeRequestDTO requestDTO){
        return driverService.updateChangeRequest(driverId, requestDTO);
    }

    @PutMapping(value = "/change-request/approve/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<ChangeRequestDTO> approveChangeRequest(@PathVariable Integer driverId,
                                                                 @Valid @RequestBody ChangeRequestDTO requestDTO){
        return driverService.approveChangeRequest(driverId, requestDTO);
    }

}
