// 代码生成时间: 2025-10-01 18:20:31
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

// Service component for handling load balancing
@Service
public class LoadBalancerService {

    // Injecting LoadBalancerClient for service instance selection
    private final LoadBalancerClient loadBalancerClient;

    // Constructor injection
    public LoadBalancerService(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    // Method to get a service instance by name
    public URI getServiceInstance(String serviceId) {
        Optional<ServiceInstance> instance = loadBalancerClient.choose(serviceId);
        return instance.map(ServiceInstance::getUri).orElseThrow(() -> new RuntimeException("No service instance found for: " + serviceId));
    }

    // Method to create a RestTemplate with load balancing
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(LoadBalancerClient loadBalancerClient) {
        return new RestTemplate();
    }

    // Example usage of restTemplate to make a call to another service
    public String callAnotherService(String serviceId, String uri) {
        try {
            URI serviceUri = getServiceInstance(serviceId);
            URI requestUri = UriComponentsBuilder.fromUri(serviceUri).path(uri).build().encode().toUri();
            return restTemplate.getForObject(requestUri, String.class);
        } catch (Exception e) {
            // Log and handle the exception as needed
            // For simplicity, we're just printing the stack trace here
            e.printStackTrace();
            return "Error calling service: " + e.getMessage();
        }
    }
}