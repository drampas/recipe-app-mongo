package drampas.springframework.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImage(String recipeId, MultipartFile file);
}
