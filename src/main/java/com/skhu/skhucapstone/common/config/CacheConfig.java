package com.skhu.skhucapstone.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    public static final String PROJECT_RECRUITMENT_CACHE = "projectRecruitment";

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // TTL 10분: 모집 글은 수정 빈도가 낮아 정합성 위험이 작고,
                // 무효화가 누락되는 예외 상황에서도 오래된 데이터 노출을 10분 이내로 제한한다.
                .entryTtl(Duration.ofMinutes(10))
                // 다른 애플리케이션과 Redis를 공유해도 키가 충돌하지 않도록 프리픽스를 붙인다.
                .computePrefixWith(cacheName -> "capstone:" + cacheName + "::")
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(jsonSerializer()));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

    // 역직렬화 시 원래 타입으로 복원되도록 JSON에 타입 정보(@class)를 함께 저장한다.
    // 임의 클래스 역직렬화 공격을 막기 위해 허용 패키지를 제한한다.
    private GenericJacksonJsonRedisSerializer jsonSerializer() {
        BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.skhu.skhucapstone.")
                .allowIfSubType("java.util.")
                .allowIfSubType("java.time.")
                .build();

        return GenericJacksonJsonRedisSerializer.builder()
                .enableDefaultTyping(typeValidator)
                .build();
    }

    // Redis 장애 시 캐시 예외를 삼키고 DB 조회로 폴백해 서비스 전체가 중단되지 않게 한다.
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.warn("캐시 조회 실패 (cache={}, key={}) - DB 조회로 폴백", cache.getName(), key, exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.warn("캐시 저장 실패 (cache={}, key={})", cache.getName(), key, exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.warn("캐시 무효화 실패 (cache={}, key={}) - TTL 만료로 정리됨", cache.getName(), key, exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.warn("캐시 전체 삭제 실패 (cache={})", cache.getName(), exception);
            }
        };
    }
}
