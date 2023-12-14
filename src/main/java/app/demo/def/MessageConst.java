package app.demo.def;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageConst {
	
	SUCCESS("SUCCESS!"), 
	IS_NOT_EXIST(" is not exist!"),
	ALREADY_EXIST(" is already exist!");

	@Getter
	private final String message;

}
