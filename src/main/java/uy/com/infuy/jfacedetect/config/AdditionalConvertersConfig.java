package uy.com.infuy.jfacedetect.config;

import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class AdditionalConvertersConfig {
    private static final Logger logger = LoggerFactory.getLogger(AdditionalConvertersConfig.class);

    @PostConstruct
    public void init() {
        logger.debug("Initializing Additional Converters Config");
    }

    @Bean
    public HttpMessageConverter getOctetStreamConverter() {
        logger.info("Returnin new converter");
        return new AbstractHttpMessageConverter<InputStream>(MediaType.APPLICATION_OCTET_STREAM) {
            @Override
            protected boolean supports(Class<?> clazz) {
                return InputStream.class.isAssignableFrom(clazz);
            }

            @Override
            protected InputStream readInternal(Class<? extends InputStream> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                return inputMessage.getBody();
            }

            @Override
            protected void writeInternal(InputStream inputStream, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                IOUtils.copy(inputStream, outputMessage.getBody());
            }
        };
    }

    @Bean public BufferedImageHttpMessageConverter getBufferedImageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
