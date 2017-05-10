<%--
  Created by IntelliJ IDEA.
  User: 15852
  Date: 2017/4/15
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Back.Service.ObjectDisplay" %>
<%
    request.setCharacterEncoding("UTF-8");
%>

<%
    ArrayList<Object> objects = (ArrayList<Object>) request.getAttribute("objects");
    for (Object object : objects) {
        Class<?> clazz = object.getClass();
        Object[] fields = ObjectDisplay.getFields(object,clazz);
%>
<tr class="object-item" id="<%fields[0].toString();%>">
    <% for (Object field : fields) {
    %>
        <td><%=field == null ? "未设置" : field.toString()%></td>
    <%}%>
</tr>
<%}%>