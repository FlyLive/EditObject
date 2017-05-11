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
<%
    request.setCharacterEncoding("UTF-8");
%>

<div class="modal-header">
    <button type="button" class="close" Data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="myModalLabel">修改对象</h4>
</div>

<div class="modal-body">
    <%
        Object obj = request.getAttribute("object");
        String objName = (String)request.getAttribute("objectName");
        Class<?> clazz = obj.getClass();

        Object[] annotations = ObjectService.getAnnotations(objName).toArray();
        Object[] fields = ObjectService.getTips(objName).toArray();
        Object[] values = ObjectDisplay.getFields(obj, clazz);

        for (int i = 0;i < fields.length;i++) {
    %>
    <div class="form-group">
    <div class="input-group">
        <span class="input-group-addon" id="basic-addon1"><%=annotations[i].toString()%></span>
        <input type="text" class="form-control input-item" id="<%=fields[i].toString()%>" value="<%=values[i].toString()%>" placeholder="<%=fields[i].toString()%>" <%=fields[i].toString() == "idNo" ? "disabled" : ""%> aria-describedby="basic-addon1">
    </div>
    </div>
    <%}%>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" onclick="SubmitEdit()">确认修改</button>
    <button type="button" class="btn btn-default" Data-dismiss="modal">关闭</button>
</div>
