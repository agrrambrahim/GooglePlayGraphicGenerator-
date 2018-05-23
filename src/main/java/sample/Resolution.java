package sample;/**
 * Created by bagrram on 23/05/2018.
 */
public enum Resolution {
    SCREENSHOT(1280,720),
    FEATURED(1024,500);

    public int height;
    public int width;

    Resolution(int height, int width) {
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
