package org.example.mainpriject.mapper.ecommerce;


import org.example.mainpriject.dto.ecommerceDTO.ProductDto;
import org.example.mainpriject.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true) // Ігноруємо ID, оскільки MongoDB сама його згенерує
    @Mapping(target = "createdAt", ignore = true) // Дату проставимо в сервісі
    Product toEntity(ProductDto productDto);
}
