package org.example.mainpriject.mapper;

import org.example.mainpriject.dto.UserDto;
import org.example.mainpriject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    // Перетворення з UserDto в User з ігноруванням полів, які не потрібно змінювати
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "restorationAt", ignore = true)
    @Mapping(target = "password", ignore = true) // Ігнорую пароль, щоб не перезаписувати зашифрований пароль
    User toEntity(UserDto userDto);

    // Метод для оновлення існуючого користувача
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "restorationAt", ignore = true)
    @Mapping(target = "password", ignore = true) // Не змінюємо пароль під час оновлення профілю
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "restorationAt", ignore = true)
    User toUser(UserDto userDto);
}