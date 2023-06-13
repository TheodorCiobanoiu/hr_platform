package com.dbproject.cvapp.service;

import com.dbproject.cvapp.dto.UserDetailsDTO;
import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.UserDetails;
import com.dbproject.cvapp.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final MyUserService myUserService;

    public UserDetails saveUserDetails(UserDetailsDTO userDetailsDTO) throws NoUserException {
        UserDetails userDetails = new UserDetails();
        userDetails.setMyUser(myUserService.getUserById(userDetailsDTO.getUserId()));
        userDetails.setJobType(userDetailsDTO.getJobType());
        userDetails.setNoOfVacationDays(24);
        return userDetailsRepository.save(userDetails);
    }
}
