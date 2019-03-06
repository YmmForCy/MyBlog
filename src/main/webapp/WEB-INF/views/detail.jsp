<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<%--<script src="../static/editormd/examples/js/jquery.min.js"></script>
<script src="../static/editormd/lib/marked.min.js"></script>
<script src="../static/editormd/lib/prettify.min.js"></script>
<script src="../static/editormd/lib/raphael.min.js"></script>
<script src="../static/editormd/lib/underscore.min.js"></script>
<script src="../static/editormd/lib/sequence-diagram.min.js"></script>
<script src="../static/editormd/lib/flowchart.min.js"></script>
<script src="../static/editormd/lib/jquery.flowchart.min.js"></script>
<script src="../static/editormd/editormd.js"></script>--%>
<head>
    <title>详情</title>
</head>
<body>
<jsp:include page="comm/top.jsp"/>
<div class="container">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">${article.title}</h3>
        </div>

        <%--<div class="span3">
            <div id="toc">
            </div><!--/.well -->
        </div><!--/span-->--%>

        <div class="panel-body">
            <span id="articleContent">${article.content}</span>
        </div>
    </div>
</div>
</body>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/showdown/1.3.0/showdown.min.js"></script>
<%--<script type="text/javascript">
    var content = "abc";
    var converter = new showdown.Converter(); //初始化转换器
    var htmlcontent = converter.makeHtml(content); //将MarkDown转为html格式的内容
    $("#articleContent").html(htmlcontent);//添加到 div 中 显示出来
</script>--%>
</html>

