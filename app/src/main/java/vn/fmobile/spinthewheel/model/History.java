package vn.fmobile.spinthewheel.model;

public class History {
    public int id;
    public String name;
    public int bgColor;
    public int textColor;

    public String dateTime;

    public History() {
    }

    public History(int id, String name, int bgColor, int textColor, String dateTime) {
        this.id = id;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.name = name;
        this.dateTime = dateTime;
    }
}
