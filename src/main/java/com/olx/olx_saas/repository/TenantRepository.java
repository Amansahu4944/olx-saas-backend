package com.olx.olx_saas.repository;

import com.olx.olx_saas.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface talks to the 'tenants' table
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}