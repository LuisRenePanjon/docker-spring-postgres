package com.panjon.restapiproducts.controller;

import com.panjon.restapiproducts.exception.ResourceNotFoundException;
import com.panjon.restapiproducts.model.Product;
import com.panjon.restapiproducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("test")
    public String testing(){
        return "rest with java";
    }


    @GetMapping("products")
    public List<Product> getProducts(){
        return this.productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId)
            throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist product with follow id: "+ productId));
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("products")
    public Product createProduct(@RequestBody Product product){
        return this.productRepository.save(product);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId,
                                                 @Validated @RequestBody Product productDetails)
                                                    throws ResourceNotFoundException{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist product with follow id: "+ productId));
        product.setImage(productDetails.getImage());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        return ResponseEntity.ok(this.productRepository.save(product));
    }

    @DeleteMapping("products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist product with follow id: "+ productId));
        this.productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}
