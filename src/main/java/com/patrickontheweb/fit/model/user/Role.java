package com.patrickontheweb.fit.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.patrickontheweb.fit.model.BaseEntity;

@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity{

	private String key;
	private String display;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}	
}
