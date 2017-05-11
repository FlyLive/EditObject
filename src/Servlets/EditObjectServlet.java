package Servlets;

import Back.Service.ObjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by 15852 on 2017/4/26.
 */
public class EditObjectServlet extends HttpServlet {
    private ObjectService _objectService = new ObjectService();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String objName = request.getParameter("objectName");
        int objId = Integer.parseInt(request.getParameter("objectId"));

        Object obj = _objectService.searchById(objName,objId);
        if(obj == null){
            return;
        }
        request.setAttribute("objectName",objName);
        request.setAttribute("object",obj);
        request.getRequestDispatcher(request.getContextPath() + "/Views/ViewComponents/_EditObject.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        String objectName = request.getParameter("objectName");
        String[] inputs = request.getParameterValues("inputs[]");
        String[] options = request.getParameterValues("options[]");

        boolean result = _objectService.update(objectName,inputs,options);

        response.getWriter().print(result);
    }
}
