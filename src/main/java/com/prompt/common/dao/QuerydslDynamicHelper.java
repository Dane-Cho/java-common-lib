package com.prompt.common.dao;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;

/**
 * QueryDSL Dynamic Helper
 */
public class QuerydslDynamicHelper {

    public static BooleanExpression eq(SimpleExpression expression, Object value) {
        BooleanExpression booleanExpression = null;
        if(value != null) {
            if(!(value instanceof String && ((String)value).isEmpty())){
                booleanExpression = expression.eq(value);
            }
        }
        return booleanExpression;
    }

    public static BooleanExpression goe(NumberExpression expression, Number value) {
        BooleanExpression booleanExpression = null;
        if(value != null) {
            booleanExpression = expression.goe(value);
        }
        return booleanExpression;
    }

    public static BooleanExpression loe(NumberExpression expression, Number value) {
        BooleanExpression booleanExpression = null;
        if(value != null) {
            booleanExpression = expression.loe(value);
        }
        return booleanExpression;
    }

    public static BooleanExpression like(StringExpression expression, String value) {
        BooleanExpression booleanExpression = null;
        if(!Strings.isNullOrEmpty(value)) {
            booleanExpression = expression.like(value);
        }
        return booleanExpression;
    }

}
