package com.we.core.redis;

public enum RedisChannel {

	ALL("c:a"), ROOM("c:r:"), API("c:api:"), ROOM_USER("c:r:u:"), USER("c:u:"),;// ;
	private String name;

	private RedisChannel(String name) {
		this.name = name;
	}

	public String get() {
		return name;
	}
}
