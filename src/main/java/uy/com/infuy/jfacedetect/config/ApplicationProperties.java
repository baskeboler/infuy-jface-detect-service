package uy.com.infuy.jfacedetect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhipster.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final FaceApi faceApi = new FaceApi();
    private final Uploads uploads = new Uploads();
    private final Faces faces = new Faces();

    public Faces getFaces() {
        return faces;
    }

    public FaceApi getFaceApi() {
        return faceApi;
    }

    public Uploads getUploads() {
        return uploads;
    }

    public static class FaceApi {
        private String endpoint;

        private String apiKey;

        public String getEndpoint() {
            return endpoint;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

    }

    public static class Faces {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
    public static class Uploads {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


}
