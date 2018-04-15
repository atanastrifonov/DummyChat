package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dto.EmptyJsonBody;
import dto.Payload;
import service.capi.RequestService;

@RestController
public class MessageController {

	@Autowired
	RequestService requestService;

	@RequestMapping(value = "/messages/{type:send_text|send_emotion}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<EmptyJsonBody> postMessage(@PathVariable String type, @Valid @RequestBody Payload payload) {
		requestService.addRequest(payload);
		return new ResponseEntity<EmptyJsonBody>(new EmptyJsonBody(), HttpStatus.CREATED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public EmptyJsonBody processValidationError(MethodArgumentNotValidException ex) {
		return new EmptyJsonBody();
	}
}
