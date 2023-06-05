package com.dbproject.cvapp.service;

import com.dbproject.cvapp.dto.RequestDTO;
import com.dbproject.cvapp.model.Request;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class RequestHelper {
    public List<LocalDate> countBusinessDaysBetween(final String startDateString, final String endDateString,
                                                    final List<LocalDate> holidays) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);
        endDate = endDate.plusDays(1L);
        // Validate method arguments
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween(" + startDate + ", " + endDate + ", " + holidays + ")");
        }

        // Predicate 1: Is a given date is a holiday
        Predicate<LocalDate> isHoliday = holidays::contains;

        // Predicate 2: Is a given date is a weekday
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        // Iterate over stream of all dates and check each day against any weekday or holiday

        return startDate.datesUntil(endDate)
                .filter(isWeekend.or(isHoliday).negate())
                .collect(Collectors.toList());
    }

    public List<RequestDTO> convertRequestListToDTO(List<Request> requests) {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        requests.forEach(request -> {
            RequestDTO requestDTO = new RequestDTO(request);
            requestDTOS.add(requestDTO);
        });
        return requestDTOS;
    }
}
