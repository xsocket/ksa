<!DOCTYPE html>
<html lang="zh">
<head>
<title>${stack.findValue("exception.name")!}</title>
</head>
<body>
	<div class="error-message" style="margin: 10px">
		<@s.actionerror/><br/>
		${stack.findValue("exception.message")!}<br/>
		${stack.findValue("exceptionStack")!}
	</div>
</body>
</html>