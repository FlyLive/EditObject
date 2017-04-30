package Servlets;

import Back.Service.ObjectService;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

/**
 * Created by 15852 on 2017/4/27.
 */
public class DeletObjectServlet extends HttpServlet{
    private ObjectService _objectService = new ObjectService();

    public boolean deletObject(String className,int id){
        boolean result = _objectService.deletObject(className,id);
        return result;
    }
}
