package com.dbproject.cvapp.repository;

import com.dbproject.cvapp.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    Optional<List<Request>> findRequestsByUser_UserID(Integer userID);
}
