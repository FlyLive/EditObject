package Servlets;

import Back.Service.ObjectService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 15852 on 2017/4/30.
 */
public class InitMenuServlet extends HttpServlet {
    private ObjectService _objectService = new ObjectService();

    public InitMenuServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> names = _objectService.getAllObjectName();
        request.getRequestDispatcher(request.getContextPath() + "/Views/SearchResult.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    }

    public void init() throws ServletException {
        // Put your code here
    }
}