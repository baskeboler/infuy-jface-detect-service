package uy.com.infuy.jfacedetect.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class FaceLandmarks {

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="pupil_left_x")),
        @AttributeOverride(name="y", column=@Column(name="pupil_left_y"))
    })
    protected Position pupilLeft;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="pupil_right_x")),
        @AttributeOverride(name="y", column=@Column(name="pupil_right_y"))
    })
    protected Position pupilRight;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_tip_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_tip_y"))
    })
    protected Position noseTip;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="mouth_left_x")),
        @AttributeOverride(name="y", column=@Column(name="mouth_left_y"))
    })
    protected Position mouthLeft;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="mouth_right_x")),
        @AttributeOverride(name="y", column=@Column(name="mouth_right_y"))
    })
    protected Position mouthRight;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eyebrow_left_outer_x")),
        @AttributeOverride(name="y", column=@Column(name="eyebrow_left_outer_y"))
    })
    protected Position eyebrowLeftOuter;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eyebrow_left_inner_x")),
        @AttributeOverride(name="y", column=@Column(name="eyebrow_left_inner_y"))
    })
    protected Position eyebrowLeftInner;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_left_outer_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_left_outer_y"))
    })
    protected Position eyeLeftOuter;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_left_top_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_left_top_y"))
    })
    protected Position eyeLeftTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_left_bottom_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_left_bottom_y"))
    })
    protected Position eyeLeftBottom;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_left_inner_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_left_inner_y"))
    })
    protected Position eyeLeftInner;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eyebrow_right_inner_x")),
        @AttributeOverride(name="y", column=@Column(name="eyebrow_right_inner_y"))
    })
    protected Position eyebrowRightInner;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eyebrow_right_outer_x")),
        @AttributeOverride(name="y", column=@Column(name="eyebrow_right_outer_y"))
    })
    protected Position eyebrowRightOuter;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_right_inner_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_right_inner_y"))
    })
    protected Position eyeRightInner;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_right_top_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_right_top_y"))
    })
    protected Position eyeRightTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_right_bottom_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_right_bottom_y"))
    })
    protected Position eyeRightBottom;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="eye_right_outer_x")),
        @AttributeOverride(name="y", column=@Column(name="eye_right_outer_y"))
    })
    protected Position eyeRightOuter;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_root_left_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_root_left_y"))
    })
    protected Position noseRootLeft;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_left_alar_top_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_left_alar_top_y"))
    })
    protected Position noseLeftAlarTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_right_alar_top_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_right_alar_top_y"))
    })
    protected Position noseRightAlarTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_left_alar_out_tip_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_left_alar_out_tip_y"))
    })
    protected Position noseLeftAlarOutTip;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="nose_right_alar_out_tip_x")),
        @AttributeOverride(name="y", column=@Column(name="nose_right_alar_out_tip_y"))
    })
    protected Position noseRightAlarOutTip;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="upper_lip_top_x")),
        @AttributeOverride(name="y", column=@Column(name="upper_lip_top_y"))
    })
    protected Position upperLipTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="upper_lip_bottom_x")),
        @AttributeOverride(name="y", column=@Column(name="upper_lip_bottom_y"))
    })
    protected Position upperLipBottom;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="under_lip_top_x")),
        @AttributeOverride(name="y", column=@Column(name="under_lip_top_y"))
    })
    protected Position underLipTop;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="x", column=@Column(name="under_lip_bottom_x")),
        @AttributeOverride(name="y", column=@Column(name="under_lip_bottom_y"))
    })
    protected Position underLipBottom;

    public Position getPupilLeft() {
        return pupilLeft;
    }

    public void setPupilLeft(Position pupilLeft) {
        this.pupilLeft = pupilLeft;
    }

    public Position getPupilRight() {
        return pupilRight;
    }

    public void setPupilRight(Position pupilRight) {
        this.pupilRight = pupilRight;
    }

    public Position getNoseTip() {
        return noseTip;
    }

    public void setNoseTip(Position noseTip) {
        this.noseTip = noseTip;
    }

    public Position getMouthLeft() {
        return mouthLeft;
    }

    public void setMouthLeft(Position mouthLeft) {
        this.mouthLeft = mouthLeft;
    }

    public Position getMouthRight() {
        return mouthRight;
    }

    public void setMouthRight(Position mouthRight) {
        this.mouthRight = mouthRight;
    }

    public Position getEyebrowLeftOuter() {
        return eyebrowLeftOuter;
    }

    public void setEyebrowLeftOuter(Position eyebrowLeftOuter) {
        this.eyebrowLeftOuter = eyebrowLeftOuter;
    }

    public Position getEyebrowLeftInner() {
        return eyebrowLeftInner;
    }

    public void setEyebrowLeftInner(Position eyebrowLeftInner) {
        this.eyebrowLeftInner = eyebrowLeftInner;
    }

    public Position getEyeLeftOuter() {
        return eyeLeftOuter;
    }

    public void setEyeLeftOuter(Position eyeLeftOuter) {
        this.eyeLeftOuter = eyeLeftOuter;
    }

    public Position getEyeLeftTop() {
        return eyeLeftTop;
    }

    public void setEyeLeftTop(Position eyeLeftTop) {
        this.eyeLeftTop = eyeLeftTop;
    }

    public Position getEyeLeftBottom() {
        return eyeLeftBottom;
    }

    public void setEyeLeftBottom(Position eyeLeftBottom) {
        this.eyeLeftBottom = eyeLeftBottom;
    }

    public Position getEyeLeftInner() {
        return eyeLeftInner;
    }

    public void setEyeLeftInner(Position eyeLeftInner) {
        this.eyeLeftInner = eyeLeftInner;
    }

    public Position getEyebrowRightInner() {
        return eyebrowRightInner;
    }

    public void setEyebrowRightInner(Position eyebrowRightInner) {
        this.eyebrowRightInner = eyebrowRightInner;
    }

    public Position getEyebrowRightOuter() {
        return eyebrowRightOuter;
    }

    public void setEyebrowRightOuter(Position eyebrowRightOuter) {
        this.eyebrowRightOuter = eyebrowRightOuter;
    }

    public Position getEyeRightInner() {
        return eyeRightInner;
    }

    public void setEyeRightInner(Position eyeRightInner) {
        this.eyeRightInner = eyeRightInner;
    }

    public Position getEyeRightTop() {
        return eyeRightTop;
    }

    public void setEyeRightTop(Position eyeRightTop) {
        this.eyeRightTop = eyeRightTop;
    }

    public Position getEyeRightBottom() {
        return eyeRightBottom;
    }

    public void setEyeRightBottom(Position eyeRightBottom) {
        this.eyeRightBottom = eyeRightBottom;
    }

    public Position getEyeRightOuter() {
        return eyeRightOuter;
    }

    public void setEyeRightOuter(Position eyeRightOuter) {
        this.eyeRightOuter = eyeRightOuter;
    }

    public Position getNoseRootLeft() {
        return noseRootLeft;
    }

    public void setNoseRootLeft(Position noseRootLeft) {
        this.noseRootLeft = noseRootLeft;
    }

    public Position getNoseLeftAlarTop() {
        return noseLeftAlarTop;
    }

    public void setNoseLeftAlarTop(Position noseLeftAlarTop) {
        this.noseLeftAlarTop = noseLeftAlarTop;
    }

    public Position getNoseRightAlarTop() {
        return noseRightAlarTop;
    }

    public void setNoseRightAlarTop(Position noseRightAlarTop) {
        this.noseRightAlarTop = noseRightAlarTop;
    }

    public Position getNoseLeftAlarOutTip() {
        return noseLeftAlarOutTip;
    }

    public void setNoseLeftAlarOutTip(Position noseLeftAlarOutTip) {
        this.noseLeftAlarOutTip = noseLeftAlarOutTip;
    }

    public Position getNoseRightAlarOutTip() {
        return noseRightAlarOutTip;
    }

    public void setNoseRightAlarOutTip(Position noseRightAlarOutTip) {
        this.noseRightAlarOutTip = noseRightAlarOutTip;
    }

    public Position getUpperLipTop() {
        return upperLipTop;
    }

    public void setUpperLipTop(Position upperLipTop) {
        this.upperLipTop = upperLipTop;
    }

    public Position getUpperLipBottom() {
        return upperLipBottom;
    }

    public void setUpperLipBottom(Position upperLipBottom) {
        this.upperLipBottom = upperLipBottom;
    }

    public Position getUnderLipTop() {
        return underLipTop;
    }

    public void setUnderLipTop(Position underLipTop) {
        this.underLipTop = underLipTop;
    }

    public Position getUnderLipBottom() {
        return underLipBottom;
    }

    public void setUnderLipBottom(Position underLipBottom) {
        this.underLipBottom = underLipBottom;
    }
}
