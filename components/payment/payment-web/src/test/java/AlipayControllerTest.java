import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
import com.sz91online.bgms.application.PaymentControllerApplication;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;
import com.sz91online.common.constant.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PaymentControllerApplication.class)
public class AlipayControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
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

		this.mockMvc.perform(post("/alipay/pay").contentType(MediaType.APPLICATION_JSON_UTF8).content(json).session(getSession()))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	// @Test
	public void callbackTest() throws Exception {

		this.mockMvc.perform(post("/alipay/notifyUrl").contentType(MediaType.TEXT_PLAIN)
				.param("notify_time", "2017-12-12 12:13:14").param("notify_type", "trade_status_sync")
				.param("notify_id", "ac05099524730693a8b330c5ecf72da9786").param("app_id", "2014072300007148")
				.param("charset", "utf-8").param("version", "1.0").param("sign_type", "RSA2").param("sign", "sign")
				.param("trade_no", "2013112011001004330000121536").param("out_trade_no", "6823789339978248")
				.param("buyer_id", "2088102122524333").param("seller_id", "2088101106499364")).andDo(print())
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
