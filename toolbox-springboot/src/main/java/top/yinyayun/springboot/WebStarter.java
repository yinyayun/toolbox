package top.yinyayun.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 服务启动
 * 
 * @author yinyayun
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebStarter extends WebMvcConfigurerAdapter {
	public static void main(String[] args) throws IOException {
		String confPath = args[0];
		File confDir = new File(confPath);
		if (args.length == 0 || !confDir.exists()) {
			System.err.println("缺少启动参数...");
			System.exit(-1);
		}
		initLog(confDir);
		// 配置注入
		SpringApplication app = new SpringApplication(WebStarter.class);
		try (InputStream inputStream = new FileInputStream(new File(confDir, "app.properties"))) {
			Properties properties = new Properties();
			properties.load(inputStream);
			app.setDefaultProperties(properties);
		}
		app.run(args);
	}

	public static void initLog(File confDir) {
		DOMConfigurator.configure(new File(confDir, "log4j.xml").getAbsolutePath());
		LoggerFactory.getLogger(WebStarter.class).info("start robot web...");
	}

	/**
	 * 根跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	/**
	 * 资源实际位置映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**").addResourceLocations("classpath:statics/");
		super.addResourceHandlers(registry);
	}

}
