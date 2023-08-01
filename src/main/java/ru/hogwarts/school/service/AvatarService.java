package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exeption.AvatarNotFoundExeption;
import ru.hogwarts.school.exeption.AvatarProcessingException;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final Path pathToAvatarDir;
    private  final AvatarMapper avatarMapper;

    public AvatarService(AvatarRepository avatarRepository,
                         @Value("${path.to.avatar.dir}") String pathToAvatarDir, AvatarMapper avatarMapper) {

        this.avatarRepository = avatarRepository;
        this.pathToAvatarDir = Path.of(pathToAvatarDir);
        this.avatarMapper = avatarMapper;
    }

   public Avatar create(Student student, MultipartFile multipartFile) {
        try {
            String contentType = multipartFile.getContentType();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            byte[] data = multipartFile.getBytes();

            String fileName = UUID.randomUUID() + "." + extension;   // Генерация названия

            Path pathToAvatar = pathToAvatarDir.resolve(fileName);  //Сохранение на диск
            Files.write(pathToAvatar, data);

            Avatar avatar = avatarRepository.findByStudent_Id(student.getId())
                    .orElse(new Avatar());
            avatar.setMediaType(contentType);
            avatar.setFileSize(data.length);
            avatar.setData(data);
            avatar.setStudent(student);
            avatar.setFilePath(pathToAvatar.toString());
            return avatarRepository.save(avatar);
        }
        catch (IOException e){
            throw new AvatarProcessingException();
        }
   }

   public Pair <byte[], String> getFromDb(long id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundExeption(id));
        return Pair.of(avatar.getData(), avatar.getMediaType());
   }

    public Pair <byte[], String> getFromFs(long id) {
        try {
            Avatar avatar = avatarRepository.findById(id)
                    .orElseThrow(() -> new AvatarNotFoundExeption(id));
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        } catch (IOException e) {
            throw new AvatarProcessingException();
        }

    }


    public List<AvatarDto> getPage(int page, int size) {
        return avatarRepository.findAll(PageRequest.of(page, size)).stream()
                .map(avatarMapper :: toDto)
                .collect(Collectors.toList());
    }
}
