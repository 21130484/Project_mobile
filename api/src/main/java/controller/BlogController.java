package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import utils.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blog")
public class BlogController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Blog blog = HttpUtil.of(req.getReader()).toModel(Blog.class);

        objectMapper.writeValue(resp.getOutputStream(), blog);
//        resp.getWriter().write(objectMapper.toString());
    }
}
