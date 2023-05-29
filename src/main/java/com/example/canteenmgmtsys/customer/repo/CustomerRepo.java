package com.example.canteenmgmtsys.customer.repo;

import com.example.canteenmgmtsys.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
    @Query(value = "select * from customer where email=?1 or phone_number=?2", nativeQuery = true)
    List<Customer> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<Customer> findByEmail(String email);
}
