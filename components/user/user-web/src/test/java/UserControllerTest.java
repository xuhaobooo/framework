import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.UserControllerApplication;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.common.constant.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserControllerApplication.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	//@Test
	@Transactional
	@Rollback(true)
	public void addUserTest() throws Exception {
		User user = new User();

		user.setLoginName("13434755542");
		user.setLastName("tt");
		user.setCathcha("233571");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);

		this.mockMvc.perform(post("/user/current").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void listUserTest() throws Exception{
		this.mockMvc.perform(get("/user/listUser?status=N").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	private MockHttpSession getSession() throws Exception {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession(true);
		UserSessionInfo user = new UserSessionInfo();
		user.setCode("Usjigou1231543434");
		user.setUserName("机构游客");
		session.setAttribute(Constants.SessionUser, user);
		MockHttpSession result = new MockHttpSession();
		result.setAttribute(Constants.SessionUser, user);
		return result;
	}
}
