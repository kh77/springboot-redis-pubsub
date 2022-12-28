package com.sm.config;

import com.sm.constant.RedisConstant;
import com.sm.receiver.EmployeeSubscriber;
import com.sm.receiver.NotificationSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter(), employeeTopic());
        container.addMessageListener(notificationListenerAdapter(), notificationTopic());
        return container;
    }


    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new EmployeeSubscriber(), "onMessage");
    }

    @Bean
    public MessageListenerAdapter notificationListenerAdapter() {
        return new MessageListenerAdapter(new NotificationSubscriber(), "onMessage");
    }

    @Bean
    public ChannelTopic employeeTopic() {
        return new ChannelTopic(RedisConstant.EMPLOYEE_TOPIC);
    }

    @Bean
    public ChannelTopic notificationTopic() {
        return new ChannelTopic(RedisConstant.NOTIFICATION_TOPIC);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return redisTemplate;
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        LettuceClientConfiguration lettuceClientConfiguration =
//                LettuceClientConfiguration.builder()
//                        .commandTimeout(Duration.ofSeconds(30000))
//                        .clientOptions(ClientOptions.builder().build())
//                        .build();
//
//
////            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
////            clusterConfiguration.clusterNode(host, port);
////            new LettuceConnectionFactory(clusterConfiguration);
////
////            return new LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration);
//
////        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
////        clusterConfiguration.clusterNode(host, port);
////        new LettuceConnectionFactory(clusterConfiguration);
//
//        return new LettuceConnectionFactory((RedisStandaloneConfiguration) lettuceClientConfiguration);
//
////        else {
////            RedisStandaloneConfiguration redisStandaloneConfiguration =
////                    new RedisStandaloneConfiguration(host, port);
////            redisStandaloneConfiguration.setDatabase(database);
////
////            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
////            jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
////            return jedisConnectionFactory;
////        }
//    }

//    private JedisPoolConfig jedisPoolConfig() {
//        final JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(128);
//        poolConfig.setMaxIdle(128);
//        poolConfig.setMinIdle(36);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
//        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
//        poolConfig.setNumTestsPerEvictionRun(3);
//        poolConfig.setBlockWhenExhausted(true);
//        return poolConfig;
//    }
}
