package com.zb.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.models.room.machines.SlotMachinesActor;
import com.zb.socket.model.Msg;

public abstract class JSONUtil {
	static final Logger log = LoggerFactory.getLogger(JSONUtil.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final JsonFactory JSONFACTORY = new JsonFactory();

	public static String beanToJson(Object o) {
		StringWriter sw = new StringWriter(300);
		JsonGenerator gen = null;
		try {
			gen = JSONFACTORY.createGenerator(sw);
			MAPPER.writeValue(gen, o);
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException("JSON转换失败", e);
		} finally {
			if (gen != null) {
				try {
					gen.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static <Value> Map<String, Value> beanToMap(Object o) {
		try {
			return MAPPER.readValue(beanToJson(o), HashMap.class);
		} catch (IOException e) {
			throw new RuntimeException("转换失败", e);
		}
	}

	public static <Object> Map<String, Object> jsonToMap(String json) {
		try {
			return MAPPER.readValue(json, HashMap.class);
		} catch (IOException e) {
			throw new RuntimeException("转换失败,JSON:" + json + " !!!END!!!", e);
		}
	}

	public static List jsonToArray(String json) {
		try {
			return MAPPER.readValue(json, ArrayList.class);
		} catch (IOException e) {
			throw new RuntimeException("转换失败,JSON:" + json + " !!!END!!!", e);
		}
	}

	public static <T> List<T> jsonToArray(String json, Class<T> type) {
		try {
			JavaType javaType = getCollectionType(ArrayList.class, type);
			return MAPPER.readValue(json, javaType);
		} catch (IOException e) {
			throw new RuntimeException("转换失败,JSON:" + json + " !!!END!!!", e);
		}
	}

	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return MAPPER.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

	public static <T> T jsonToBean(String json, Class<T> type) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		try {
			//log.error("：：：：：：："+json);
			return (T) MAPPER.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException("转换失败,JSON:" + json + " !!!END!!!", e);
		}
	}
	
	static {
		MAPPER.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		// try {
		// Class GString = Class.forName("groovy.lang.GString");
		// SimpleModule gStringModule = new SimpleModule();
		// gStringModule.addSerializer(GString, new JsonSerializer() {
		// public void serialize(Object value, JsonGenerator jgen,
		// SerializerProvider provider) throws IOException,
		// JsonProcessingException {
		// jgen.writeString(String.valueOf(value));
		// }
		// });
		// MAPPER.registerModule(gStringModule);
		// } catch (Throwable ignored) {
		// }
		MAPPER.getSerializationConfig().withSerializationInclusion(
				Include.NON_NULL);
	}

	public static void main(String[] args) {
//		String json = "{\"msg\":{\"_id\":94579,\"via\":0,\"fr\":0,\"to\":100139520,\"t\": 100,\"v\":1,\"o\":{\"uid\":100655371,\"shId\":2,\"roomId\":200012},\"hasS\":false,\"ok\":false,\"c\":0,\"dt\":1476869122755,\"st\":null}}";
//		Map<String,Object> m = JSONUtil.jsonToMap(json);
//		Msg msg = (Msg) m.get("msg");
//		msg.get_id();
		String json="{\"sid\":\"6BB033C0-967F-4E13-8D2B-8514F1C23032\",\"rid\":99999900,\"time\":1488885816150,\"st\":3,\"self\":false}";
		SlotMachinesActor ac=JSONUtil.jsonToBean(json,SlotMachinesActor.class);
		System.out.println("");
	}
}
