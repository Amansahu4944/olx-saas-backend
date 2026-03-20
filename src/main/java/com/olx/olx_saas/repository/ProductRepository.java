package com.olx.olx_saas.repository;

import com.olx.olx_saas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Yeh method Tenant ke basis pe data filter karega
    List<Product> findByTenantId(Long tenantId);
}