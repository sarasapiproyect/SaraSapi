package com.sara.services.web.rest.response;

import java.util.List;

public class ResponseMenu {

	private List<String> menu;


	public ResponseMenu(List<String> menu) {
		super();
		this.menu = menu;
	}

	public List<String> getMenu() {
		return menu;
	}

	public void setMenu(List<String> menu) {
		this.menu = menu;
	}

	
}
