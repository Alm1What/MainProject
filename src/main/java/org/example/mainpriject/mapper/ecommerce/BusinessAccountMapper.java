package org.example.mainpriject.mapper.ecommerce;

import org.example.mainpriject.dto.ecommerceDTO.BusinessAccountDto;
import org.example.mainpriject.model.BusinessAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusinessAccountMapper {

    BusinessAccountDto toDto(BusinessAccount businessAccount);

    @Mapping(target = "id", ignore = true) // генерується окремо
    @Mapping(target = "userId", ignore = true) // передається окремо
    @Mapping(target = "reviews", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "products", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    BusinessAccount toEntity(BusinessAccountDto businessAccountDto);
}
