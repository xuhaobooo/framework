import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.BabyControllerApplication;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BabyControllerApplication.class)
public class UserInfoControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addUserTest() throws Exception {

		SimpleBabyUserInfo userInfo = new SimpleBabyUserInfo();
		userInfo.setAddrName("asdfasdf");
		userInfo.setAddrPosX(100.12123123414123);
		userInfo.setAddrPosY(120.32142653456343);
		userInfo.setNote("我是一只小小鸟");
		userInfo.setTel("12312312");
		userInfo.setUserCode("US2231453we1");
		userInfo.setUserRole("DD");
		userInfo.setVisitCode("asfasdf");
		userInfo.setUserName("我的名字");
		userInfo.setVisitCode("13312312312");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(userInfo);

		this.mockMvc
				.perform(post("/babyUserInfo/addUserInfo").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void listRequireTest() throws Exception {
		this.mockMvc.perform(get("/babyUserInfo/findByCode/US1513825153775").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

}
