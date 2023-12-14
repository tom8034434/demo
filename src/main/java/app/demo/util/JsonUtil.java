package app.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import app.demo.def.MessageConst;
import app.demo.def.MessageModel;

public class JsonUtil {

	private JsonUtil() {
	}

	private static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public static String objToString(Object obj) throws JsonProcessingException {
		return ow.writeValueAsString(obj);
	}

	public static String format(String message) throws JsonProcessingException {
		return ow.writeValueAsString(new MessageModel(message));
	}

	public static String format(MessageConst messageConst) throws JsonProcessingException {
		return format(messageConst.getMessage());
	}
}
