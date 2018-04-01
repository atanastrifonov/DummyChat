package dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmotionPayload extends Payload {
	
	@NotNull
	@Size(min=2, max=10)
	@Pattern(regexp="[^0-9]+")
	private String payload;
	
	public EmotionPayload() {
	}
	
	public EmotionPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
