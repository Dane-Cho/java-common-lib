package com.prompt.common.dao;

import com.prompt.common.model.BooleanFlag;

import javax.persistence.Converter;

/**
 * JPA BooleanFlag Converter
 */
@Converter(autoApply = true)
public class BooleanConverter extends EnumTypeConverter<BooleanFlag> {

    public BooleanConverter() {
        super(BooleanFlag.class);
    }

}
