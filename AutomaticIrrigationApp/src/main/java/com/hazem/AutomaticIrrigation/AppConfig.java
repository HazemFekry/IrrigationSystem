package com.hazem.AutomaticIrrigation;

import com.hazem.AutomaticIrrigation.service.IrrigationService;
import com.hazem.AutomaticIrrigation.service.SchedulerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@EnableRetry
@PropertySource("classpath:retryConfig.properties")
@PropertySource("classpath:sensor.properties")
public class AppConfig {

    //should be configured dynamically
    final public static int TIMEZONE_OFFSET = 3;

    @Bean
    public CommandLineRunner init(ApplicationContext ctx) {
        return (args) -> {
            ctx.getBean(IrrigationService.class).fillNextIrrigation();
            ctx.getBean(SchedulerService.class).setupTimeSlotThreadsForToday();
        };
    }

}
