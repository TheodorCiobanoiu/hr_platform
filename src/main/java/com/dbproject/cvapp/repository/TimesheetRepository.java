package com.dbproject.cvapp.repository;

import com.dbproject.cvapp.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer> {
     List<Timesheet> findAllByUser_UserID(Integer userID);

     Timesheet findByMonthAndUser_UserID(Integer month, Integer userID);

     Integer countAllByMonth(Integer month);
}
