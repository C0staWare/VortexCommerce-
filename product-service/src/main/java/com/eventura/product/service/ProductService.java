package com.eventura.product.service;

import com.eventura.product.model.Product;
import com.eventura.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReactiveRedisOperations<String, Product> redisOperations;
    private static final String CACHE_KEY = "product:";

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(Long id) {
        String key = CACHE_KEY + id;
        return redisOperations.opsForValue().get(key)
                .doOnNext(p -> log.info("Caché hit para producto: {}", id))
                .switchIfEmpty(
                        productRepository.findById(id)
                                .flatMap(p -> redisOperations.opsForValue().set(key, p, Duration.ofMinutes(10))
                                        .thenReturn(p))
                                .doOnNext(p -> log.info("Caché miss para producto: {}, cargado de DB", id)));
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product)
                .flatMap(p -> redisOperations.opsForValue().delete(CACHE_KEY + p.getId()) // Invalidar caché
                        .thenReturn(p));
    }
}