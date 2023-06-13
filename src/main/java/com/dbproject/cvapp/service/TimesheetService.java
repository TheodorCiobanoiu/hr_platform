package com.dbproject.cvapp.service;

import com.dbproject.cvapp.model.MonthlyTimesheetData;
import com.dbproject.cvapp.model.Timesheet;
import com.dbproject.cvapp.repository.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final HolidayService holidayService;
    private final VacationDayService vacationDayService;

    public Timesheet saveTimesheet(Timesheet timesheet) {
        Timesheet existingTimesheet = timesheetRepository.findByMonthAndUser_UserID(timesheet.getMonth(), timesheet.getUser().getUserID());
        if (existingTimesheet == null)
            return timesheetRepository.save(timesheet);
        else {
            existingTimesheet.setWorkDates(timesheet.getWorkDates());
            return timesheetRepository.save(existingTimesheet);
        }
    }

    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> getAllTimesheetsByUser(Integer userID) {
        return timesheetRepository.findAllByUser_UserID(userID);
    }

    public Timesheet getTimesheetByUserAndMonth(Integer month, Integer userID) {
        return timesheetRepository.findByMonthAndUser_UserID(month, userID);
    }

    public MonthlyTimesheetData getDataForGivenMonth(Integer month) {
        MonthlyTimesheetData monthlyTimesheetData = new MonthlyTimesheetData();
        monthlyTimesheetData.setNoOfFilledTimesheets(timesheetRepository.countAllByMonth(month));
        monthlyTimesheetData.setNoOfHolidays(holidayService.countAllHolidaysInMonth(month + 1));
        monthlyTimesheetData.setNoOfVacationDays(vacationDayService.countAllVacationDaysInMonth(month + 1));
        return monthlyTimesheetData;
    }

}
