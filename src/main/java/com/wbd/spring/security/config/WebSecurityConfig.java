package com.wbd.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wbd.spring.security.service.impl.CustomUserDetailsService;

/**
 * 该类是 Spring Security 的配置类，该类的三个注解分别是
 * 标识该类是配置类、
 * 开启 Security 服务、
 * 开启全局 Securtiy 注解。


 * @author zgh
 *
 */
@Configuration //为配置类
@EnableWebSecurity//开启security服务
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启全局 Securtiy 方法注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
	private CustomUserDetailsService userDetailsService;
    
    /**
	 * 全局用户信息<br>
	 * 方法上的注解@Autowired的意思是，方法的参数的值是从spring容器中获取的<br>
	 * 即参数AuthenticationManagerBuilder是spring中的一个Bean
	 *
	 *首先将我们自定义的 userDetailsService 注入进来，在 configure() 方法中使用 auth.userDetailsService() 方法替换掉默认的 userDetailsService。

这里我们还指定了密码的加密方式（5.0 版本强制要求设置），因为我们数据库是明文存储的，所以明文返回即可，如下所示：
	 * @param auth 认证管理
	 * @throws Exception 用户认证异常信息
	 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(rawPassword.toString());
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		});
    }
    
    //过滤一些静态资源,或者请求的方法， 自定义即可
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/css/**","/js/**");
    }
    
    
    /**
	 * 认证管理
	 * 
	 * @return 认证管理对象
	 * @throws Exception
	 *             认证异常信息
	 */

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * http安全配置
	 * 
	 * @param http
	 *            http安全对象
	 * @throws Exception
	 *             http安全异常信息
	 */
    //对http请求的过滤配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.authorizeRequests().anyRequest().authenticated()
    	//设置登录页
    	.and().formLogin().loginPage("/login")
    	//设置登录成功页
    	.defaultSuccessUrl("/").permitAll()
    	.and().logout().permitAll();
    	
    	//关闭csrf跨域
    	///关闭打开的csrf保护
    	http.csrf().disable();
    	
    }
}
