package com.lhx.aggregate.config;

/**
 * 全局权限常量
 */
public final class PCons {
	/** 根权限ID */
	public static final int ROOT = 0;
	/** 后台管理 */
	public static final int NODE_ONE = 10;
	/** 数据分析 */
	public static final int NODE_TWO = 11;

	/**
	 * 菜单及按钮权限说明：
	 * 菜单：如用户组管理,MENU_GROUP=101000,六位数字含义：前两位10表示节点,中间两位表示菜单
	 * 按钮：如查看用户组,GR1=101001,六位数字含义：前两位10表示节点,中间两位表示菜单,后两位表示该菜单下面按钮的操作
	 */
	/******************** 后台管理开始 ***************************/
	/** 用户组管理(菜单入口) */
	public static final int MENU_GROUP = 101000;
	/** 查看用户组 */
	public static final int GR1 = 101001;
	/** 添加或修改用户组 */
	public static final int GR2 = 101002;
	/** 删除用户组 */
	public static final int GR3 = 101003;
	/** 批量删除用户组 */
	public static final int GR4 = 101004;
	/** 查看组用户 */
	public static final int GR5 = 101005;
	/** 编辑组权限 */
	public static final int GR6 = 101006;
	/** 更新组权限 */
	public static final int GR7 = 101007;

	/** 用户管理(菜单入口) */
	public static final int MENU_USER = 101100;
	/** 查看用户 */
	public static final int USER1 = 101101;
	/** 添加或修改用户 */
	public static final int USER2 = 101102;
	/** 删除用户 */
	public static final int USER3 = 101103;
	/** 批量删除用户 */
	public static final int USER4 = 101104;
	/** 编辑用户权限 */
	public static final int USER5 = 101105;
	/** 更新用户权限 */
	public static final int USER6 = 101106;

	/** 操作日志列表(菜单入口) */
	public static final int MENU_OPER_LOG = 101200;
	/** 添加操作日志 */
	public static final int OL1 = 101201;
	/** 删除操作日志 */
	public static final int OL2 = 101202;
	/******************** 后台管理结束 ***************************/

	/******************** 数据分析开始 ***************************/
	/** opt列表(菜单入口) */
	public static final int MENU_OPT = 111000;
	/** 增加opt */
	public static final int OPT1 = 111001;
	/** 删除opt */
	public static final int OPT2 = 111002;
	/******************** 数据分析结束 ***************************/
}
