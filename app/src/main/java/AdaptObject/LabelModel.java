package AdaptObject;

import java.io.Serializable;

public class LabelModel implements Serializable{
    private String textValue;
    private boolean isClick;

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
