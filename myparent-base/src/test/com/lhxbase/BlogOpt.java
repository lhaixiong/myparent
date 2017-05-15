package lhxbase;

/**
 * 操作选项,注意:字段命名尽量限制在30字符以内! 命名规则:系统+[二级分类]+动作<br>
 * 如:购买成长基金,拆分为你 成长基金growthFund(可以和类名一致)+购买(buy),由于成长基金属于活动系统activity故最终命名为：
 * activity_growthFund_buy
 * 
 * @author yaowenhao
 * @date 2014年10月9日 下午1:54:07
 */
public enum BlogOpt {
	NA,
	/** 挑战副本 */
	dragon_challenge,
	/** 领取副本宝箱 */
	dragon_getbox,
	/** 领取副本宝箱 */
	dragon_aaa,
	/** 挑战补给线 */
	feedline_challenge,
	/** 扫荡补给线 */
	feedline_sweep,
	/** 重置补给线 */
	feedline_reset,
	/** 扫荡普通关卡 */
	dragon_sweep


}
