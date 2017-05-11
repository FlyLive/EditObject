<%--
  Created by IntelliJ IDEA.
  User: 15852
  Date: 2017/4/15
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Back.Service.ObjectDisplay" %>
<%@ page import="Back.Service.ObjectService" %>
<%@ page import="java.util.ArrayList" %>
<%
    request.setCharacterEncoding("UTF-8");
%>

<div class="modal-header">
    <button type="button" class="close" Data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="myModalLabel">创建对象</h4>
</div>

<div class="modal-body">
    <%
        String objName = (String)request.getAttribute("objectName");
        Object[] annotations = ObjectService.getAnnotations(objName).toArray();
        Object[] fields = ObjectService.getTips(objName).toArray();

        for (int i = 0;i < fields.length;i++) {
    %>
    <div class="form-group">
    <div class="input-group">
        <span class="input-group-addon" id="basic-addon1"><%=annotations[i].toString()%></span>
        <input type="text" class="form-control input-item" id="<%=fields[i].toString()%>" placeholder="<%=fields[i].toString()%>" aria-describedby="basic-addon1">
    </div>
    </div>
    <%}%>

    <%--<div class="form-group">--%>
        <%--<label>支付密码</label>--%>
        <%--<input id="pay_password" class="form-control" placeholder="请输入支付密码" type="password"/>--%>
    <%--</div>--%>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" onclick="SubmitCreate()">新建对象</button>
    <button type="button" class="btn btn-default" Data-dismiss="modal">关闭</button>
</div>
