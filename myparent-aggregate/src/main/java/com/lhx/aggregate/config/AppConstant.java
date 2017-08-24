package com.lhx.aggregate.config;

/**
 * 系统常用常量
 */
public class AppConstant {
    public static final String PASSWORD_KEY = "lhx";
    public static final String ACCOUNT_ADMIN = "admin";
    public static final String SESSION_LOGIN_USER = "session_login_user";
    public static final String SESSION_LOGIN_GROUP = "session_login_group";
    public static final String SESSION_USER_AUTH = "session_user_auth";

    public static final short GROUP_COMMON= 2;// 普通组
    public static final short GROUP_ADMIN= 1;// 管理员组
//    public static final int GROUP_TECHNOLOGY= 2;// 技术组
//    public static final int GROUP_PRODUCT_OPERATE = 3;// 产品运营组
//    public static final int GROUP_MARKET_OPERATE = 4;// 市场运营组
    public static final short OPT_ADD=1;//新增操作
    public static final short OPT_DEL=2;//删除操作
    public static final short OPT_EDIT=3;//修改操作
    public static final int CODE_SUC=200;//操作成功
    public static final int CODE_FAIL=400;//操作失败
}
