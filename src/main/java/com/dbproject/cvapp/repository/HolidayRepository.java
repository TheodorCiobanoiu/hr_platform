package com.dbproject.cvapp.repository;

import com.dbproject.cvapp.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
}
