package Bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 信息发布对象的基础类
 */
public class MessageItem extends BmobObject {
    /**
     * 私有成员。
     * title:通知标题
     * content:通知内容
     * author:通知发布者
     * image:通知发布中可能附带的图片
     * Labels:通知携带的标签信息
     */
    private String title;
    private String content;
    private User author;
    private ArrayList<String> images;
    private String[] Labels;

    /**
     * 获取通知标题
     * @return 标题
     */
    public String getTitle(){
        return title;
    }

    /**
     * 设置通知标题
     * @param title 用户输入的通知标题
     * @return  该对象，也就是this
     */
    public MessageItem setTitle(String title){
        this.title = title;
        return this;
    }

    /**
     * 获取通知内容
     * @return  一个string类型的内容实例
     */
    public String getContent(){
        return content;
    }

    /**
     * 设置通知内容
     * @param content 用户输入的通知内容
     * @return  该对象，也就是this
     */
    public MessageItem setContent(String content){
        this.content = content;
        return this;
    }

    /**
     * 获取通知发布者（User类型）
     * @return 发布者对象
     */
    public User getAuthor(){
        return author;
    }

    /**
     * 设置通知发布者（User类型）
     * @param author 先前初始化好的User对象
     * @return 该对象，也就是this
     */
    public MessageItem setAuthor(User author){
        this.author = author;
        return this;
    }

    /**
     * 获取通知附带的图片信息
     * @return  一个BmobFile实例，在此处为图片
     */
    public ArrayList<String> getImages(){
        return images;
    }

    /**
     * 设置通知附带的图片信息
     * @param images 照片在本地的本地Url
     * @return  该对象，也就是this
     */
    public MessageItem setImages(ArrayList<String> images){
        this.images = images;
        return this;
    }

    /**
     * 获取通知携带的标签信息
     * @return  标签
     */
    public String[] getLabels() {return this.Labels;}

    /**
     * 设置通知自带的标签信息
     * @param labels 设置好的标签
     * @return 该对象，也就是this
     */
    public MessageItem setLabels(String[] labels) {
        this.Labels = labels;
        return this;
    }
}
