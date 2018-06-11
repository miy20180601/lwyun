package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * @author Android
 * @date 2018/6/11
 * @details 我的发票bean
 */

public class InvoiceListBean {

    /**
     * totalCount : 1
     * result : [{"id":10,"userDTO":null,"orderNo":"319343284385","createTime":null,"status":3,"finishTime":1526389200000,"problem":null,"attachments":[],"timeMsg":{"id":6,"startTime":1526385600000,"endTime":1526389200000,"status":2},"channel":{"id":1,"name":"婚姻家事","icon":null},"realPrice":100}]
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 10
         * userDTO : null
         * orderNo : 319343284385
         * createTime : null
         * status : 3
         * finishTime : 1526389200000
         * problem : null
         * attachments : []
         * timeMsg : {"id":6,"startTime":1526385600000,"endTime":1526389200000,"status":2}
         * channel : {"id":1,"name":"婚姻家事","icon":null}
         * realPrice : 100
         */

        private int id;
        private Object userDTO;
        private String orderNo;
        private Object createTime;
        private int status;
        private long finishTime;
        private Object problem;
        private TimeMsgBean timeMsg;
        private ChannelBean channel;
        private int realPrice;
        private List<?> attachments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getUserDTO() {
            return userDTO;
        }

        public void setUserDTO(Object userDTO) {
            this.userDTO = userDTO;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(long finishTime) {
            this.finishTime = finishTime;
        }

        public Object getProblem() {
            return problem;
        }

        public void setProblem(Object problem) {
            this.problem = problem;
        }

        public TimeMsgBean getTimeMsg() {
            return timeMsg;
        }

        public void setTimeMsg(TimeMsgBean timeMsg) {
            this.timeMsg = timeMsg;
        }

        public ChannelBean getChannel() {
            return channel;
        }

        public void setChannel(ChannelBean channel) {
            this.channel = channel;
        }

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
            this.realPrice = realPrice;
        }

        public List<?> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<?> attachments) {
            this.attachments = attachments;
        }

        public static class TimeMsgBean {
            /**
             * id : 6
             * startTime : 1526385600000
             * endTime : 1526389200000
             * status : 2
             */

            private int id;
            private long startTime;
            private long endTime;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class ChannelBean {
            /**
             * id : 1
             * name : 婚姻家事
             * icon : null
             */

            private int id;
            private String name;
            private Object icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getIcon() {
                return icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }
        }
    }
}
