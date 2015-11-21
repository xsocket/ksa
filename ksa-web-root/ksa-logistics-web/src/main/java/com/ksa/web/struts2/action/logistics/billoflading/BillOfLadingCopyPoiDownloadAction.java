package com.ksa.web.struts2.action.logistics.billoflading;


public class BillOfLadingCopyPoiDownloadAction extends BillOfLadingPoiDownloadAction {

    private static final long serialVersionUID = 2556966112259639994L;

    @Override
    protected String getWorkbookTemplateLocation() {
        return "classpath:ui/logistics/tmpl/billoflading-copy.xls";
    }
}
