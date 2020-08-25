package DAO;

import java.io.IOException;

import Bean.User;

/**
 * 网络访问接口
 */
public interface OkHttpDAO {
    /**
     * 初始化，主要用于收集cookie和viewState(?)
     */
    public void init();

    /**
     * 根据指定url发送给请求
     *
     * @param url 请求url
     * @param ref 引用
     * @return 相应页面的HTML文档
     */
    public void sendPostRequest(String url, String ref);

    /**
     * 登录
     *
     * @param user 用户信息
     * @return
     */
    public String login(User user) throws IOException;

    /**
     * 设置教务网地址
     */
    public void setJwURL(String url);

}
