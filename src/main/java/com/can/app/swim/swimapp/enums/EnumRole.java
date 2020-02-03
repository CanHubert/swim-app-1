package com.can.app.swim.swimapp.enums;

public enum EnumRole {
	ROLE_USER("user"),
	ROLE_INSTRUCTOR("instructor"),
	ROLE_MODERATOR("moderator"),
	ROLE_ADMIN("admin");

	final String name;

	EnumRole(String name){
		this.name = name;
	}

	public static EnumRole getByName(String name){
		return BaseEnum.findByName(EnumRole.class, name);
	}
}
