package com.dbproject.cvapp.controller;


import com.dbproject.cvapp.service.VacationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("vacations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VacationDayController {

    private final VacationDayService vacationDayService;

    @GetMapping("by-user/{userID}")
    public List<LocalDate> getAllVacationDaysForUser(@PathVariable Integer userID) {
        return vacationDayService.getAllVacationDaysAsLocalDateByUser(userID);
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @GetMapping("all")
    public List<LocalDate> getAllVacationDays() {
        return vacationDayService.getAllVacationDaysAsLocalDates();
    }

}
