<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/views/styles/bootstrap.css" rel="stylesheet">
    <!--<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.21/themes/black-tie/jquery-ui.css" rel="stylesheet">-->
    <link href="/src/stylesheets/jquery.tocify.css" rel="stylesheet">
    <link href="/views/styles/prettify.css" type="text/css" rel="stylesheet" />

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <!--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>-->
    <%--<![endif]-->--%>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
    <style>
        body {
            padding-top: 20px;
        }
        p {
            font-size: 16px;
        }
        .headerDoc {
            color: #005580;
        }

        @media (max-width: 767px) {
            #toc {
                position: relative;
                width: 100%;
                margin: 0px 0px 20px 0px;
            }
        }
    </style>
</head>

<body>
<%--<jsp:include page="comm/top.jsp"/>--%>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <div id="toc">
            </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
            <div class="panel-heading">
                <h3 class="panel-title">${article.title}</h3>
            </div>

            <div class="panel-body">
                <span id="articleContent">${article.content}</span>
            </div>

        </div><!--/span-->
    </div><!--/row-->

</div><!--/.fluid-container-->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/libs/jquery/jquery-1.8.3.min.js"></script>
<script src="/libs/jqueryui/jquery-ui-1.9.1.custom.min.js"></script>
<script src="/views/javascripts/bootstrap.js"></script>
<script src="/src/javascripts/jquery.tocify.js"></script>
<script src="/views/javascripts/prettify.js"></script>
<script>
    $(function() {
        var toc = $("#toc").tocify({
            selectors: "h2,h3,h4,h5"
        });

        //prettyPrint();
        //$(".optionName").popover({ trigger: "hover" });
    });
</script>

</body>
</html>


<%--<body>
<div class="container">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">${article.title}</h3>
        </div>

        &lt;%&ndash;<div class="span3">
            <div id="toc">
            </div><!--/.well -->
        </div><!--/span-->&ndash;%&gt;

        <div class="panel-body">
            <span id="articleContent">${article.content}</span>
        </div>
    </div>
</div>
</body>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/showdown/1.3.0/showdown.min.js"></script>
&lt;%&ndash;<script type="text/javascript">
    var content = "abc";
    var converter = new showdown.Converter(); //初始化转换器
    var htmlcontent = converter.makeHtml(content); //将MarkDown转为html格式的内容
    $("#articleContent").html(htmlcontent);//添加到 div 中 显示出来
</script>&ndash;%&gt;
</html>--%>

