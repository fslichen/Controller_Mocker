package evolution.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import evolution.controller.AnyController;

@Service
public class AnyService {
	@Autowired
	private AnyController anyController;
	
	private Map<String, ObjectAndMethod> classAndMethods;
	
	@PostConstruct
	public void postConstruct() {
		List<Field> controllerFields = FieldUtils.getFieldsListWithAnnotation(AnyService.class, Autowired.class);
		for (Field controllerField : controllerFields) {
			Class<?> controllerClass = controllerField.getType();
			RequestMapping requestMapping = AnyController.class.getAnnotation(RequestMapping.class);
			String basePath = requestMapping == null ? "" : requestMapping.value()[0];
			classAndMethods = new LinkedHashMap<>();
			List<Method> methods = MethodUtils.getMethodsListWithAnnotation(controllerClass, PostMapping.class);
			for (Method method : methods) {
				String subPath = method.getAnnotation(PostMapping.class).value()[0];
				classAndMethods.put(basePath + subPath, new ObjectAndMethod(anyController, method, method.getParameterTypes()[0]));
			}
		}
	}
	
	public void invoke(String path, JSONObject jsonObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ObjectAndMethod objectAndMethod = classAndMethods.get(path);
		objectAndMethod.getMethod().invoke(objectAndMethod.getObject(), jsonObject.toJavaObject(objectAndMethod.getParameterType()));
	}
}
