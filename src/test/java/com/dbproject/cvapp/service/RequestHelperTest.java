package com.dbproject.cvapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class RequestHelperTest {

    @InjectMocks
    private RequestHelper requestHelper;

    @Test
    void givenSpiedRequestHelper_WhenGivenTwoDates_thenReturnCorrectNoOfBusinessDaysBetweenDays() {
        List<LocalDate> holidays = new ArrayList<>();
        List<LocalDate> businessDaysBetween = requestHelper.countBusinessDaysBetween(
                "08-06-2023", "13-06-2023", holidays);
        Assertions.assertEquals(4, businessDaysBetween.size());
    }

}
