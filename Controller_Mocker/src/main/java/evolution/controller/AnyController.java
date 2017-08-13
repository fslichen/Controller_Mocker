package evolution.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import evolution.service.AnyService;

@RestController
@RequestMapping("/base")
public class AnyController {
	@Autowired
	private AnyService anyService;
	
	@PostMapping("/post")
	public AnyDto post(@RequestBody AnyDto anyDto) {
		System.out.println(anyDto);
		return anyDto;
	}
	
	@GetMapping("/invoke")
	public void invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		anyService.invoke("/base/post", JSONObject.parseObject("{\"name\":\"Chen\"}"));
	}
}
