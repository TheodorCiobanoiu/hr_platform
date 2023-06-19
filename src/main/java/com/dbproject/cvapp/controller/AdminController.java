package com.dbproject.cvapp.controller;


import com.dbproject.cvapp.dto.UserDTO;
import com.dbproject.cvapp.exception.AdminDeleteException;
import com.dbproject.cvapp.exception.NoAuthorizationException;
import com.dbproject.cvapp.exception.NoRequestException;
import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.Holiday;
import com.dbproject.cvapp.model.MyUser;
import com.dbproject.cvapp.model.OverviewMessage;
import com.dbproject.cvapp.payload.response.MessageResponse;
import com.dbproject.cvapp.service.HolidayService;
import com.dbproject.cvapp.service.MyUserService;
import com.dbproject.cvapp.service.OverviewMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final MyUserService myUserService;
    private final HolidayService holidayService;
    private final OverviewMessagesService overviewMessagesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("all-users")

    public List<MyUser> getAllUsers() {
        return myUserService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-user/{username}/{requesterId}")
    public String deleteUserById(@PathVariable String username, @PathVariable Integer requesterId) throws NoUserException, NoAuthorizationException, AdminDeleteException {
        return myUserService.deleteUserByUsername(username, requesterId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("modify-user")
    public MyUser modifyUser(@RequestBody MyUser newUser){
        return myUserService.modifyUser(newUser);
    }

    @GetMapping("get-user/by-id/{userId}")
    public UserDTO getUserByIdDto(@PathVariable Integer userId) throws NoUserException {
        return myUserService.getUserByIdDto(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-user/by-username/{username}")
    public MyUser getUserById(@PathVariable String username) throws NoUserException {
        return myUserService.getUserByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add-holiday")
    public Holiday addHoliday(@RequestBody Holiday holiday) {
        return holidayService.addHoliday(holiday);
    }


    @GetMapping("get-all-holidays")
    public List<LocalDate> getHolidays() {
        return holidayService.getAllHolidaysAsLocalDate();
    }

    @GetMapping("get-overview-message/{userID}")
    public OverviewMessage getOverviewMessage(@PathVariable Integer userID) throws NoRequestException {
        return overviewMessagesService.generateOverviewMessage(userID);
    }

    @GetMapping("check-token")
    public ResponseEntity<?> checkUser() {
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
