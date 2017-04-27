package com.billy;

import com.billy.scriptParser.ScriptParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @date 2016-05-11 11:50
 * @since 2016-05-11 11:50
 */
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String n = req.getParameter("n");

        try {
            ScriptParser.main(new String[]{n});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
