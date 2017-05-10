package Servlets;

import Back.Service.ObjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 15852 on 2017/4/27.
 */
public class DeleteObjectServlet extends HttpServlet{
    private ObjectService _objectService = new ObjectService();

    public DeleteObjectServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        //boolean result = _objectService.deletObject(className,id);
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
