package com.ksa.web.struts2.action.data;

import com.opensymphony.xwork2.Action;


public interface ComboDataAction extends Action {
    /**
     * 获取分页数据结果。
     * @return 列表数据
     */
    Object[] getComboData();
}
