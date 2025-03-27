package org.example.mainpriject.controller.ecommerceController;


import org.example.mainpriject.dto.ecommerceDTO.BusinessAccountDto;
import org.example.mainpriject.service.ecommerce_function.BusinessAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-account")
public class BusinessAccountController {

    private final BusinessAccountService businessAccountService;

    public BusinessAccountController(BusinessAccountService businessAccountService) {
        this.businessAccountService = businessAccountService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BusinessAccountDto> create(@RequestBody BusinessAccountDto businessAccountDto, @PathVariable Long userId) {
        return ResponseEntity.ok(businessAccountService.create(businessAccountDto, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BusinessAccountDto>> findAll(@PathVariable Long userId) {
        return ResponseEntity.ok(businessAccountService.findAll(userId));
    }

    @DeleteMapping("/{deleteAccountId}/{userId}")
    public ResponseEntity<BusinessAccountDto> delete(@PathVariable Long deleteAccountId, @PathVariable Long userId) {
        return ResponseEntity.ok(businessAccountService.deleteById(deleteAccountId, userId));
    }

    @GetMapping("/{userId}/{accountId}")
    public BusinessAccountDto findById(@PathVariable Long userId, @PathVariable Long accountId) {
        return businessAccountService.getAccountById(accountId, userId);
    }


}
