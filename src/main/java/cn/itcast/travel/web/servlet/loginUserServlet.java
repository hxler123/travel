package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginUserServlet")
public class loginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        UserServiceImpl userService = new UserServiceImpl();
        User u = userService.login(user);

        ResultInfo info = new ResultInfo();

        //判断用户对象是否为null
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        if (u != null && !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户未激活");
        }

        if (u != null && "Y".equals(u.getStatus())){
            info.setFlag(true);
        }

        ObjectMapper mapper = new ObjectMapper();


    }
}
