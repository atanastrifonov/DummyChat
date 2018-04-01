package dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TextPayload extends Payload {
	
	@NotNull
	@Size(min=1, max=160)
	private String payload;
	
	public TextPayload() {
	}
	
	public TextPayload(String payload) {
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
