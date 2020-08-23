package Bean;

import java.util.HashMap;

import Login.IdAnalysisActivity;
import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    /**
     * 用户基本信息类(Bmob后端云中的User表)
     */
    private String StuID;
    private String Grade;
    private String Major;
    private String ClassID;
    private String ID;

    public User setStuID() {
        this.StuID = new IdAnalysisActivity().IdAnalysis(this.getUsername());
        return this;
    }
    public String getStuID(){
        return StuID;
    }

    public User setGrade(){
        int grade = StuID.charAt(0) - 'a' + 2016;
        this.Grade = Integer.toString(grade);
        return this;
    }
    public String getGrade(){
        return Grade;
    }

    public User setMajor(HashMap<Integer, String> m){
        int code = StuID.charAt(1) - 'a';
        this.Major = m.get(code);
        return this;
    }
    public String getMajor(){
        return this.Major;
    }

    public User setClassID(){
        int code = StuID.charAt(2) - 'a';
        this.ClassID = Integer.toString(code) + "班";
        return this;
    }
    public String getClassID(){
        return this.ClassID;
    }

    public User setID(){
        int code = StuID.charAt(3) - 'a';
        this.ID = Integer.toString(code);
        return this;
    }
    public String getID(){
        return ID;
    }
    public User setAll(String usr, String pwd) {
        HashMap<Integer, String> m = new HashMap<>();
        int tempCode = 0;
        m.put(tempCode++, "计算机");    /** 计算机 */
        m.put(tempCode++, "电信");      /** 电信 */
        m.put(tempCode++, "机械");      /** 机械 */
        m.put(tempCode++, "自动化");    /** 自动化 */
        m.put(tempCode++, "电气");      /** 电气 */
        m.put(tempCode++, "土木");      /** 土木 */
        m.put(tempCode++, "环境");      /** 环境 */
        m.put(tempCode++, "材料");      /** 材料 */
        m.put(tempCode++, "建筑");      /** 建筑 */
        m.put(tempCode++, "经济");      /** 经济 */
        m.put(tempCode++, "工商管理");   /** 工商管理 */
        m.put(tempCode++, "数学");      /** 数学 */

        setUsername(usr);
        setPassword(pwd);
        if (usr.length() != 9) return this;

        this.setStuID()
                .setGrade()
                .setMajor(m)
                .setClassID()
                .setID();
        return this;
    }
}