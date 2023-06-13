package com.dbproject.cvapp.service;

import com.dbproject.cvapp.model.Holiday;
import com.dbproject.cvapp.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public List<LocalDate> getAllHolidaysAsLocalDate() {
        List<Holiday> holidaysList = holidayRepository.findAll();
        List<LocalDate> holidays = new ArrayList<>();
        holidaysList.forEach(holiday -> holidays.add(holiday.getDate()));
        return holidays;
    }

    public Integer countAllHolidaysInMonth(Integer month) {
        List<LocalDate> holidays = getAllHolidaysAsLocalDate();
        return ((int) holidays.stream().filter(holiday -> holiday.getMonth().getValue() == month).count());
    }

    public Holiday addHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }
}
