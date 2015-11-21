package com.ksa.web.struts2.action;

import com.opensymphony.xwork2.Action;

public interface JsonAction extends Action {

    /**
     * 返回需要序列化为 json 格式的数据对象。
     * @return 结果数据
     */
    Object getJsonResult();
}
