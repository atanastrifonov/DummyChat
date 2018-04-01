package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Request;
import repository.capi.RequestRepository;
import service.capi.RequestService;

@Component
public class DefaultRequestService implements RequestService {
	
	@Autowired
	RequestRepository requestRepository;

	public void addRequest(Request request) {
		requestRepository.addRequest(request);		
	}
}
