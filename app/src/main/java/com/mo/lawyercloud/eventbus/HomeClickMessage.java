package com.mo.lawyercloud.eventbus;

/**
 * Created by Mohaifeng on 18/6/13.
 */
public class HomeClickMessage {
    public Integer channel;
    /**1 为home点击跳转  2为登出跳转  3为消息跳转 4.搜索跳转*/
    public int type;
    public String name;
}
