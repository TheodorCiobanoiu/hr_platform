package com.dbproject.cvapp.controller;

import com.dbproject.cvapp.dto.TimesheetDTO;
import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.MonthlyTimesheetData;
import com.dbproject.cvapp.model.MyUser;
import com.dbproject.cvapp.model.Timesheet;
import com.dbproject.cvapp.service.MyUserService;
import com.dbproject.cvapp.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("timesheet")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TimesheetController {

    private final TimesheetService timesheetService;
    private final MyUserService userService;

    @PostMapping("save")
    public Timesheet saveTimesheet(@RequestBody TimesheetDTO timesheetDTO) throws NoUserException {
        MyUser user = userService.getUserById(timesheetDTO.getUserID());
        Timesheet timesheet = new Timesheet(timesheetDTO, user);
        return timesheetService.saveTimesheet(timesheet);
    }

    @GetMapping("get/{userID}")
    public List<Timesheet> getAllTimesheetsForUser(@PathVariable Integer userID) {
        return timesheetService.getAllTimesheetsByUser(userID);
    }

    @GetMapping("get/{month}/{userID}")
    public Timesheet getTimesheetsForUserByMonth(@PathVariable Integer month, @PathVariable Integer userID) {
        return timesheetService.getTimesheetByUserAndMonth(month, userID);
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @GetMapping("get/all")
    public List<Timesheet> getAllTimesheets() {
        return timesheetService.getAllTimesheets();
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @GetMapping("monthly-data/{month}")
    public MonthlyTimesheetData getDataForGivenMonth(@PathVariable Integer month) {
        return timesheetService.getDataForGivenMonth(month);
    }

}
