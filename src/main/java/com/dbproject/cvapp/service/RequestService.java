package com.dbproject.cvapp.service;


import com.dbproject.cvapp.dto.RequestDTO;
import com.dbproject.cvapp.exception.NoRequestException;
import com.dbproject.cvapp.model.Request;
import com.dbproject.cvapp.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestHelper requestHelper;

    public Request saveRequestFromDto(RequestDTO requestDTO) throws NoRequestException {
        if (requestRepository.findById(requestDTO.getId()).isEmpty()) {
            throw new NoRequestException(requestDTO.getId());
        }
        Request requestFromDto = requestRepository.findById(requestDTO.getId()).get();
        requestFromDto.setFileId(requestDTO.getFileId());
        requestFromDto.setStatus(requestDTO.getStatus());
        return requestRepository.save(requestFromDto);
    }

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<RequestDTO> getRequestsByUserId(Integer id) throws NoRequestException {
        Optional<List<Request>> optionalRequests = requestRepository.findRequestsByUser_UserID(id);
        if (optionalRequests.isEmpty()) {
            throw new NoRequestException(id);
        }
        return requestHelper.convertRequestListToDTO(optionalRequests.get());
    }

    public List<RequestDTO> getAllRequests() {
        List<Request> requests = requestRepository.findAll();
        return requestHelper.convertRequestListToDTO(requests);
    }

    public RequestDTO getRequestById(Integer id) throws NoRequestException {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            throw new NoRequestException(id);
        }
        return new RequestDTO(optionalRequest.get());
    }

}
