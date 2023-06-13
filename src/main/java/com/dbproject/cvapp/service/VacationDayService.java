package com.dbproject.cvapp.service;

import com.dbproject.cvapp.dto.RequestDTO;
import com.dbproject.cvapp.model.MyUser;
import com.dbproject.cvapp.model.Status;
import com.dbproject.cvapp.model.VacationDay;
import com.dbproject.cvapp.repository.UserRepository;
import com.dbproject.cvapp.repository.VacationDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class VacationDayService {

    private final VacationDayRepository vacationDayRepository;
    private final UserRepository userRepository;

    public void addVacationDays(RequestDTO requestDTO) {
        if (requestDTO.getStatus() == Status.Accepted) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate startDate = LocalDate.parse(requestDTO.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(requestDTO.getEndDate(), formatter);
            long numOfDaysBetween = requestDTO.getNoOfDays();
            List<LocalDate> localDateList = IntStream.iterate(0, i -> i + 1)
                    .limit(numOfDaysBetween)
                    .mapToObj(startDate::plusDays)
                    .toList();
            List<VacationDay> vacationDays = new ArrayList<>();
            MyUser user = userRepository.findByUserID(requestDTO.getUserDTO().getUserID());
            localDateList.forEach(localDate -> {
                VacationDay vacationDay = new VacationDay();
                vacationDay.setDate(localDate);
                vacationDay.setUser(user);
                vacationDays.add(vacationDay);
            });
            vacationDayRepository.saveAll(vacationDays);
        }
    }

    public List<LocalDate> getAllVacationDaysAsLocalDateByUser(Integer userID) {
        List<VacationDay> vacationDays = vacationDayRepository.findAllByUser_UserID(userID);
        List<LocalDate> vacationDaysAsLocalDate = new ArrayList<>();

        vacationDays.forEach(vacationDay -> vacationDaysAsLocalDate.add(vacationDay.getDate()));
        return vacationDaysAsLocalDate;
    }

    public List<LocalDate> getAllVacationDaysAsLocalDates() {
        List<VacationDay> vacationDays = vacationDayRepository.findAll();
        List<LocalDate> vacationDaysAsLocalDate = new ArrayList<>();

        vacationDays.forEach(vacationDay -> vacationDaysAsLocalDate.add(vacationDay.getDate()));
        return vacationDaysAsLocalDate;
    }

    public Integer countAllVacationDaysInMonth(Integer month) {
        List<LocalDate> vacationDays = getAllVacationDaysAsLocalDates();
        return ((int) vacationDays.stream().filter(vacationDay -> vacationDay.getMonth().getValue() == month).count());
    }
}
