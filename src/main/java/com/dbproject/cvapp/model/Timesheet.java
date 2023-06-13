package com.dbproject.cvapp.model;

import com.dbproject.cvapp.dto.TimesheetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timesheet")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer month;

    @ManyToOne
    private MyUser user;

    @ElementCollection
    private Set<LocalDate> workDates;

    public Timesheet(TimesheetDTO dto, MyUser user) {
        this.month = dto.getMonth();
        this.user = user;
        this.workDates = new HashSet<>(dto.getWorkDates());
    }
}