package com.olx.olx_saas.service;

import com.olx.olx_saas.model.Product;
import com.olx.olx_saas.model.Tenant;
import com.olx.olx_saas.model.User;
import com.olx.olx_saas.repository.ProductRepository;
import com.olx.olx_saas.repository.TenantRepository;
import com.olx.olx_saas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder; // Import this
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Helper Method: Currently Logged-in User ko dhundho
    private User getCurrentUser() {
        // SecurityContext se email nikalo
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElse(null);
    }

    public Product saveProduct(Product product) {
        // 1. Current User lo (Jo bhi login hai)
        User user = getCurrentUser();
        
        // 2. Uska Tenant lo
        Tenant tenant = user.getTenant();
        
        // 3. Product me set karo
        product.setTenant(tenant);
        product.setUser(user);
        
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        
        User user = getCurrentUser();
        
       
        return productRepository.findByTenantId(user.getTenant().getId());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}