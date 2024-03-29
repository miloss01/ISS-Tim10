package com.ISSUberTim10.ISSUberTim10.appUser.account.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
import com.ISSUberTim10.ISSUberTim10.appUser.account.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IMessageService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.INoteService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPasswordResetCodeService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.auth.EmailService;
import com.ISSUberTim10.ISSUberTim10.auth.JwtTokenUtil;
import com.ISSUberTim10.ISSUberTim10.auth.UserSecurity;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomExceptionWithMessage;
import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@Validated
public class AppUserController {

    @Autowired
    IAppUserService appUserService;

    @Autowired
    IRideService rideService;

    @Autowired
    INoteService noteService;

    @Autowired
    IPasswordResetCodeService passwordResetCodeService;

    @Autowired
    IMessageService messageService;

    @Autowired
    EmailService emailService;

    @Value("${postmark.receiver}")
    private String receiver;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<AllUsersDTO> getAll(Pageable page) {

        List<AppUser> users = appUserService.getAll(page);

        ArrayList<UserExpandedDTO> userExpandedDTOs = new ArrayList<>();

        for (AppUser appUser : users)
            userExpandedDTOs.add(new UserExpandedDTO(appUser));

        return new ResponseEntity<>(
                new AllUsersDTO(userExpandedDTOs.size(), userExpandedDTOs),
                HttpStatus.OK
        );



//        ArrayList<UserExpandedDTO> usersDTO = new ArrayList<>();
//        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
//        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
//        return new ResponseEntity<>(new AllUsersDTO(usersDTO.size(), usersDTO), HttpStatus.OK);
    }

