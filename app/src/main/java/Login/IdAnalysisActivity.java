package Login;

import java.util.HashMap;

public class IdAnalysisActivity {
    private String newCode = "";
    private HashMap<String, Integer> m = new HashMap<>();

    private void init(HashMap m) {
        int tempCode = 1;
        m.put("011", tempCode++);    /** 计算机 */
        m.put("021", tempCode++);    /** 电信 */
        m.put("031", tempCode++);    /** 机械 */
        m.put("032", tempCode++);    /** 自动化 */
        m.put("033", tempCode++);    /** 电气 */
        m.put("041", tempCode++);    /** 土木 */
        m.put("042", tempCode++);    /** 环境 */
        m.put("051", tempCode++);    /** 材料 */
        m.put("061", tempCode++);    /** 建筑 */
        m.put("071", tempCode++);    /** 经济 */
        m.put("072", tempCode++);    /** 工商管理 */
        m.put("081", tempCode++);    /** 数学 */
    }

    private boolean Grade(String ID) {
        if (ID.length() != 2) return false;
        int code = Integer.valueOf(ID);
        newCode += 'a'+code-16;
        return true;
    }

    private boolean Major(String ID) {
        if (!m.containsKey(ID)) return false;
        newCode += 'a'+m.get(ID);
        return true;
    }

    private boolean Class(String ID) {
        if (ID.length()!=2) return false;
        int code = Integer.valueOf(ID);
        newCode += 'a'+code;
        return true;
    }

    private boolean Number(String ID) {
        if (ID.length()!=2) return false;
        int code = Integer.valueOf(ID);
        newCode += 'a'+code;
        return true;
    }
    public String Analysis(String ID){
        init(m);
        String sub1 = ID.substring(0, 2);
        String sub2 = ID.substring(2, 5);
        String sub3 = ID.substring(5, 7);
        String sub4 = ID.substring(7, 9);
        if (!(Grade(sub1) && Major(sub2) && Class(sub3) && Number(sub4))) return "";
        return newCode;
    }
}
