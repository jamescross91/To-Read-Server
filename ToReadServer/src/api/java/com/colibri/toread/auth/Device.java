package com.colibri.toread.auth;

import com.colibri.toread.ToReadBaseEntity;

public class Device extends ToReadBaseEntity {
	private String device_os_id;
	private String device_make;
	private String device_model;
	private String platform;
	private String os_version;
	private String cellular_network;
	
	public String getDevice_os_id() {
		return device_os_id;
	}
	public void setDevice_os_id(String device_os_id) {
		this.device_os_id = device_os_id;
	}
	public String getDevice_make() {
		return device_make;
	}
	public void setDevice_make(String device_make) {
		this.device_make = device_make;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getCellular_network() {
		return cellular_network;
	}
	public void setCellular_network(String cellular_network) {
		this.cellular_network = cellular_network;
	}
}