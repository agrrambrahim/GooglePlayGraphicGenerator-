package sample;

public enum Thumbnail {
    xxxhdpi ("mipmap-xxxhdpi",192,192),
    xxhdpi("mipmap-xxhdpi",144,144),
    xhdpi("mipmap-xhdpi",96,96),
    hdpi("mipmap-hdpi",72,72),
    mdpi("mipmap-mdpi",48,48),
    ldpi("mipmap-ldpi",36,36);

    public int height;
    public int width;
    public String folderName;

    Thumbnail(String folderName, int width, int height) {
        this.height = height;
        this.width = width;
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
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
