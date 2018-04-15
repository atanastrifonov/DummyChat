package service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.Payload;
import entity.Request;
import repository.capi.RequestRepository;
import service.capi.RequestService;

@Component
public class DefaultRequestService implements RequestService {

	@Autowired
	RequestRepository requestRepository;

	public void addRequest(Payload payload) {
		Request requestModel = new Request();
		requestModel.setType(RequestMethod.POST.toString());
		requestModel.setPayload(payload.getPayload());
		requestModel.setCreatedAt(new Date());

		requestRepository.addRequest(requestModel);
	}
}
