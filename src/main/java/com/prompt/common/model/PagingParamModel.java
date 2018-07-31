package com.prompt.common.model;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 페이징 파라미터 모델
 */
public class PagingParamModel implements Serializable {

    @Min(0)
    private int pageNum = 0;

    @Min(0)
    private int pageSize = 10;

    public PagingParamModel() {}

    public PagingParamModel(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PagingParamModel{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
