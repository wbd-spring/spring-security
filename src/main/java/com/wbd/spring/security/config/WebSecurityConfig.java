package com.wbd.spring.security.config;

import javax.sql.DataSource;

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
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.wbd.spring.security.evaluators.CustomPermissionEvaluator;
import com.wbd.spring.security.filter.VerifyCodeFilter;
import com.wbd.spring.security.service.impl.CustomUserDetailsService;

/**
 * 该类是 Spring Security 的配置类，该类的三个注解分别是
 * 标识该类是配置类、
 * 开启 Security 服务、
 * 开启全局 Securtiy 注解。


使用过滤器就已经实现了验证码功能，但其实它和 AJAX 验证差别不大。

1.AJAX 是在提交前发一个请求，请求返回成功就提交，否则不提交；

2.过滤器是先验证验证码，验证成功就让 Spring Security 验证用户名和密码；验证失败，则产生异常·。

如果我们要做的需求是用户登录是需要多个验证字段，不单单是用户名和密码，那么使用过滤器会让逻辑变得复杂，这时候可以考虑自定义 Spring Security 的验证逻辑了…


 * @author zgh
 *
 */
@Configuration //为配置类
@EnableWebSecurity//开启security服务
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启全局 Securtiy 方法注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
	private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private DataSource dataSource;
    
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
    	
    	JdbcTokenRepositoryImpl jtri = new JdbcTokenRepositoryImpl();
    	jtri.setDataSource(dataSource);
    	// 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
    	//jtri.setCreateTableOnStartup(true)
    	return jtri;
    }
    
    
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
    @Bean
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
    	
    	http.authorizeRequests()
    	.antMatchers("/getVerifyCode").permitAll() //对验证码的请求进行放行
    	.anyRequest().authenticated()
    	
    	//设置登录页
    	.and().formLogin().loginPage("/login")
    	//设置登录成功页
    	.defaultSuccessUrl("/").permitAll()//对登录成功之后调整到主页请求进行放行
    	.failureUrl("/login/error")
    	//添加一个addFilterBefore()，有两个参数， 在执行参数二之前先执行参数一， 
    	//spring security对于用户名密码登录方式通过UsernamePasswordAuthenticationFilter处理的
    	//我们在它之前执行验证码过滤器即可
    	.and().addFilterBefore(new VerifyCodeFilter(), UsernamePasswordAuthenticationFilter.class) 
    	.logout().permitAll()//对退出请求进行放行
    	.and().rememberMe()
    	.tokenRepository(persistentTokenRepository())
    	//设置有效期
    	.tokenValiditySeconds(60)
    	.userDetailsService(userDetailsService);
    	
    	
    	
    	//关闭csrf跨域
    	///关闭打开的csrf保护
    	http.csrf().disable();
    	
    }
    
    
    /**
     * 注册自定义的PermissionEvaluator
     */
    
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpression() {
    	
    	DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    	handler.setPermissionEvaluator(new CustomPermissionEvaluator());
    	return handler;
    }
    
}
