package com.prompt.common.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum Type 인터페이스
 */
public interface EnumTypeable {
    @JsonValue
    String getType();
}
