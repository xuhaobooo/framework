import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.common.constant.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BabyControllerApplication.class)
public class BabyEvalutionControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// @Test
	public void addItemTest() throws Exception {

		BabyRequireItemDict bean = new BabyRequireItemDict();
		bean.setItemCode("TTKK5");
		bean.setItemName("测试用");
		bean.setItemPrice(200.5F);
		bean.setEnableFlag(true);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bean);

		this.mockMvc.perform(post("/itemDict/current").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)
				.session(getSession())).andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void modifyItemTest() throws Exception {

		BabyRequireItemDict bean = new BabyRequireItemDict();
		bean.setItemName("测试用2");
		bean.setItemPrice(11.5F);
		bean.setItemCode("TTKK22");
		bean.setEnableFlag(false);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(bean);

		this.mockMvc.perform(patch("/itemDict/update/71").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)
				.session(getSession())).andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void deleteItemTest() throws Exception {

		this.mockMvc.perform(delete("/itemDict/current/72").contentType(MediaType.APPLICATION_JSON_UTF8)
				.session(getSession())).andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void getAllTest() throws Exception {
		this.mockMvc
				.perform(get("/itemDict/current").contentType(MediaType.APPLICATION_JSON_UTF8).session(getSession()))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	private MockHttpSession getSession() {
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
