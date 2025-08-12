package com.justsearch.backend.mapper;
import com.justsearch.backend.dto.NotificationDto;
import com.justsearch.backend.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto toDto(Notification notification);

    List<NotificationDto> toDtoList(List<Notification> notifications);
    Notification toEntity(NotificationDto notificationDto);
    List<Notification> toEntityList(List<NotificationDto> notificationDtos);
}
