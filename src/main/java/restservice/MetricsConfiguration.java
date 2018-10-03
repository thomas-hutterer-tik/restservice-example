package restservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
 
/**
 * Configuration bean for metrics.
 * 
 * @author ru-rocker
 *
 */
/*@Configuration
public class MetricsConfiguration {
 
    /**
     * Register common tags application instead of job.
     * This application tag is needed for Grafana dashboard.
     *
     * @return registry with registered tags.
     */
/*    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            registry.config().commonTags("application", "restservice-example");
        };
    }
 
}
*/