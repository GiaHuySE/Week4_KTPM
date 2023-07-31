package com.example.product.controller;


import com.example.product.model.Product;
import com.example.product.service.ProductServices;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-service")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping("/products")
    @Retry(name = "retryApi",fallbackMethod = "fallbackAfterRetry")
    @RateLimiter(name = "rateLimit")
    public List<Product> listProducts() {
        return productServices.listProducts();
    }

    public String fallbackAfterRetry(Exception ex) {
        return "all retries have exhausted";
    }

    @GetMapping("/product/{id}")
    public Product findByIntId(@PathVariable("id") int prdId) throws Exception {
        Product product = productServices.findByIntId(prdId);
        return product;
    }

    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product) {
        return productServices.addProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") int prdId) {
        Product product = productServices.findByIntId(prdId);
        productServices.deleteProduct(product);
        System.out.println("Đã xóa sản phẩm có mã: " +prdId);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int prdId, @RequestBody Product product) {
        Product newPrd = productServices.findByIntId(prdId);
        newPrd.setTitle(product.getTitle());
        newPrd.setPrice(product.getPrice());
        newPrd.setDescribeDetails(product.getDescribeDetails());
        newPrd.setLink(product.getLink());
        newPrd.setStatusPrd("true");

        productServices.updateProduct(newPrd);
        return ResponseEntity.ok().body(newPrd);
    }

}