package com.algoarena.filter;

import com.algoarena.util.UserDetailsGetter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final Map<String, Bucket> submitAPIBuckets = new ConcurrentHashMap<>();

    @Autowired
    private UserDetailsGetter userDetailsGetter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws java.io.IOException, ServletException {

        boolean isSubmitRequest = request.getRequestURI().equals("/api/submit");

        Bucket bucket = isSubmitRequest ?
                submitAPIBuckets.computeIfAbsent(userDetailsGetter.getUserName(), userId -> createNewBucketForSubmitRequest()) :
                buckets.computeIfAbsent(request.getRemoteAddr(), k -> createNewBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests! Slow Down!");
        }
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.greedy(5, Duration.ofSeconds(10)); // 10 requests refilled per 10 secs
        Bandwidth limit = Bandwidth.classic(5, refill); // Capacity and initial tokens in the bucket
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket createNewBucketForSubmitRequest() {
        Refill refill = Refill.greedy(1, Duration.ofSeconds(5)); // 1 requests refilled per 5 secs
        Bandwidth limit = Bandwidth.classic(1, refill); // Capacity and initial tokens in the bucket
        return Bucket.builder().addLimit(limit).build();
    }
}
