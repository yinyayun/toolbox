package top.yinyayun.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面映射
 * 
 * @author yinyayun
 *
 */
@Controller
public class PageController {
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "statics/index.html";
	}
}
