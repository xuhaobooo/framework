package com.sz91online.common.version;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

public abstract class VersionService {

	private static Set<VersionBean> modelList = new HashSet<VersionBean>();

	public abstract String getGroupId();

	public abstract String getArtifactId();

	public Set<VersionBean> getAllModel() {
		return modelList;
	}

	@PostConstruct
	public void registe() {
		VersionBean bean = getInfo();
		modelList.add(bean);
		System.out.println("================add model:" + bean.getModelName() + "-" + bean.getVersion());
	}

	private VersionBean getInfo() {
		String file = "/META-INF/maven/" + getGroupId() + "/" + getArtifactId()+"/pom.properties";
		URL fileURL = this.getClass().getResource(file);
		if (fileURL != null) {
			try {
				Properties props = new Properties();
				props.load(this.getClass().getResourceAsStream(file));
				VersionBean bean = new VersionBean();
				bean.setModelName(props.getProperty("artifactId"));
				bean.setVersion(props.getProperty("version"));
				return bean;
			} catch (IOException e) {
				
			}
		}
		VersionBean bean = new VersionBean();
		bean.setModelName(getArtifactId());
		bean.setVersion("not_load");
		return bean;
	}
}
