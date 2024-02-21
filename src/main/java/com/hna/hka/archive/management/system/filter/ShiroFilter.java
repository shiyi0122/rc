package com.hna.hka.archive.management.system.filter;

import com.hna.hka.archive.management.system.util.YmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;

/**
 * @program: rc
 * @description: 登录验证工具类
 * @author: zhaoxianglong
 * @create: 2021-10-19 13:20
 **/
public class ShiroFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        这里只有返回false才会执行onAccessDenied方法,因为
//         return super.isAccessAllowed(request, response, mappedValue);
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        String token = getRequestToken((HttpServletRequest) request);
        String login = ((HttpServletRequest) request).getServletPath();

        //如果为登录,就放行
        if ("/login".equals(login)){
            return true;
        }
//        if ("/".equals(login)){
//            return true;
//        }

        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal != null){
            return true;
        }

        if (token == null || !getSysUserBylonginTokenId(token)){
//            System.out.println("无效token");
            ((HttpServletResponse)response).sendRedirect("/login");
            return false;
        }
        return true;
    }
    private String getRequestToken(HttpServletRequest request){
        //默认从请求头中获得token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }

    private boolean getSysUserBylonginTokenId(String token) {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Connection conn = null;
        try {
            // 加载数据库驱动，注册到驱动管理器
            Class.forName(YmlUtils.getValue("spring.datasource.driver-class-name"));
            // 数据库连接字符串
            String url = YmlUtils.getValue("spring.datasource.url");
            // 数据库用户名
            String username = YmlUtils.getValue("spring.datasource.username");
            // 数据库密码
            String password = YmlUtils.getValue("spring.datasource.password");
            // 创建Connection连接
            conn = DriverManager.getConnection(url, username, password);
            // 获取Statement
            preparedStatement = conn.prepareStatement("select * from SYS_APP_USERS where LONGIN_TOKEN_ID = ?");
            // 添加图书信息的SQL语句
            preparedStatement.setString(1, token);
            // 执行查询
            rs = preparedStatement.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();        // 关闭ResultSet
                preparedStatement.close();    // 关闭Statement
                conn.close();    // 关闭Connection
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

}
