package com.mo.lawyercloud.jpush;

/**
 * Created by lc on 2016/11/25.
 * 图片圆角规则 eg. TOP：上半部分
 */

public enum HalfType {
    LEFT, // 左上角 + 左下角
    RIGHT, // 右上角 + 右下角
    TOP, // 左上角 + 右上角
    BOTTOM, // 左下角 + 右下角
    ALL // 四角
}
