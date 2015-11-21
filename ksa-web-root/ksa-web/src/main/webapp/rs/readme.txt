theme 说明

1. 使用开源框架说明
	1)  bootstrap : 	v2.1.0
	2)  jquery : 		v1.7.2 注意：easyui-1.3以下与 jquery-1.8不兼容
		不兼容1：outHeight()的解读不同。
			1.7.2中 $().outerHeight() === null
			1.8.0中 $().outerHeight() === undefined
		2.1) jquery.hotkeys :  v0.8			
	3)  easyui :		v1.3
	4)  html5
	5)  kibo
	
2. 自定义扩展说明
	probe.web.core.theme.custom 包下的资源文件为针对上述开源框架的自定义扩展。
	主要是调整样式，将 bootstrap 框架和 easyui 框架融合起来。