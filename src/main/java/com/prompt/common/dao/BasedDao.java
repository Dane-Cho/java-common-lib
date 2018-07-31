package com.prompt.common.dao;

import com.prompt.common.model.PagingParamModel;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 
 * 기본 DAO 추상 클래스
 *
 */
public abstract class BasedDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private BatchSqlSessionTemplate batchSqlSessionTemplate;
    
    protected final SqlSession getSqlSession() {
        return sqlSessionTemplate;
    }

    protected final SqlSession getBatchSqlSession() {
        return batchSqlSessionTemplate;
    }
    
    protected final String getQueryStatement(String queryId) {
        return this.getClass().getName()+"."+queryId;
    }

    protected final void mappingPagingParam(Map<String,Object> paramMap, PagingParamModel pagingParam) {
        if(paramMap != null && pagingParam != null){
            int pageNum = pagingParam.getPageNum();
            int pageSize = pagingParam.getPageSize();
            if(pageNum > 0 && pageSize > 0){
                pageNum = (pageNum-1) * pageSize;
                paramMap.put("startNum", pageNum);
                paramMap.put("pageSize", pageSize);
            }
        }
    }

}