package com.sz91online.common.version;

public class VersionBean {

	private String modelName;
	private String version;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.modelName == null || this.version == null || obj == null) {
			return false;
		}

		if (obj instanceof VersionBean) {
			VersionBean version = (VersionBean) obj;
			if (this.modelName.equals(version.getModelName()) && this.version.equals(version.getVersion())) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

}
