package com.dbproject.cvapp.controller;


import com.dbproject.cvapp.dto.RequestDTO;
import com.dbproject.cvapp.exception.NoRequestException;
import com.dbproject.cvapp.model.Request;
import com.dbproject.cvapp.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("request")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {

    private final RequestService requestService;

    @PostMapping("add")
    public Request saveRequestFromDto(@RequestBody RequestDTO request) throws NoRequestException {
        return requestService.saveRequestFromDto(request);
    }

    @GetMapping("get/user/{userId}")
    public List<RequestDTO> getRequestsByUserId(@PathVariable Integer userId) throws NoRequestException {
        return requestService.getRequestsByUserId(userId);
    }

    @GetMapping("get/{id}")
    public RequestDTO getRequestById(@PathVariable Integer id) throws NoRequestException {
        return requestService.getRequestById(id);
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @GetMapping("get/all")
    public List<RequestDTO> getAllRequests() {
        return requestService.getAllRequests();
    }

}
