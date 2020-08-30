package AdaptObject;

import java.util.List;

public class Labelrange {
    private String rangename;
    private List<LabelModel> labelLayoutlist;

    public Labelrange(String rangename, List<LabelModel> labelLayoutlist)
    {
        this.rangename=rangename;
        this.labelLayoutlist=labelLayoutlist;
    }

    public String getRangename(){
        return rangename;
    }

    public List<LabelModel> getLabelLayoutlist(){
        return  labelLayoutlist;
    }
}