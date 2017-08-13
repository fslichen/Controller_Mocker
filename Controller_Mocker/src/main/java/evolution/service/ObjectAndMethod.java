package evolution.service;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectAndMethod {
	private Object object;
	private Method method;
	private Class<?> parameterType;
}
