package uy.com.infuy.jfacedetect.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Position {
    protected Double x;
    protected Double y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
