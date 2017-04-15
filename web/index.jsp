<%--
  Created by IntelliJ IDEA.
  User: 15852
  Date: 2017/4/14
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>任意对象的增删改查(Web)</title>
    <link href="Css/Index.css" rel="stylesheet"/>
    <link href="Css/bootstrap.min.css" rel="stylesheet"/>
    <script src="JS/jquery-2.2.3.min.js"></script>
</head>
<body>
<div class="wrapper">
    <div class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            Dropdown
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
        </ul>
    </div>

    <div class="row">
        <button class="btn btn-primary" type="button" onclick="CreateObject()">新建对象</button>
        <div class="col-lg-6">
            <div class="input-group">
                <input id="search" type="text" class="form-control" placeholder="搜索...">
                <span class="input-group-btn"><button class="btn btn-default" type="button" onclick="Search()">搜索</button></span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
    </div><!-- /.row -->
    Hello World,Hello TomCat!
    <button class="btn btn-primary" type="button" onclick="EditObject()">修改对象</button>
    <ul class="list-group">
        <li class="list-group-item">Cras justo odio</li>
        <li class="list-group-item">Dapibus ac facilisis in</li>
        <li class="list-group-item">Morbi leo risus</li>
        <li class="list-group-item">Porta ac consectetur ac</li>
        <li class="list-group-item">Vestibulum at eros</li>
    </ul>

    <div class="right-click" id="click-menu">
        <p>编辑角色</p>
        <ul>
            <li id="addItem" onclick="DeletObject()">
                删除对象
            </li>
            <li onclick="EditObject()">修改对象信息</li>
        </ul>
    </div>

    <%@ include file="Views/ViewConponents/_CreateObject.jsp"%>
    <%@ include file="Views/ViewConponents/_EditObject.jsp"%>

    <script src="JS/Index.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</div>
</body>
</html>
