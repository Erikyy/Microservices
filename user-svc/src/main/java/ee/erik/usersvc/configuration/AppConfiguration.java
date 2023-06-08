package ee.erik.usersvc.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class AppConfiguration {


    @Bean
    public NewTopic accountTopic() {
        return TopicBuilder.name("account")
                .partitions(4)
                .replicas(1)
                .build();
    }
}
