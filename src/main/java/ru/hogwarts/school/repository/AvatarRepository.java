package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.entity.Avatar;

public interface AvatarRepository extends JpaRepository <Avatar, Long>{



}
