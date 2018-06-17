package com.mo.lawyercloud.eventbus;

/**
 * Created by Mohaifeng on 18/6/17.
 */
public class AdvisoryMessage {
    public Integer channel;
    /**1 channel跳转  2搜索跳转*/
    public int type;
    public String name;
}
