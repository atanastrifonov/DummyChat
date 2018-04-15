package deserializer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.EmotionPayload;
import dto.Payload;
import dto.TextPayload;

public class PayloadDeserializer extends JsonDeserializer<Payload> {

	@Override
	public Payload deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String theURL = request.getRequestURI();
		String[] parts = theURL.split("/");

		String pathParam = parts[parts.length - 1];

		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		Class<? extends Payload> payloadClass = null;

		if (pathParam.equals("send_text")) {
			payloadClass = TextPayload.class;
		} else if (pathParam.equals("send_emotion")) {
			payloadClass = EmotionPayload.class;
		}
		if (payloadClass == null) {
			return null;
		}
		return mapper.readValue(jp, payloadClass);
	}

}
