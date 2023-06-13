package com.dbproject.cvapp.service;

import com.dbproject.cvapp.exception.NoRequestException;
import com.dbproject.cvapp.model.OverviewMessage;
import com.dbproject.cvapp.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OverviewMessagesService {

    private final RequestService requestService;
    private final VacationDayService vacationDayService;

    public OverviewMessage generateOverviewMessage(Integer userID) throws NoRequestException {
        OverviewMessage overviewMessage = new OverviewMessage();
        int noOfDaysTillEndOfMonth = Math.toIntExact(noOfDaysTillEndOfMonth());
        int noOfOpenRequests = Math.toIntExact(noOfOpenRequests(userID));
        int timeTillNextVacation = Math.toIntExact(timeTillNextVacationDay(userID));

        String messageForNoOfDaysTillEndOfMonth = noOfDaysTillEndOfMonth > 0 ?
                ("There are " + noOfDaysTillEndOfMonth + " days until the end of month. Please make sure to fill in you timesheet") : null;
        String messageForNoOfOpenRequests = noOfOpenRequests > 0 ?
                ("You have " + noOfOpenRequests + " open requests") : null;
        String messageForTimeTillNextVacation = timeTillNextVacation != -1 ?
                ("You have " + timeTillNextVacation + " days left untill your next vacation") : null;

        overviewMessage.setWarningForTimesheet(messageForNoOfDaysTillEndOfMonth);
        overviewMessage.setNoOfOpenRequests(messageForNoOfOpenRequests);
        overviewMessage.setTimeTillNextVacationDay(messageForTimeTillNextVacation);
        return overviewMessage;
    }

    public long noOfDaysTillEndOfMonth() {
        LocalDate currentDate = LocalDate.now(); // get current date
        LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth()); // get last day of current month

        return java.time.temporal.ChronoUnit.DAYS.between(currentDate, endOfMonth); // calculate days difference
    }

    public long noOfOpenRequests(Integer userID) throws NoRequestException {
        return requestService.getRequestsByUserId(userID)
                .stream().filter(request ->
                        request.getStatus() != Status.Accepted && request.getStatus() != Status.Rejected)
                .count();
    }

    public long timeTillNextVacationDay(Integer userID) {
        List<LocalDate> allVacationDays = vacationDayService.getAllVacationDaysAsLocalDateByUser(userID);

        // The current date
        LocalDate currentDate = LocalDate.now();

        // Find the next closest vacation day
        Optional<LocalDate> nextVacationDay = allVacationDays.stream()
                .filter(vacationDay -> vacationDay.isAfter(currentDate)) // only future dates
                .min(Comparator.naturalOrder()); // find the earliest one

        // If there's a next vacation day, return the number of days until it. Otherwise, return -1.
        return nextVacationDay
                .map(vacationDay -> ChronoUnit.DAYS.between(currentDate, vacationDay))
                .orElse(-1L);
    }
}
