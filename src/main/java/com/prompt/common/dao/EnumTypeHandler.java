package com.prompt.common.dao;

import com.google.common.base.Strings;
import com.prompt.common.model.EnumTypeable;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBastis EnumType Handler
 */
public abstract class EnumTypeHandler<T extends Enum<T> & EnumTypeable> implements TypeHandler<T> {

    private final Class<T> targetClass;

    public EnumTypeHandler(Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException();
        }
        this.targetClass = targetClass;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getType());
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        return getEnumTypeByResult(result);
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return getEnumTypeByResult(result);
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        return getEnumTypeByResult(result);
    }

    private T getEnumTypeByResult(String result) throws SQLException {
        T enumType = null;
        if (!Strings.isNullOrEmpty(result)) {
            T[] constants = targetClass.getEnumConstants();
            for (T constant : constants) {
                if (constant.getType().equals(result)) {
                    enumType = constant;
                    break;
                }
            }
        }
        return enumType;
    }

}
