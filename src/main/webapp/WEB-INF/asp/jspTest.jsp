<%@ page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="utf-8" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="javascript:void(0);" id="createHtml" onclick="createHtml()">生成html模板...</a><br/>
<a href="${pageContext.request.contextPath}/freemarkerController/jumpFtl" id="jumpFtl">跳转ftl</a>
<br/>
bbb
<%= "asddsad"%>
</body>
<script type="text/javascript" src="/jquery.js"></script>
<script type="text/javascript">
    $(function () {
    });
</script>
</html>
