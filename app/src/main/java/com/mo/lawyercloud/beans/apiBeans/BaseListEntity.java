package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/6.
 * 数据分页
 */
public class BaseListEntity<T> {

    /**
     * totalCount : 1
     * result : [{"id":3,"createTime":1525847493000,"post":"实习律师","count":5,"workPlace":"北京",
     * "content":"完成总监交代的工作完成总监交代的工作！","status":1}]
     * pageNo : 1
     * pageSize : 10
     * first : 1
     * nextPage : 1
     * isHasNext : false
     * totalPages : 1
     * isHasPre : false
     * prePage : 1
     */

    private int totalCount;
    private int pageNo;
    private int pageSize;
    private int first;
    private int nextPage;
    private boolean isHasNext;
    private int totalPages;
    private boolean isHasPre;
    private int prePage;
    private List<T> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsHasNext() {
        return isHasNext;
    }

    public void setIsHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isIsHasPre() {
        return isHasPre;
    }

    public void setIsHasPre(boolean isHasPre) {
        this.isHasPre = isHasPre;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
