package com.dbproject.cvapp.repository;

import com.dbproject.cvapp.model.VacationDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationDayRepository extends JpaRepository<VacationDay, Integer> {
    List<VacationDay> findAllByUser_UserID(Integer userID);
}
