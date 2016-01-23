package org.pwr.crawler.utils.htmlUtils;

import java.lang.reflect.Type;

import org.bson.types.ObjectId;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonMongoDeserializer implements JsonDeserializer<ObjectId> {

	private static JsonMongoDeserializer instance;

	private JsonMongoDeserializer() {

	}

	@Override
	public ObjectId deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {

		return new ObjectId(element.getAsJsonObject().get("$oid").getAsString());
	}

	public static JsonMongoDeserializer getDeserializer() {
		if (instance == null) {
			instance = new JsonMongoDeserializer();
			return instance;
		} else {
			return instance;
		}
	}
}
