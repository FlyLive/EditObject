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
public class CreateObjectServlet extends HttpServlet {
    private ObjectService _objectService = new ObjectService();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String objName = request.getParameter("objectName");
        if(objName == "" || objName == null){
            return;
        }
        request.setAttribute("objectName",objName);
        request.getRequestDispatcher(request.getContextPath() + "/Views/ViewComponents/_CreateObject.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        String objectName = request.getParameter("objectName");
        String[] inputs = request.getParameterValues("inputs[]");
        String[] options = request.getParameterValues("options[]");

        boolean result = _objectService.create(objectName,inputs,options);

        response.getWriter().print(result);
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
