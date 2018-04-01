package controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dto.EmotionPayload;
import dto.EmptyJsonBody;
import dto.Payload;
import dto.TextPayload;
import entity.Request;
import service.capi.RequestService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	RequestService requestService;

	@RequestMapping(value = "/send_text", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<EmptyJsonBody> postText(@Valid @RequestBody TextPayload payload) {
		persistRequest(payload);
		return new ResponseEntity<EmptyJsonBody>(new EmptyJsonBody(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/send_emotion", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<EmptyJsonBody> postEmotion(@Valid @RequestBody EmotionPayload payload) {
		persistRequest(payload);
		return new ResponseEntity<EmptyJsonBody>(new EmptyJsonBody(), HttpStatus.CREATED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public EmptyJsonBody processValidationError(MethodArgumentNotValidException ex) {
		return new EmptyJsonBody();
	}
	
	private void persistRequest(Payload payload) {
		Request requestModel = new Request();
		requestModel.setType(RequestMethod.POST.toString());
		requestModel.setPayload(payload.getPayload());
		requestModel.setCreatedAt(new Date());
		requestService.addRequest(requestModel);
	}
}
