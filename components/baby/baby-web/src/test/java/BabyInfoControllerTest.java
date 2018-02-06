import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.BabyControllerApplication;
import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BabyControllerApplication.class)
public class BabyInfoControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	//@Test
	public void addBabyTest() throws Exception {

		BabyInfo babyInfo = new BabyInfo();
		babyInfo.setBabyBirthday(new Date());
		babyInfo.setBabyName("小宝贝");
		babyInfo.setBabySex("M");
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(babyInfo);

		this.mockMvc.perform(post("/babyInfo/current").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void findByUserCodeTest() throws Exception {

		this.mockMvc.perform(get("/babyInfo/findByUserCode").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	//@Test
	public void removeBabyTest() throws Exception {

		this.mockMvc.perform(delete("/babyInfo/removeBaby/Bb1513772779410").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	
	//@Test
	public void findByCodeTest() throws Exception {

		this.mockMvc.perform(delete("/babyInfo/findByCode/Bb1513772779410").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void updateTest() throws Exception {
		BabyInfo babyInfo = new BabyInfo();
		babyInfo.setBabyBirthday(new Date());
		babyInfo.setBabyName("修改后的小宝贝");
		babyInfo.setBabySex("F");
		babyInfo.setBabyCode("Bb1513772414181");
		babyInfo.setId(3L);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(babyInfo);

		this.mockMvc
				.perform(patch("/babyInfo/update/3").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
}
