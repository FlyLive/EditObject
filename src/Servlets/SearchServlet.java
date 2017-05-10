package Servlets;

import Back.Service.ObjectService;
import ViewModels.PersonEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 15852 on 2017/4/30.
 */
public class SearchServlet extends HttpServlet{
    private ObjectService _objectService = new ObjectService();
    public SearchServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String objectName = request.getParameter("objectName").trim();
        String search = request.getParameter("search").trim();

        ArrayList<Object> objects = new ArrayList<Object>();

        if(search != null && search != ""){
            objects.addAll(_objectService.search(objectName,search));
        } else if(objectName != null && objectName != "") {
            objects.addAll(_objectService.showAll(objectName));
        }

        request.setAttribute("objects",objects);
        request.getRequestDispatcher(request.getContextPath() + "/Views/SearchResult.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
