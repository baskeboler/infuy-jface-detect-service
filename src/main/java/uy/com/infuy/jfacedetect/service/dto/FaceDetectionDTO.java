package uy.com.infuy.jfacedetect.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FaceDetectionDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("faceAttributes")
    private FaceAttributes faceAttributes;
    @JsonProperty("faceLandmarks")
    private FaceLandmarks faceLandmarks;
    @JsonProperty("faceRectangle")
    private FaceRectangle faceRectangle;
    @JsonProperty("faceId")
    private String faceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FaceAttributes getFaceAttributes() {
        return faceAttributes;
    }

    public void setFaceAttributes(FaceAttributes faceAttributes) {
        this.faceAttributes = faceAttributes;
    }

    public FaceLandmarks getFaceLandmarks() {
        return faceLandmarks;
    }

    public void setFaceLandmarks(FaceLandmarks faceLandmarks) {
        this.faceLandmarks = faceLandmarks;
    }

    public FaceRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public static class FaceAttributes {
        @JsonProperty("emotion")
        private Emotion emotion;
        @JsonProperty("age")
        private double age;
        @JsonProperty("gender")
        private String gender;

        public Emotion getEmotion() {
            return emotion;
        }

        public void setEmotion(Emotion emotion) {
            this.emotion = emotion;
        }

        public double getAge() {
            return age;
        }

        public void setAge(double age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class Emotion {
        @JsonProperty("surprise")
        private double surprise;
        @JsonProperty("sadness")
        private double sadness;
        @JsonProperty("neutral")
        private double neutral;
        @JsonProperty("happiness")
        private int happiness;
        @JsonProperty("fear")
        private int fear;
        @JsonProperty("disgust")
        private double disgust;
        @JsonProperty("contempt")
        private double contempt;
        @JsonProperty("anger")
        private double anger;

        public double getSurprise() {
            return surprise;
        }

        public void setSurprise(double surprise) {
            this.surprise = surprise;
        }

        public double getSadness() {
            return sadness;
        }

        public void setSadness(double sadness) {
            this.sadness = sadness;
        }

        public double getNeutral() {
            return neutral;
        }

        public void setNeutral(double neutral) {
            this.neutral = neutral;
        }

        public int getHappiness() {
            return happiness;
        }

        public void setHappiness(int happiness) {
            this.happiness = happiness;
        }

        public int getFear() {
            return fear;
        }

        public void setFear(int fear) {
            this.fear = fear;
        }

        public double getDisgust() {
            return disgust;
        }

        public void setDisgust(double disgust) {
            this.disgust = disgust;
        }

        public double getContempt() {
            return contempt;
        }

        public void setContempt(double contempt) {
            this.contempt = contempt;
        }

        public double getAnger() {
            return anger;
        }

        public void setAnger(double anger) {
            this.anger = anger;
        }
    }

    public static class FaceLandmarks {
        @JsonProperty("underLipBottom")
        private UnderLipBottom underLipBottom;
        @JsonProperty("underLipTop")
        private UnderLipTop underLipTop;
        @JsonProperty("upperLipBottom")
        private UpperLipBottom upperLipBottom;
        @JsonProperty("upperLipTop")
        private UpperLipTop upperLipTop;
        @JsonProperty("noseRightAlarOutTip")
        private NoseRightAlarOutTip noseRightAlarOutTip;
        @JsonProperty("noseLeftAlarOutTip")
        private NoseLeftAlarOutTip noseLeftAlarOutTip;
        @JsonProperty("noseRightAlarTop")
        private NoseRightAlarTop noseRightAlarTop;
        @JsonProperty("noseLeftAlarTop")
        private NoseLeftAlarTop noseLeftAlarTop;
        @JsonProperty("noseRootRight")
        private NoseRootRight noseRootRight;
        @JsonProperty("noseRootLeft")
        private NoseRootLeft noseRootLeft;
        @JsonProperty("eyeRightOuter")
        private EyeRightOuter eyeRightOuter;
        @JsonProperty("eyeRightBottom")
        private EyeRightBottom eyeRightBottom;
        @JsonProperty("eyeRightTop")
        private EyeRightTop eyeRightTop;
        @JsonProperty("eyeRightInner")
        private EyeRightInner eyeRightInner;
        @JsonProperty("eyebrowRightOuter")
        private EyebrowRightOuter eyebrowRightOuter;
        @JsonProperty("eyebrowRightInner")
        private EyebrowRightInner eyebrowRightInner;
        @JsonProperty("eyeLeftInner")
        private EyeLeftInner eyeLeftInner;
        @JsonProperty("eyeLeftBottom")
        private EyeLeftBottom eyeLeftBottom;
        @JsonProperty("eyeLeftTop")
        private EyeLeftTop eyeLeftTop;
        @JsonProperty("eyeLeftOuter")
        private EyeLeftOuter eyeLeftOuter;
        @JsonProperty("eyebrowLeftInner")
        private EyebrowLeftInner eyebrowLeftInner;
        @JsonProperty("eyebrowLeftOuter")
        private EyebrowLeftOuter eyebrowLeftOuter;
        @JsonProperty("mouthRight")
        private MouthRight mouthRight;
        @JsonProperty("mouthLeft")
        private MouthLeft mouthLeft;
        @JsonProperty("noseTip")
        private NoseTip noseTip;
        @JsonProperty("pupilRight")
        private PupilRight pupilRight;
        @JsonProperty("pupilLeft")
        private PupilLeft pupilLeft;

        public UnderLipBottom getUnderLipBottom() {
            return underLipBottom;
        }

        public void setUnderLipBottom(UnderLipBottom underLipBottom) {
            this.underLipBottom = underLipBottom;
        }

        public UnderLipTop getUnderLipTop() {
            return underLipTop;
        }

        public void setUnderLipTop(UnderLipTop underLipTop) {
            this.underLipTop = underLipTop;
        }

        public UpperLipBottom getUpperLipBottom() {
            return upperLipBottom;
        }

        public void setUpperLipBottom(UpperLipBottom upperLipBottom) {
            this.upperLipBottom = upperLipBottom;
        }

        public UpperLipTop getUpperLipTop() {
            return upperLipTop;
        }

        public void setUpperLipTop(UpperLipTop upperLipTop) {
            this.upperLipTop = upperLipTop;
        }

        public NoseRightAlarOutTip getNoseRightAlarOutTip() {
            return noseRightAlarOutTip;
        }

        public void setNoseRightAlarOutTip(NoseRightAlarOutTip noseRightAlarOutTip) {
            this.noseRightAlarOutTip = noseRightAlarOutTip;
        }

        public NoseLeftAlarOutTip getNoseLeftAlarOutTip() {
            return noseLeftAlarOutTip;
        }

        public void setNoseLeftAlarOutTip(NoseLeftAlarOutTip noseLeftAlarOutTip) {
            this.noseLeftAlarOutTip = noseLeftAlarOutTip;
        }

        public NoseRightAlarTop getNoseRightAlarTop() {
            return noseRightAlarTop;
        }

        public void setNoseRightAlarTop(NoseRightAlarTop noseRightAlarTop) {
            this.noseRightAlarTop = noseRightAlarTop;
        }

        public NoseLeftAlarTop getNoseLeftAlarTop() {
            return noseLeftAlarTop;
        }

        public void setNoseLeftAlarTop(NoseLeftAlarTop noseLeftAlarTop) {
            this.noseLeftAlarTop = noseLeftAlarTop;
        }

        public NoseRootRight getNoseRootRight() {
            return noseRootRight;
        }

        public void setNoseRootRight(NoseRootRight noseRootRight) {
            this.noseRootRight = noseRootRight;
        }

        public NoseRootLeft getNoseRootLeft() {
            return noseRootLeft;
        }

        public void setNoseRootLeft(NoseRootLeft noseRootLeft) {
            this.noseRootLeft = noseRootLeft;
        }

        public EyeRightOuter getEyeRightOuter() {
            return eyeRightOuter;
        }

        public void setEyeRightOuter(EyeRightOuter eyeRightOuter) {
            this.eyeRightOuter = eyeRightOuter;
        }

        public EyeRightBottom getEyeRightBottom() {
            return eyeRightBottom;
        }

        public void setEyeRightBottom(EyeRightBottom eyeRightBottom) {
            this.eyeRightBottom = eyeRightBottom;
        }

        public EyeRightTop getEyeRightTop() {
            return eyeRightTop;
        }

        public void setEyeRightTop(EyeRightTop eyeRightTop) {
            this.eyeRightTop = eyeRightTop;
        }

        public EyeRightInner getEyeRightInner() {
            return eyeRightInner;
        }

        public void setEyeRightInner(EyeRightInner eyeRightInner) {
            this.eyeRightInner = eyeRightInner;
        }

        public EyebrowRightOuter getEyebrowRightOuter() {
            return eyebrowRightOuter;
        }

        public void setEyebrowRightOuter(EyebrowRightOuter eyebrowRightOuter) {
            this.eyebrowRightOuter = eyebrowRightOuter;
        }

        public EyebrowRightInner getEyebrowRightInner() {
            return eyebrowRightInner;
        }

        public void setEyebrowRightInner(EyebrowRightInner eyebrowRightInner) {
            this.eyebrowRightInner = eyebrowRightInner;
        }

        public EyeLeftInner getEyeLeftInner() {
            return eyeLeftInner;
        }

        public void setEyeLeftInner(EyeLeftInner eyeLeftInner) {
            this.eyeLeftInner = eyeLeftInner;
        }

        public EyeLeftBottom getEyeLeftBottom() {
            return eyeLeftBottom;
        }

        public void setEyeLeftBottom(EyeLeftBottom eyeLeftBottom) {
            this.eyeLeftBottom = eyeLeftBottom;
        }

        public EyeLeftTop getEyeLeftTop() {
            return eyeLeftTop;
        }

        public void setEyeLeftTop(EyeLeftTop eyeLeftTop) {
            this.eyeLeftTop = eyeLeftTop;
        }

        public EyeLeftOuter getEyeLeftOuter() {
            return eyeLeftOuter;
        }

        public void setEyeLeftOuter(EyeLeftOuter eyeLeftOuter) {
            this.eyeLeftOuter = eyeLeftOuter;
        }

        public EyebrowLeftInner getEyebrowLeftInner() {
            return eyebrowLeftInner;
        }

        public void setEyebrowLeftInner(EyebrowLeftInner eyebrowLeftInner) {
            this.eyebrowLeftInner = eyebrowLeftInner;
        }

        public EyebrowLeftOuter getEyebrowLeftOuter() {
            return eyebrowLeftOuter;
        }

        public void setEyebrowLeftOuter(EyebrowLeftOuter eyebrowLeftOuter) {
            this.eyebrowLeftOuter = eyebrowLeftOuter;
        }

        public MouthRight getMouthRight() {
            return mouthRight;
        }

        public void setMouthRight(MouthRight mouthRight) {
            this.mouthRight = mouthRight;
        }

        public MouthLeft getMouthLeft() {
            return mouthLeft;
        }

        public void setMouthLeft(MouthLeft mouthLeft) {
            this.mouthLeft = mouthLeft;
        }

        public NoseTip getNoseTip() {
            return noseTip;
        }

        public void setNoseTip(NoseTip noseTip) {
            this.noseTip = noseTip;
        }

        public PupilRight getPupilRight() {
            return pupilRight;
        }

        public void setPupilRight(PupilRight pupilRight) {
            this.pupilRight = pupilRight;
        }

        public PupilLeft getPupilLeft() {
            return pupilLeft;
        }

        public void setPupilLeft(PupilLeft pupilLeft) {
            this.pupilLeft = pupilLeft;
        }
    }

    public static class UnderLipBottom {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class UnderLipTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class UpperLipBottom {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class UpperLipTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseRightAlarOutTip {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseLeftAlarOutTip {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseRightAlarTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseLeftAlarTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseRootRight {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class NoseRootLeft {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeRightOuter {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeRightBottom {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeRightTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeRightInner {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private int x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    public static class EyebrowRightOuter {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyebrowRightInner {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeLeftInner {
        @JsonProperty("y")
        private int y;
        @JsonProperty("x")
        private double x;

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeLeftBottom {
        @JsonProperty("y")
        private int y;
        @JsonProperty("x")
        private double x;

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeLeftTop {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyeLeftOuter {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyebrowLeftInner {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class EyebrowLeftOuter {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class MouthRight {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class MouthLeft {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private int x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    public static class NoseTip {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class PupilRight {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }

    public static class PupilLeft {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private int x;

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }

    public static class FaceRectangle {
        @JsonProperty("left")
        private int left;
        @JsonProperty("top")
        private int top;
        @JsonProperty("width")
        private int width;
        @JsonProperty("height")
        private int height;

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

    @Override
    public String toString() {
        return "FaceDetectionDTO{" +
            "id=" + id +
            ", faceAttributes=" + faceAttributes +
            ", faceLandmarks=" + faceLandmarks +
            ", faceRectangle=" + faceRectangle +
            ", faceId='" + faceId + '\'' +
            '}';
    }
}