    @GetMapping(value="/1", produces = "application/json")
    public ResponseEntity<UserExpandedDTO> getById(@RequestParam Integer id) {
        return new ResponseEntity<>(appUserService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value="/email", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getById(@RequestParam String email) {
        AppUser appUser = appUserService.findByEmail(email);
        return new ResponseEntity<>(new UserResponseDTO(appUser), HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/ride", produces = "application/json")
//    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<RideResponseDTO> getUsersRides(@PathVariable Integer id,
                                                         Pageable page,
                                                         @RequestParam(required = false) String from,
                                                         @RequestParam(required = false) String to) {
//        Page<Ride> resultPage = rideService.getByUser(id.longValue(), page);
//        List<Ride> rides;
//        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
//        rides = resultPage.getContent();
//        for (Ride ride : rides) {
//            ridesDTO.add(new RideDTO(ride));
//            System.out.println(ride.getId());
//        }
//        RideResponseDTO responseDTO = new RideResponseDTO(ridesDTO.size(), ridesDTO);

        AppUser appUser = appUserService.findById(id.longValue());

        List<Ride> rides;
        if (appUser.getRole() == Role.DRIVER)
            rides = rideService.getByDriver(page, (Driver) appUser);
        else if (appUser.getRole() == Role.PASSENGER)
            rides = rideService.getByPassenger(page, (Passenger) appUser);
        else
            rides = new ArrayList<>();

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

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        String role = sc.getAuthentication().getAuthorities().toString();
        AppUser user = appUserService.findByEmail(loginDTO.getEmail());

        String token = jwtTokenUtil.generateToken(
            loginDTO.getEmail(),
            Role.valueOf(role.substring(role.indexOf("_") + 1, role.length() - 1)),
            user.getId());

        return new ResponseEntity<>(
                new TokenResponseDTO(token, ""),
                HttpStatus.OK
        );

    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<String> blockUser(@PathVariable Integer id) {
//        try {
            return appUserService.blockUser(id);
//        } catch (Exception responseStatusException) {
//            return new ResponseEntity<>(responseStatusException.getMessage(), HttpStatus.NOT_FOUND);
//        }
        //return new ResponseEntity<>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<String> unblockUser(@PathVariable Integer id) {
//        try {
            return appUserService.unblockUser(id);
//        } catch (Exception responseStatusException) {
//            return new ResponseEntity<>(responseStatusException.getMessage(), HttpStatus.NOT_FOUND);
//        }
    }

    @GetMapping(value = "/{id}/message", produces = "application/json")
    @PreAuthorize(value = "hasRole('ADMIN') or @userSecurity.hasUserId(authentication, #id, 'Message')")
    public ResponseEntity<MessageResponseDTO> getMessagesById(@PathVariable Integer id, Pageable page) {

        AppUser appUser = appUserService.findById(id.longValue());

        List<Message> messages = messageService.getMessagesBySenderOrReceiver(appUser, appUser, page);

        ArrayList<MessageReceivedDTO> messageReceivedDTOs = new ArrayList<>();

        for (Message message : messages){
            messageReceivedDTOs.add(new MessageReceivedDTO(message.getId(), message.getTimeSent().toString(), message.getSender().getId(), message.getReceiver().getId(), message.getText(), message.getMessageType().toString(), message.getRideId()));
        }

        return new ResponseEntity<>(
                new MessageResponseDTO(messageReceivedDTOs.size(), messageReceivedDTOs),
                HttpStatus.OK
        );

//        ArrayList<MessageReceivedDTO> messages = new ArrayList<>();
//        messages.add(new MessageReceivedDTO(10L, "11.11.2022.", 1L, 2L, "Message", "RIDE", 3L));
//        return new ResponseEntity<>(new MessageResponseDTO(messages.size(), messages), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/message", consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "@userSecurity.hasUserId(authentication, #id, 'Message')")
    public ResponseEntity<MessageReceivedDTO> sendMessagesById(@PathVariable Integer id,
                                                               @Valid @RequestBody MessageSentDTO messageSent) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser sender = appUserService.findByEmail(authentication.getName());
//        AppUser receiver = appUserService.findById(messageSent.getReceiverId());
        AppUser receiver = appUserService.findById(id.longValue());

        Integer rideId = 0;
        if (messageSent.getRideId() != 0)
            rideId = rideService.getRideById(messageSent.getRideId()).getId().intValue();

        Message message = new Message(null, sender, receiver, messageSent.getMessage(), LocalDateTime.now(), Message.MESSAGE_TYPE.valueOf(messageSent.getType().toLowerCase()), messageSent.getRideId());

        Message saved = messageService.save(message);

        this.simpMessagingTemplate.convertAndSend("/ride-notification-message/" + receiver.getId(), messageSent);

        MessageReceivedDTO dto = new MessageReceivedDTO(saved.getId(), saved.getTimeSent().toString(), saved.getSender().getId(), saved.getReceiver().getId(), saved.getText(), saved.getMessageType().toString(), saved.getRideId());

        this.simpMessagingTemplate.convertAndSend("/chat/" + receiver.getId() + "/" + sender.getId(), dto);

        return new ResponseEntity<>( dto, HttpStatus.OK);

//        return new ResponseEntity<>(new MessageReceivedDTO(10L, "11.11.2022.", 1L, messageSent.getReceiverId(), messageSent.getMessage(), messageSent.getType(), messageSent.getRideId()), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note", consumes = "application/json", produces = "application/json")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<NoteDTO> sendNote(@PathVariable Integer id,
                                            @Valid @RequestBody NoteMessageDTO messageDTO) {

        return appUserService.sendMessage(id, messageDTO);
    }

    @GetMapping(value = "/{id}/note", produces = "application/json")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<NoteResponseDTO> getNotes(@PathVariable Integer id, Pageable page){
//        return appUserService.getNotes(id, null);
        AppUser appUser = appUserService.findById(id.longValue());
        List<Note> notes = noteService.getAll(page, appUser);

        ArrayList<NoteDTO> noteDTOs = new ArrayList<>();

        for (Note note : notes)
            noteDTOs.add(new NoteDTO(note));

        NoteResponseDTO noteResponseDTO = new NoteResponseDTO();
        noteResponseDTO.setResults(noteDTOs);
        noteResponseDTO.setTotalCount(noteDTOs.size());

        return new ResponseEntity<>(noteResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/isBlocked", produces = "application/json")
    public ResponseEntity<IsBlockedDTO> isBlocked(@RequestParam Integer id){
        return appUserService.isBlocked(id);
    }

    @PutMapping(value = "/changeActiveFlag/{id}", consumes = "application/json", produces = "application/json")
    //@PreAuthorize(value = "@userSecurity.hasUserId(authentication, #id, 'Active flag')")
    public ResponseEntity<IsActiveDTO> changeActiveFlag(@PathVariable Integer id, @RequestBody IsActiveDTO isActiveDTO) {
        AppUser appUser = appUserService.findById(id.longValue());
        appUser.setActiveFlag(isActiveDTO.isActive());
        if (appUser.isBlockedFlag()) {
            appUser.setActiveFlag(false);
        }
        appUserService.save(appUser);
        return new ResponseEntity<>(isActiveDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/activeFlag/{id}", produces = "application/json")
    //@PreAuthorize(value = "@userSecurity.hasUserId(authentication, #id, 'Active flag')")
    public ResponseEntity<IsActiveDTO> getActiveFlag(@PathVariable Integer id) {
        AppUser appUser = appUserService.findById(id.longValue());
        return new ResponseEntity<>(new IsActiveDTO(appUser.isActiveFlag()), HttpStatus.OK);
    }

    @PostMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> requestCode(@Valid @RequestBody PasswordResetCodeDTO passwordResetCodeDTO) {
        System.out.println("u post");

        AppUser appUser = appUserService.findByEmail(passwordResetCodeDTO.getEmail());

        String resetCode = "neki kod";

        PasswordResetCode passwordResetCode = new PasswordResetCode();
        passwordResetCode.setEmail(passwordResetCodeDTO.getEmail());
        passwordResetCode.setCode(resetCode);
        passwordResetCode.setDateExpiration(LocalDateTime.now().plusMinutes(10L));

        passwordResetCodeService.save(passwordResetCode);

        emailService.sendEmail(receiver, "Password reset code", resetCode);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> applyCode(@Valid @RequestBody PasswordResetCodeDTO passwordResetCodeDTO) {

        AppUser appUser = appUserService.findByEmail(passwordResetCodeDTO.getEmail());

        Optional<PasswordResetCode> code = passwordResetCodeService.findByEmail(passwordResetCodeDTO.getEmail());

        if (!code.isPresent())
            throw new CustomException("Code is not found!", HttpStatus.NOT_FOUND);

        if (!code.get().getCode().equals(passwordResetCodeDTO.getCode()))
            throw new CustomException("Code is not correct!", HttpStatus.BAD_REQUEST);

        if (code.get().getDateExpiration().isBefore(LocalDateTime.now())) {
            passwordResetCodeService.deleteById(code.get().getId());
            throw new CustomException("Code is expired!", HttpStatus.BAD_REQUEST);
        }

        appUser.setPassword(new BCryptPasswordEncoder().encode(passwordResetCodeDTO.getNewPassword()));
        appUserService.save(appUser);

        passwordResetCodeService.deleteById(code.get().getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/{id}/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@PathVariable Integer id, @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {

        AppUser appUser = appUserService.findById(id.longValue());

        if (!(new BCryptPasswordEncoder()).matches(changePasswordDTO.getOldPassword(), appUser.getPassword()))
            throw new CustomExceptionWithMessage("Current password is not matching!", HttpStatus.BAD_REQUEST);

        String codedNew = (new BCryptPasswordEncoder()).encode(changePasswordDTO.getNewPassword());
        appUser.setPassword(codedNew);
        appUserService.save(appUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDTO>> getAdmins() {
        List<AppUser> allUsers = (List<AppUser>) appUserService.getAll();
        List<UserResponseDTO> ret = new ArrayList<>();
        for (AppUser appUser : allUsers)
            if (appUser.getRole().equals(Role.ADMIN))
                ret.add(new UserResponseDTO(appUser));
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

}
