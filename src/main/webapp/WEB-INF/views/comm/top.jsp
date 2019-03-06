<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<script src="<%=contextPath%>/static/jquery-3.1.1.js"></script>
<script src="<%=contextPath%>/static/bootstrap/js/bootstrap.js"></script>
<link href="<%=contextPath%>/static/bootstrap/css/bootstrap.css" rel="stylesheet"/>
<link href="<%=contextPath%>/static/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
<div class="row">
    <nav class="navbar navbar-inverse">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%=contextPath%>/">chenyu的专栏</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="nv1">
                    <li class="active" id="firstPage"><a href="<%=contextPath%>/">首页</a></li>
                    <%--<li id="webPage"><a href="<%=contextPath%>/column/JavaWeb专栏/webPage">JavaWeb专栏</a></li>
                    <li id="androidPage"><a href="<%=contextPath%>/column/Android专栏/androidPage">Android专栏</a>
                    </li>
                    <li id="rnPage"><a href="<%=contextPath%>/column/React Native专栏/rnPage">React Native专栏</a>
                    </li>
                    <li id="ubuntuPage"><a href="<%=contextPath%>/column/Ubuntu专栏/ubuntuPage">Ubuntu专栏</a>
                    </li>--%>
                    <li id="webPage"><a href="<%=contextPath%>/column/Java/webPage">Java</a></li>
                    <li id="linuxPage"><a href="<%=contextPath%>/column/Linux/linuxPage">Linux</a></li>
                    <li id="frontPage"><a href="<%=contextPath%>/column/front/frontPage">前端</a></li>
                    <li id="noCategoryPage"><a href="<%=contextPath%>/column/noCategory/noCategoryPage">未分类</a></li>
                </ul>
                <form class="navbar-form navbar-right">
                    <div class="input-group">
                        <input id="searchText" type="text" class="form-control" placeholder="搜索">
                        <span class="input-group-btn">
                            <button id="btnSearch" class="btn btn-default" type="button" onclick="searchArticle()">Go!</button>
                        </span>
                    </div>
                    <c:if test="${user == null}">
                        <button onclick="onLogin()" type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
                            登录
                        </button>
                    </c:if>
                    <c:if test="${user != null}">
                        <button onclick="onLogout()" type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
                            退出
                        </button>
                    </c:if>
                </form>

            </div>
        </div>
    </nav>
</div>
<script>
    var href = location.href;
    var id = href.substring(href.lastIndexOf("/") + 1, href.length);
    if (id == "") {
        id = "firstPage";
    }
    // var ids = ["firstPage", "webPage", "androidPage", "rnPage", "ubuntuPage"];
    var ids = ["firstPage", "webPage", "linuxPage", "frontPage", "noCategoryPage"];
    for (var i = 0; i < ids.length; i++) {
        if (id == ids[i]) {
            $("#" + id).attr("class", "active");
            break;
        } else {
            $("#" + ids[i]).removeAttr("class");
        }
    }

    function searchArticle() {
        //alert($("#searchText").val());
        var searchText = $("#searchText").val();
        searchText = searchText?searchText:"";
        location.href = "/search?searchText=" + searchText;
    }

    function onLogin() {
        location.href = "/chenyu";
    }

    function onLogout() {
        location.href = "/chenyu/doLogout"
    }

    /*function getQueryVariable()
    {
        var query = window.location.search.substring(1);
        /!*var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }*!/
        var para = query.split("=");
        document.getElementById("searchText").value = para[1];
    }*/
    //回车提交事件
    $("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            searchArticle();
        }
    });
    //--------回车提交事件完毕---------------------//
</script>