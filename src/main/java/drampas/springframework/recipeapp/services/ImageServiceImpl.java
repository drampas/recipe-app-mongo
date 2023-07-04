package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImage(String recipeId, MultipartFile file) {

        Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe=recipeOptional.get();
            try {
                Byte[] byteObject=new Byte[file.getBytes().length];
                int i=0;
                for(byte b:file.getBytes()){
                    byteObject[i]=b;
                    i++;
                }
                recipe.setImage(byteObject);
                recipeRepository.save(recipe);
            } catch (IOException e) {
                log.error("An error has occurred: ",e);
                e.printStackTrace();
            }
        }else {
            log.debug("Recipe not found");
        }

    }
}
