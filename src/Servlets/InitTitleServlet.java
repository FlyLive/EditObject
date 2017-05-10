package Servlets;

import Back.Service.ObjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 15852 on 2017/5/9.
 */
public class InitTitleServlet extends HttpServlet{
    private ObjectService _objectService = new ObjectService();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String objectName = request.getParameter("objectName").trim();
        List<String> names = _objectService.getAnnotations(objectName);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(names);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    }
}
