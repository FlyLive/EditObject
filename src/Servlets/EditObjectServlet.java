package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 15852 on 2017/4/26.
 */
public class EditObjectServlet extends HttpServlet {

    public EditObjectServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(request.getContextPath() + "/Views/SearchResult.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.getRequestDispatcher(request.getContextPath() + "/Views/SearchResult.jsp").forward(request,response);
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
