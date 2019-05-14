package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "store_info")
class StoreInfo {
	
	@JCID
	BigInteger id;

	BigInteger store_basic;
	
	String store_logo;
	
	String store_no;
	
	String store_name;
	
	String province;
	
	String city;
	
	String province_no;
	
	String city_no;
	
	String zone_no;
	
	String zone;
	
	String address;
	
	String phone;
	
	String introduction;
	
	BigInteger manager_id;
	
	Timestamp c_time;
	
	String latitude;
	
	String longitude;
	
	BigInteger utimekey;
	
	BigInteger duty_manager_id;
	
	BigInteger proj_id;
	
	String domain;
	
	Integer flag;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getStore_basic() {
		return store_basic;
	}

	public void setStore_basic(BigInteger store_basic) {
		this.store_basic = store_basic;
	}

	public String getStore_logo() {
		return store_logo;
	}

	public void setStore_logo(String store_logo) {
		this.store_logo = store_logo;
	}

	public String getStore_no() {
		return store_no;
	}

	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince_no() {
		return province_no;
	}

	public void setProvince_no(String province_no) {
		this.province_no = province_no;
	}

	public String getCity_no() {
		return city_no;
	}

	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}

	public String getZone_no() {
		return zone_no;
	}

	public void setZone_no(String zone_no) {
		this.zone_no = zone_no;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public BigInteger getManager_id() {
		return manager_id;
	}

	public void setManager_id(BigInteger manager_id) {
		this.manager_id = manager_id;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public BigInteger getUtimekey() {
		return utimekey;
	}

	public void setUtimekey(BigInteger utimekey) {
		this.utimekey = utimekey;
	}

	public BigInteger getDuty_manager_id() {
		return duty_manager_id;
	}

	public void setDuty_manager_id(BigInteger duty_manager_id) {
		this.duty_manager_id = duty_manager_id;
	}

	public BigInteger getProj_id() {
		return proj_id;
	}

	public void setProj_id(BigInteger proj_id) {
		this.proj_id = proj_id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
