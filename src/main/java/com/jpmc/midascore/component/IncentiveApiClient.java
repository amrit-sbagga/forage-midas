package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveApiClient {

    private final String incentiveApiUrl;
    // = "http://localhost:8080/incentive";
    private final RestTemplate restTemplate;

    public IncentiveApiClient(
        @Value("${general.incentive-api-url}") String incentiveApiUrl,
        RestTemplateBuilder builder) {
        this.incentiveApiUrl = incentiveApiUrl;
        this.restTemplate = builder.build();
    }

    public float getIncentive(Transaction transaction) {
        Incentive incentive = restTemplate.postForObject(
                incentiveApiUrl,
                transaction,      // Spring serializes this as JSON
                Incentive.class   // Spring deserializes response
        );
        return incentive != null ? incentive.getAmount() : 0f;
    }
}