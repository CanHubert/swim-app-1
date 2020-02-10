package com.can.app.swim.swimapp.enums;

import java.util.Arrays;

public enum EnumRole {
	USER("user"),
	INSTRUCTOR("instructor"),
	MANAGER("manager"),
	ADMIN("admin");

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
