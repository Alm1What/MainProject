package org.example.mainpriject.service.ecommerce_function;

import org.example.mainpriject.dto.ecommerceDTO.BusinessAccountDto;
import org.example.mainpriject.mapper.ecommerce.BusinessAccountMapper;
import org.example.mainpriject.model.BusinessAccount;
import org.example.mainpriject.model.User;
import org.example.mainpriject.repository.BusinessAccountRepository;
import org.example.mainpriject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessAccountService {

    private final BusinessAccountRepository businessAccountRepository;
    private final UserService userService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final BusinessAccountMapper businessAccountMapper;

    @Autowired
    public BusinessAccountService(BusinessAccountRepository businessAccountRepository,
                                  UserService userService,
                                  SequenceGeneratorService sequenceGeneratorService,
                                  BusinessAccountMapper businessAccountMapper) {
        this.businessAccountRepository = businessAccountRepository;
        this.userService = userService;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.businessAccountMapper = businessAccountMapper;
    }

    public BusinessAccountDto create(BusinessAccountDto businessAccountDto, Long userId) {

        if (businessAccountRepository.findAllByUserId(userId).size() >= 2) {
            throw new RuntimeException("Too many business accounts, max is 2");
        }

        if (businessAccountRepository.findByNameOfCompany(businessAccountDto.getNameOfCompany())) {
            throw new RuntimeException("Business account with name of company already exists");
        }

        checkAuthorized(userId);

        if (businessAccountDto.getNameOfCompany() == null ||
                businessAccountDto.getNameOfCompany().isEmpty()) {
            throw new RuntimeException("Name of company is required");
        }

        BusinessAccount businessAccount = businessAccountMapper.toEntity(businessAccountDto);

        businessAccount.setId(sequenceGeneratorService.generateSequence(BusinessAccount.SEQUENCE_NAME));
        businessAccount.setUserId(userId);

        BusinessAccount savedBusinessAccount = businessAccountRepository.save(businessAccount);

        return businessAccountMapper.toDto(savedBusinessAccount);

    }

    public List<BusinessAccountDto> findAll(Long userId) {
        checkAuthorized(userId);
        return businessAccountRepository.findAllByUserId(userId);
    }

    public BusinessAccountDto deleteById(Long deleteAccountId, Long userId) {
        checkAuthorized(userId);
        if (deleteAccountId == null || deleteAccountId <= 0) {
            throw new RuntimeException("Id is required");
        }
        BusinessAccount account = businessAccountRepository.findById(deleteAccountId);

        businessAccountRepository.delete(account);
        return businessAccountMapper.toDto(account);
    }

    public BusinessAccountDto getAccountById(Long id, Long userId) {
        checkAuthorized(userId);
        if (id == null || id <= 0) {
            throw new RuntimeException("Id is required");
        }
        BusinessAccount account = businessAccountRepository.findById(id);
        return businessAccountMapper.toDto(account);
    }

    public BusinessAccountDto update(BusinessAccountDto businessAccountDto, Long userId, Long accountId) {
        checkAuthorized(userId);
        if (businessAccountRepository.findById(accountId) == null) {
            throw new RuntimeException("Account with id " + accountId + " does not exist");
        }

        if (businessAccountDto.getNameOfCompany() == null ||
                businessAccountDto.getNameOfCompany().isEmpty()) {
            throw new RuntimeException("Name of company is required");
        }

        BusinessAccount account = businessAccountRepository.findById(accountId);
        account.setNameOfCompany(businessAccountDto.getNameOfCompany());
        BusinessAccount savedBusinessAccount = businessAccountRepository.save(account);
        return businessAccountMapper.toDto(savedBusinessAccount);
    }


    public boolean checkAuthorized(Long userId) {
        User user = userService.getCurrentUser();
        if (user.getId() != userId || userId == null || userId == 0) {
            throw new RuntimeException("User is not logged in");
        } else {
            return true;
        }
    }


}
