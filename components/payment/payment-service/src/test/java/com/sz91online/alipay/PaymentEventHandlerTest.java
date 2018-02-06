package com.sz91online.alipay;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.common.eventbus.handler.AbstractEventHandler;

@Component
@Scope("singleton") // 必须是单例
public class PaymentEventHandlerTest extends AbstractEventHandler {

	// 方法的参数类型必须与上面发送的类型一致，所在这些类型应该在service的基本项目中定义
	@Subscribe
	public void sub(PayPayment payment) {
		System.out.println("++++++支付 callback:" + payment.getBusiCode() + "---" + payment.getPayId() + "---"
				+ payment.getPayeeResultTime());
	}
}
