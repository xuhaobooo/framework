import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.PaymentControllerApplication;
import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PaymentControllerApplication.class)
public class MoneyControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void listMineTest() throws Exception {

		this.mockMvc.perform(get("/money/findMine").contentType(MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void payTest() throws Exception {

		// given(this.thirdPayService.callback(map)).willReturn("success");
		Map<String, String> params = new HashMap<>();
		params.put("busiCode", "BZJ123456789");
		params.put("payAmount", "0.01");
		params.put("busiType", "BZJ");

		SimplePayPayment payment = new SimplePayPayment();
		payment.setBusiCode("BZJ123456789");
		payment.setPayAmount(new BigDecimal("0.01"));
		payment.setBusiType("BZJ");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(payment);

		this.mockMvc.perform(post("/money/pay").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void withdrawTest() throws Exception {

		// given(this.thirdPayService.callback(map)).willReturn("success");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("amount", "0.01");
		params.add("accountName", "波波2");
		params.add("bank", "中国银行深圳分行创业路支行");
		params.add("accountNo", "1231232132134324123");

		this.mockMvc.perform(post("/money/withdraw").contentType(MediaType.TEXT_PLAIN).params(params))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}
}
