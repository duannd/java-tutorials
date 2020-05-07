package com.duanndz.repositories;

import com.duanndz.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * duan.nguyen
 * Datetime 5/7/20 16:47
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
