import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.BabyControllerApplication;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.baby.domain.BabyEvaluation;
import com.sz91online.common.constant.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BabyControllerApplication.class)
public class BabyRequireItemControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	//@Test
	public void addBabyTest() throws Exception {

		BabyEvaluation bean = new BabyEvaluation();
		bean.setLevel("LOW");
		bean.setNotes("东asdfasfdsadfasdf");
		bean.setSendUserCode("us123456");
		bean.setReceiveUserCode("13312312312");
		bean.setRequireCode("123123123");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bean);

		this.mockMvc.perform(post("/babyEvaluate/current").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void findByCodeTest() throws Exception {

		this.mockMvc
				.perform(get("/babyEvaluate/findByRequireCode/fsdfasdfasdf")
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void findMineTest() throws Exception {
		this.mockMvc.perform(get("/babyEvaluate/findMine").contentType(MediaType.APPLICATION_JSON_UTF8).session(getSession())).andDo(print())
				.andExpect(status().isOk()).andDo(print());
	}
	
	private MockHttpSession getSession() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
		UserSessionInfo user = new UserSessionInfo();
		user.setCode("Usjigou1231543434");
		user.setUserName("机构游客");
		session.setAttribute(Constants.SessionUser,user);
		MockHttpSession result = new MockHttpSession();
		result.setAttribute(Constants.SessionUser,user);
		return result;
	}
}
