package com.example.ics.utils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

public class RequestThrottler {
    private static final int REQUEST_LIMIT = 5;
    private static final int REQUEST_INTERVAL_MINUTES = 1;

    private final Bucket bucket;

    public RequestThrottler() {
        Bandwidth limit = Bandwidth.classic(REQUEST_LIMIT, Refill.intervally(REQUEST_LIMIT, Duration.ofMinutes(REQUEST_INTERVAL_MINUTES)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    public void  consumeRequest() {
        // Check if the request exceeds the rate limit
        if (!bucket.tryConsume(1)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests");
        }
    }
}