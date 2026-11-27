package com.prysh.product_service.service;

import com.prysh.product_service.dto.ProductRequest;
import com.prysh.product_service.dto.ProductResponse;
import com.prysh.product_service.entity.Product;
import com.prysh.product_service.exception.DuplicateProductSkuException;
import com.prysh.product_service.exception.ProductNotFoundException;
import com.prysh.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        String sku= normalizeSku(request.sku());
        if(productRepository.existsBySku(sku)){
            throw new DuplicateProductSkuException("Product with sku "+sku+" already exists");
        }
        Product product=new Product();

        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setSku(sku);
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> productList=productRepository.findAll();
        return productList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found with id: "+id)
                );
        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest request,Long id){
        Product product=productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: "+id));

        String sku=normalizeSku(request.sku());
        if(productRepository.existsBySkuAndIdNot(sku,id)){
            throw new DuplicateProductSkuException("Product with sku "+sku+" already exists");
        }


        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setSku(sku);
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }
    @Transactional
    public String deleteProduct(Long id){
        Product product=productRepository.findById(id)
                .orElseThrow(()->
                    new ProductNotFoundException("Product not found with id: "+id)
                );
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    public String normalizeSku(String sku){
        return sku.trim().toUpperCase(Locale.ROOT);
    }
    public ProductResponse mapToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
