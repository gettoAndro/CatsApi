package com.getto.artos;

import com.getto.artos.domain.Cat;
import com.getto.artos.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "cats")
public class ArtOsController {
    @Autowired

    private CatRepository catRepository;


    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping(path = "/get_cats")
    public @ResponseBody Iterable<Cat> getCats() {
        return catRepository.findAll();
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addCat(@RequestParam String name, @RequestPart(required = false) MultipartFile file) throws IOException {
        Cat cat = new Cat();
        cat.setName(name);
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            cat.setImagename(resultFilename);
        }

        catRepository.save(cat);
        return "saved";
    }

    @GetMapping(path = "/del")
    public @ResponseBody String deleteCat(@RequestParam Long id){
        Optional<Cat> cats = catRepository.findById(id);
        catRepository.delete(cats.get());
        return "delete success";
    }

    @GetMapping(path = "/get_cats_id")
    public @ResponseBody Optional<Cat> getCatsId(@RequestParam Long id){
        return catRepository.findById(id);
    }

    @GetMapping(path = "/update")
    public @ResponseBody String updateCat(@RequestHeader Long id, @RequestPart(required = false) String name,
                                          @RequestPart(required = false) String imagename){
        Optional<Cat> cat = catRepository.findById(id);
        Cat cat1 = cat.get();
        cat1.setName(name);
        cat1.setImagename(imagename);
        catRepository.save(cat1);
        return "Update success";
    }


}
