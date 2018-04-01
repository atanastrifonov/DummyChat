package controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.EmotionPayload;
import dto.TextPayload;
import entity.Request;
import service.capi.RequestService;
import service.impl.DefaultRequestService;


public class TestMessageController {

	private MessageController controller;
	private MockMvc mockMvc;
	private RequestService mockRequestService;

	public static final String SEND_TEXT_URL = "/messages/send_text";
	public static final String SEND_EMOTION_URL = "/messages/send_emotion";
	public static final String VALID_TEXT_PAYLOAD = "some text";
	public static final String VALID_EMOTION_PAYLOAD = "emotion";
	public static final String UNVALID_TEXT_PAYLOAD = "";
	public static final String UNVALID_EMOTION_PAYLOAD = "emotion payload that is toooo long";
	public static final String EMPTY_RESPONSE_BODY = "{}";

	@Before
	public void setUp() {
		this.controller = new MessageController();
		this.mockMvc = standaloneSetup(controller).build();
		this.mockRequestService = mock(DefaultRequestService.class);
		ReflectionTestUtils.setField(this.controller, "requestService", this.mockRequestService);
	}

	@Test
	public void testPostText() throws Exception {
		
		// happy case
		TextPayload validPayloadDTO = new TextPayload(VALID_TEXT_PAYLOAD);
		
		this.mockMvc.perform(
				post(SEND_TEXT_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(convertObjectToJsonBytes(validPayloadDTO)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(EMPTY_RESPONSE_BODY));
		
		verify(this.mockRequestService, times(1)).addRequest(Matchers.<Request>anyObject());
		
		// error case
		TextPayload unvalidPayloadDTO = new TextPayload(UNVALID_TEXT_PAYLOAD);
		
		this.mockMvc.perform(
				post(SEND_TEXT_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(convertObjectToJsonBytes(unvalidPayloadDTO)))
				.andExpect(status().isPreconditionFailed())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(EMPTY_RESPONSE_BODY));
		
		verifyZeroInteractions(this.mockRequestService);
	
	}
	
	@Test
	public void testPostEmotion() throws Exception {
		
		// happy case
		EmotionPayload validPayloadDTO = new EmotionPayload(VALID_EMOTION_PAYLOAD);
		
		this.mockMvc.perform(
				post(SEND_EMOTION_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(convertObjectToJsonBytes(validPayloadDTO)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(EMPTY_RESPONSE_BODY));
		
		verify(this.mockRequestService, times(1)).addRequest(Matchers.<Request>anyObject());
		
		// error case
		EmotionPayload unvalidPayloadDTO = new EmotionPayload(UNVALID_EMOTION_PAYLOAD);
		
		this.mockMvc.perform(
				post(SEND_EMOTION_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(convertObjectToJsonBytes(unvalidPayloadDTO)))
				.andExpect(status().isPreconditionFailed())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(EMPTY_RESPONSE_BODY));
		
		verifyZeroInteractions(this.mockRequestService);
		
	}
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
