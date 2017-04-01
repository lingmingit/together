package com.together.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.together.test.entity.TestVo;



/***
 * 测试Spring MVC是否配置成功 后台Controller<p>
 * @author LingMin 
 * @date 2013-9-12<br>
 * @version 1.0<br>
 */
@Controller
@RequestMapping(value="/test")
public class TestController {

	
	/*****
	 * 实例化 一个 实体对象 存入 model对象中<p>
	 * 初始化 一个 新实体，可以做界面 值默认初始化
	 * @return <p>
	 * User
	 */
	@ModelAttribute("test")       
	public TestVo createFormBean() {     
		return new TestVo();       
	}
	/**
	 * 页面跳转
	 * @param request请求对象
	 * @return
	 */
	@RequestMapping(value = "/testIndex", method = RequestMethod.GET)
	public String testIndex(HttpServletRequest request) {
		return "/test/testIndex"; 
	}
	
	/**
	 * 页面跳转
	 * @param request请求对象
	 * @return
	 */
	@RequestMapping(value = "/testAdd", method = RequestMethod.GET)
	public String testAdd(HttpServletRequest request) {
		return "/test/testAdd"; 
	}
	
	
	
	/***
	 * 保存方法<p>
	 * @param model
	 * @return <p>
	 * String @Validated
	 */
	@RequestMapping(value= "/save" ,method = RequestMethod.POST )
	public String save(TestVo test, BindingResult result, ModelMap model){
		model.addAttribute("test", test);
		
		//校验没有通过
		if(result.hasErrors()){
			return "/test/testAdd";
		}
		System.out.println("test="+test+" \t test.numbers="+test.getNumbers()+" \t test.name="+test.getName());
		
		
		return "/test/testIndex"; 
	}
}
