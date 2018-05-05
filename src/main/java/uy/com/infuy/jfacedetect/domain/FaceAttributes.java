package uy.com.infuy.jfacedetect.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class FaceAttributes {

    protected String gender;
    protected Double age;

    @Embedded
    protected Emotion emotion;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }


}
