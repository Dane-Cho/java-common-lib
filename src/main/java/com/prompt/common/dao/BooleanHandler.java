package com.prompt.common.dao;

import com.prompt.common.model.BooleanFlag;
import org.apache.ibatis.type.MappedTypes;

/**
 * MyBatis BooleanFlag Handler
 */
@MappedTypes(BooleanFlag.class)
public class BooleanHandler extends EnumTypeHandler<BooleanFlag> {

    public BooleanHandler() {
        super(BooleanFlag.class);
    }

}
