package app.demo.def;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

@Data
public class MessageModel implements Serializable {

	private static final long serialVersionUID = -5785794633698274357L;

	public MessageModel(String message) {
		this.message = message;
	}
	
	public MessageModel(MessageConst messageConst) {
		this.message = messageConst.getMessage();
	}

	@Getter
	private String message;
}
