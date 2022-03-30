package ru.project.musicbandsearch.controllers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.project.musicbandsearch.configurations.services.FilesStorageService;
import ru.project.musicbandsearch.configurations.models.FileInfo;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.models.ResponseMessage;
import ru.project.musicbandsearch.services.UsersService;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class FilesController {

    private final FilesStorageService storageService;

    private final UsersService usersService;

//    @PostMapping("/profile_edit")
//    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile file, Authentication authentication) {
//        String message = "";
//        User user = usersService.getUserByEmail(authentication.getName());
//        try {
//            storageService.save(file, user.getId());
//            user.setAvatar(true);
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }
//    @PostMapping("/profile_edit")
//    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile file, Authentication authentication) {
//        String message = "";
//        User user = usersService.getUserByEmail(authentication.getName());
//        try {
//            storageService.save(file, user.getId());
//            user.setAvatar(true);
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//    }
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
    // url по которому будут доступны сохраненные файлы
    @GetMapping("/avatar/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
