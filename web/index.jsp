<%--
  Created by IntelliJ IDEA.
  User: 15852
  Date: 2017/4/14
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
%>
<html>
<head>
    <title>任意对象的增删改查(Web)</title>
    <link href="Css/Index.css" rel="stylesheet"/>
    <link href="Css/bootstrap.min.css" rel="stylesheet"/>
    <script src="JS/jquery-2.2.3.min.js"></script>
</head>
<body>
<div class="wrapper">
    <br>
    <div class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="object-menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            选择对象
            <span class="caret"></span>
        </button>&emsp;(<span>右键编辑对象</span>)
        <ul id="objects" class="dropdown-menu" aria-labelledby="object-menu">
        </ul>
    </div>
    <br>
    <div class="row">
        <button class="btn btn-primary" type="button" onclick="CreateObject()">新建对象</button>
        <h4 class="selected-object">(<span id="focus-object"></span>)</h4>
        <div class="col-lg-6">
            <div class="input-group">
                <input id="search" type="text" class="form-control" placeholder="搜索...">
                <span class="input-group-btn"><button class="btn btn-default" type="button" onclick="Search()">搜索</button></span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
    </div><!-- /.row -->
    <br>

    <table class="table table-hover">
        <caption></caption>
        <thead>
        <tr id="object-head"></tr>
        </thead>
        <tbody id="object-body"></tbody>
    </table>

    <div class="right-click" id="click-menu">
        <input id="selected-item" type="hidden">
        <p>编辑对象</p>
        <ul>
            <li onclick="DeleteObject()">删除对象</li>
            <li onclick="EditObject()">修改对象信息</li>
        </ul>
    </div>

    <div class="modal fade" id="objectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</div>
<script src="JS/bootstrap-3.3.7.min.js"></script>
<script src="Content/layer/layer.js"></script>
<script src="JS/layer_open_tip.js"></script>
<script src="JS/Index.js"></script>
</body>
</html>
