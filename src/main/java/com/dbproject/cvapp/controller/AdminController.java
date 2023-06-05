package com.dbproject.cvapp.controller;


import com.dbproject.cvapp.dto.UserDTO;
import com.dbproject.cvapp.exception.AdminDeleteException;
import com.dbproject.cvapp.exception.NoAuthorizationException;
import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.MyUser;
import com.dbproject.cvapp.payload.response.MessageResponse;
import com.dbproject.cvapp.service.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final MyUserService myUserService;

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

    @GetMapping("check-token")
    public ResponseEntity<?> checkUser() {
        return ResponseEntity.ok(new MessageResponse("OK"));
    }


}
