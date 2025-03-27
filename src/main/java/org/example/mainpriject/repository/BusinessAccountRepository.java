package org.example.mainpriject.repository;


import org.example.mainpriject.dto.ecommerceDTO.BusinessAccountDto;
import org.example.mainpriject.model.BusinessAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessAccountRepository extends MongoRepository<BusinessAccount, String> {
    List<BusinessAccountDto> findAllByUserId(Long userId);
    BusinessAccount findById(Long id);
    boolean findByNameOfCompany(String nameOfCompany);
}
