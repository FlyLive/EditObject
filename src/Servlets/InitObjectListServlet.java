package Servlets;

import Back.Service.ObjectService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 15852 on 2017/4/30.
 */
public class InitObjectListServlet extends HttpServlet {
    private ObjectService _objectService = new ObjectService();

    public InitObjectListServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> names = _objectService.getAllObjectName();

        JSONObject json = new JSONObject();
        try {
            for(String name :names){
                json.append(";",name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getOutputStream().write(json.toString().getBytes("UTF-8"));
        response.setContentType("text/json; charset=UTF-8");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.getRequestDispatcher("Views/SearchResult.jsp").forward(request,response);
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
