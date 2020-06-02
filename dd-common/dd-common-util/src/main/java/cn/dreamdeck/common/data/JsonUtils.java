package cn.dreamdeck.common.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


public class JsonUtils {


	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static String writeValueAsString(Object obj) {
		
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	public static <T> T readValue(String content,Class<T> entityClass) {
		
		try {
			return (T)mapper.readValue(content, entityClass);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	} 
	
	public static List readAsObjects(String content, Class clazz) {
		
		List beanList = null;
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
		
		try {
			beanList =  (List)mapper.readValue(content, javaType);
			
			return beanList;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
		
	}
}
