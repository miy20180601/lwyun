package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/10/010
 * @annotation 时间管理列表
 */
public class TimeMsgBean {

    /**
     * totalCount : 1
     * result : [{"id":2,"startTime":1526112000000,"endTime":1526115600000,"status":1}]
     * pageNo : 1
     * pageSize : 10
     * first : 1
     * isHasNext : false
     * totalPages : 1
     * nextPage : 1
     * isHasPre : false
     * prePage : 1
     */

    private int totalCount;
    private int pageNo;
    private int pageSize;
    private int first;
    private boolean isHasNext;
    private int totalPages;
    private int nextPage;
    private boolean isHasPre;
    private int prePage;
    private List<ResultBean> result;

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

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 2
         * startTime : 1526112000000
         * endTime : 1526115600000
         * status : 1
         */

        private int id;
        private String startTime;
        private String endTime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
