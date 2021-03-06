package com.prompt.common.model;

import java.io.Serializable;

/**
 * Boolean Flag 상수
 */
public enum BooleanFlag implements EnumTypeable, Serializable {

	YES("Y"),
	NO("N");

	private String type;

	private BooleanFlag(String type) {
		this.type = type;
	}

	public String getType() {
			return type;
		}

}
