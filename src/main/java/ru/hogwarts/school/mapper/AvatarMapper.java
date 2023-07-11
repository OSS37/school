package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.entity.Avatar;

@Component
public class AvatarMapper {
    public AvatarDto toDto (Avatar avatar) {
        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setId(avatar.getId());
        avatarDto.setFileSize(avatarDto.getFileSize());
        avatarDto.setMediaType(avatarDto.getMediaType());
        avatarDto.setAvatarUrl("http://localhost:8888/avatars/" + avatar.getId() + "from-db");
        return avatarDto;

    }
}
