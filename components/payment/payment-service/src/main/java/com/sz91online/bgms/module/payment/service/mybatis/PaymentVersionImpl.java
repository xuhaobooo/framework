package com.sz91online.bgms.module.payment.service.mybatis;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sz91online.common.version.VersionService;

@Service
@Lazy(false)
@Scope("singleton")
public class PaymentVersionImpl extends VersionService {

	private final String groupId = "com.sz91online.bgms.payment";
	private final String artifactId = "bgms-payment-service";

	@Override
	public String getGroupId() {
		return groupId;
	}

	@Override
	public String getArtifactId() {
		return artifactId;
	}

}
