import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sz91online.bgms.application.MainControllerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainControllerApplication.class)
public class SecurityControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	SecurityManager securityManager;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		SecurityUtils.setSecurityManager(securityManager);
	}

	// @Test
	public void loginTest() throws Exception {
		this.mockMvc.perform(post("/security/login?loginName=asdf&password=asdf").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void tokenLoginTest() throws Exception {
		this.mockMvc.perform(post("/security/tokenLogin?userCode=asdf&token=asdf").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void captchaTest() throws Exception {
		this.mockMvc.perform(post("/security/captcha/13434755542").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}
	
	
}
