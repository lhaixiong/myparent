package com.lhx.aggregate.controller;

import com.lhx.aggregate.entity.OperateLog;
import com.lhx.aggregate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.config.PCons;
import com.lhx.aggregate.service.OperateLogService;
import com.lhx.aggregate.tools.PageBean;

@Controller
@RequestMapping("/operatelog")
@Permission(id = PCons.NODE_ONE, parent = PCons.ROOT, type = PermissionInfo.TYPE_NODE, name = "后台管理", icon = "", sort = PCons.NODE_ONE)
public class OperateLogController {
	@Autowired
	private OperateLogService service;
    @Autowired
    private UserService userService;

	@RequestMapping("/list")
	@Permission(id = PCons.MENU_OPER_LOG, parent = PCons.NODE_ONE, type = PermissionInfo.TYPE_MENU, name = "操作日志列表", icon = "", sort = PCons.MENU_OPER_LOG)
	public ModelAndView list(
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
			String datemin, String datemax,
			@RequestParam(value = "creater", required = false, defaultValue = "0") int creater,
			String keyword) {
		ModelAndView mv = new ModelAndView("/operatelog/operatelog_list");
        PageBean<OperateLog> pageBean = service.findPageList(curPage, pageSize, datemin, datemax, creater, keyword);
        mv.addObject("pageBean", pageBean);
        mv.addObject("datemin", datemin);
        mv.addObject("datemax", datemax);
        mv.addObject("creater", Integer.valueOf(creater));
        mv.addObject("keyword", keyword);
        mv.addObject("allUser", userService.listUser(""));
		return mv;
	}

	@RequestMapping("/add")
	@Permission(id = PCons.OL1, parent = PCons.MENU_OPER_LOG, type = PermissionInfo.TYPE_BUTTON, name = "添加操作日志", icon = "", sort = PCons.OL1)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("/operatelog/operatelog_list");
		mv.addObject("msg", "添加操作日志列表");
		return mv;
	}

	@RequestMapping("/del")
	@Permission(id = PCons.OL2, parent = PCons.MENU_OPER_LOG, type = PermissionInfo.TYPE_BUTTON, name = "删除操作日志", icon = "", sort = PCons.OL2)
	public ModelAndView del() {
		ModelAndView mv = new ModelAndView("/operatelog/operatelog_list");
		mv.addObject("msg", "删除操作日志列表");
		return mv;
	}
}
