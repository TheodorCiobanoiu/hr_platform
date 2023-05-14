package com.dbproject.cvapp.repository;

import com.dbproject.cvapp.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
