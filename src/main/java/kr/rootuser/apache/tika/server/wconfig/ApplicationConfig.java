package kr.rootuser.apache.tika.server.wconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = { "kr.rootuser.apache.tika.server.wconfig.*" })
@PropertySource({"classpath:user.properties"})
public class ApplicationConfig {
	
	@Autowired
	private Environment env;

	public String getTessdata() {
		return env.getProperty("parse.config.tessdata");
	}
	
}
