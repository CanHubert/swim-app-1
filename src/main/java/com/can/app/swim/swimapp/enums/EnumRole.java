package com.can.app.swim.swimapp.enums;

import java.util.Arrays;

public enum EnumRole {
	ROLE_USER("user"),
	ROLE_INSTRUCTOR("instructor"),
	ROLE_MANAGER("manager"),
	ROLE_ADMIN("admin");

	final String name;

	EnumRole(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static EnumRole getByName(String name){
		return Arrays.asList(EnumRole.values())
				.stream()
				.filter(role -> role.getName().equals(name))
				.findFirst()
				.orElseGet(null);
	}
}
