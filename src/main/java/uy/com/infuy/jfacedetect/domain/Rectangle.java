package uy.com.infuy.jfacedetect.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Rectangle {
    protected int top;
    protected int left;
    protected int width;
    protected int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }


    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

}
