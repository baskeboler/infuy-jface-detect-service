package uy.com.infuy.jfacedetect.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(uy.com.infuy.jfacedetect.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonGroup.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonGroup.class.getName() + ".people", jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonGroupPerson.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonGroupPerson.class.getName() + ".faces", jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.Face.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.Image.class.getName() + ".faces", jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.FaceDetection.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonFaceSubmission.class.getName(), jcacheConfiguration);
            cm.createCache(uy.com.infuy.jfacedetect.domain.PersonFaceIdentification.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
