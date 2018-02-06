import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.common.constant.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BabyControllerApplication.class)
public class RequireControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// @Test
	public void addUserTest() throws Exception {

		SimpleBabyRequire require = new SimpleBabyRequire();
		require.setAddrName("baoan");
		require.setAddrPosX(111.023423421341234);
		require.setAddrPosY(222.065787567845676);
		require.setBabyAge(10);
		require.setBabyName("babyName");
		require.setBabySex("M");
		require.setCreditCode("LS");
		require.setStartTime(new Date());
		require.setEndTime(new Date());
		require.setFeeAmount(55.0F);
		require.setPayMore(10F);

		List<BabyRequireItem> items = new ArrayList<>();
		BabyRequireItem item = new BabyRequireItem();
		item.setItemCode("GW");
		item.setItemName("陪购物");
		item.setItemPrice(55F);
		items.add(item);

		BabyRequireItem item2 = new BabyRequireItem();
		item2.setItemCode("CF");
		item2.setItemName("吃饭");
		item2.setItemPrice(100F);
		items.add(item2);

		require.setItems(items);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(require);

		this.mockMvc.perform(post("/babyRequire/publish").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void applyTest() throws Exception {
		this.mockMvc.perform(post("/babyRequire/apply/Rq1514444054890").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void findApplyTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/findApply/Rq1513490187869").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void selectTest() throws Exception {
		this.mockMvc.perform(post("/babyRequire/select/1").contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andDo(print());
	}

	// @Test
	public void arriveTest() throws Exception {
		this.mockMvc.perform(post("/babyRequire/arrive/Tk1513492273584").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void provideFinishTest() throws Exception {
		this.mockMvc.perform(post("/babyRequire/provideFinish/Tk1513492273584").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void customerFinishTest() throws Exception {
		this.mockMvc.perform(post("/babyRequire/customerFinish/Rq1513490273254").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void listMIneTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/listMine?startDate=2017-12-21 00:00:00&endDate=2017-12-27 23:59:59")
				.contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void listRequireTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/listRequire?startTime=2017-12-12 12:12:12")
				.contentType(MediaType.TEXT_PLAIN).session(getSession())).andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void findOneTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/findOne/123123123").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void findTaskByTaskCodeTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/findTaskByTaskCode/ardsfg").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void findTaskByRequireCodeTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/findTaskByRequireCode/123123123").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void listTasksTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/listTasks").contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andDo(print());
	}

	// @Test
	public void listDoneTasksTest() throws Exception {
		this.mockMvc.perform(get("/babyRequire/listDoneTasks").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void cancelTest() throws Exception {
		this.mockMvc.perform(delete("/babyRequire/cancel/Rq1234567").contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andDo(print());
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
