package com.hna.hka.archive.management.wenYuRiverInterface.model;

import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.model
 * @ClassName: BaseQueryVo
 * @Author: 郭凯
 * @Description: 查询基础父类
 * @Date: 2021/5/19 9:16
 * @Version: 1.0
 */
public class BaseQueryVo {

    private Map<String, String> search;

    private Integer  pageNum;

    private Integer pageSize;

    private Map<String, Object> newSearch;


    /**
     * @return the pageNum
     */
    public Integer getPageNum() {
        return pageNum == null?1:pageNum<=0?1:pageNum;
    }

    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize == null?10:pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the search
     */
    public Map<String, String> getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(Map<String, String> search) {
        this.search = search;
    }

    /**
     * @return the newSearch
     */
    public Map<String, Object> getNewSearch() {
        return newSearch;
    }

    /**
     * @param newSearch the newSearch to set
     */
    public void setNewSearch(Map<String, Object> newSearch) {
        this.newSearch = newSearch;
    }

}
