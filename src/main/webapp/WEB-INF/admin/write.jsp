<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>写博客</title>
    <script src="<%=contextPath%>/static/jquery-3.1.1.js"></script>
    <script src="<%=contextPath%>/static/editormd/editormd.js"></script>
    <script src="<%=contextPath%>/static/bootstrap/js/bootstrap.js"></script>
    <link href="<%=contextPath%>/static/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/static/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=contextPath%>/static/editormd/css/editormd.css"/>
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <form method="post" action="/chenyu/write">
        <br>
        <br>
        <div class="row">
            <div class="col-lg-6">
                <div class="input-group">
                    <div class="input-group-btn">
                        <c:choose>
                            <c:when test="${article==null}">
                                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false" id="categoryBtn">未分类
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false" id="categoryBtn">${article.category}
                                </button>
                            </c:otherwise>
                        </c:choose>

                        <%--选择分组--%>
                        <ul class="dropdown-menu">
                            <c:forEach var="category" items="${categories}">
                                <li>
                                    <a onclick="selectCategory('${category.name}','${category.displayName}');">${category.displayName}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <input name="category" id="categoryInput" type="hidden">

                    <%--标题回显--%>
                    <c:choose>
                        <c:when test="${article==null}">
                            <input type="text" class="form-control" placeholder="标题" name="title">
                        </c:when>
                        <c:otherwise>
                            <input name="id" type="hidden" value="${article.id}">
                            <input type="text" class="form-control" placeholder="标题" name="title"
                                   value="${article.title}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <%--发表、新增--%>
            <c:choose>
                <c:when test="${article==null}">
                    <div class="col-lg-6" style="text-align: right;">
                        <p>
                            <input type="submit" class="btn btn-primary dropdown-toggle" role="button" value="发表">
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-lg-6" style="text-align: right;">
                        <p>
                            <input type="submit" class="btn btn-primary dropdown-toggle" role="button" value="修改">
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>

            <%--</div>--%>
        </div>
        <br>
        <div id="layout" style="width: 100%;height: 100%">
            <div id="test-editormd">
                <%--文章内容回显--%>
                <c:choose>
                    <c:when test="${article==null}">
                        <textarea style="display:none;" name="content"></textarea>
                    </c:when>
                    <c:otherwise>
                        <textarea style="display:none;" name="content">${article.content}</textarea>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <script type="text/javascript">
            var testEditor;
            $(function () {
                testEditor = editormd("test-editormd", {
                    width: "100%",
                    height: 640,
                    syncScrolling: "single",
                    path: "/static/editormd/lib/",
                    imageUpload: true,
                    imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                    imageUploadURL: "./php/upload.php?test=dfdf"

                    /*
                     上传的后台只需要返回一个 JSON 数据，结构如下：
                     {
                     success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
                     message : "提示的信息，上传成功或上传失败及错误信息等。",
                     url     : "图片地址"        // 上传成功时才返回
                     }
                     */
                });

                /*
                 // or
                 testEditor = editormd({
                 id      : "test-editormd",
                 width   : "90%",
                 height  : 640,
                 path    : "../lib/"
                 });
                 */
            });
        </script>
    </form>
</div>
<script>
    function selectCategory(name, displayName) {
        $("#categoryBtn").html(displayName);
        $("#categoryInput").val(name);
    }
</script>
</body>
</html>
