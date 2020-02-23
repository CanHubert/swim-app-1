package com.can.app.swim.swimapp.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumRole {
	USER,
	INSTRUCTOR,
	MANAGER,
	ADMIN;

	public static EnumRole getByName(String name){
		return Arrays.stream(EnumRole.values())
				.filter(role -> role.name().equals(name.toUpperCase()))
				.findFirst()
				.orElseGet(null);//TODO poprawić nulla
	}

	public static List<EnumRole> getByNames(List<String> names){
		List<EnumRole> roles = new ArrayList<>();
		names.forEach(name-> roles.add(getByName(name)));
		return roles;
	}
}
