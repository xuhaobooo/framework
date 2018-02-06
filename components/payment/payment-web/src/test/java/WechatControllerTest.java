import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz91online.bgms.application.PaymentControllerApplication;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PaymentControllerApplication.class)
public class WechatControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void xiadanTest() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// given(this.thirdPayService.callback(map)).willReturn("success");

		SimplePayPayment payment = new SimplePayPayment();
		payment.setBusiCode("BZJ123456789");
		payment.setPayAmount(new BigDecimal("0.01"));
		payment.setBusiType("BZJ");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(payment);

		this.mockMvc.perform(post("/wxpay/pay").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void callbackTest() throws Exception {
		
		String xmlStr = "<xml>"
				+ "<appid><![CDATA[wx0591f27a4ad89186]]></appid>"
				+ "<attach><![CDATA[支付测试]]></attach>"
				+ "<bank_type><![CDATA[CFT]]></bank_type>"
				+ "<fee_type><![CDATA[CNY]]></fee_type>"
				+ "<is_subscribe><![CDATA[Y]]></is_subscribe>"
				+ "<mch_id><![CDATA[1333917301]]></mch_id>"
				+ "<nonce_str><![CDATA[TvoFsYBYuK2pT2FM]]></nonce_str>"
				+ "<openid><![CDATA[wxd930ea5d5a258f4f]]></openid>"
				+ "<out_trade_no><![CDATA[PY940881197907464192]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<sign><![CDATA[7210A14217A995212798C7F0897FE9D8]]></sign>"
				+ "<sub_mch_id><![CDATA[10000100]]></sub_mch_id>"
				+ "<time_end><![CDATA[20140903131540]]></time_end>"
				+ "<total_fee>1</total_fee>"
				+ "<coupon_fee><![CDATA[10]]></coupon_fee>"
				+ "<coupon_count><![CDATA[1]]></coupon_count>"
				+ "<coupon_type><![CDATA[CASH]]></coupon_type>"
				+ "<coupon_id><![CDATA[10000]]></coupon_id>"
				+ "<coupon_fee><![CDATA[100]]></coupon_fee>"
				+ "<trade_type><![CDATA[JSAPI]]></trade_type>"
				+ "<transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";

		this.mockMvc.perform(post("/wxpay/notifyUrl").contentType(MediaType.APPLICATION_XML).content(xmlStr))
				.andDo(print()).andExpect(status().isOk()).andDo(print());
	}

}
