package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import deserializer.PayloadDeserializer;

@JsonDeserialize(using = PayloadDeserializer.class)
public abstract class Payload {
	public abstract String getPayload();
}
