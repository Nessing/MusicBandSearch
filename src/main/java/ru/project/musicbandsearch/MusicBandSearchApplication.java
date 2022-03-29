package ru.project.musicbandsearch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.project.musicbandsearch.configurations.services.FilesStorageService;

import javax.annotation.Resource;

@SpringBootApplication
public class MusicBandSearchApplication implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(MusicBandSearchApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
//        storageService.deleteAll();
        storageService.init();
    }

}
