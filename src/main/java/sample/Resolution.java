package sample;/**
 * Created by bagrram on 23/05/2018.
 */
public enum Resolution {
    SCREENSHOT(1280,720),
    FEATURED(1024,500);


    private int height;
    private int width;


    Resolution(int width, int height) {
        this.height = height;
        this.width = width;

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
