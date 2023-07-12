package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

//    @Override
//    public Mono<Void> saveImage(String recipeId, MultipartFile file) {
//
//        Recipe recipe= recipeReactiveRepository.findById(recipeId).block();
//        if(recipe!=null){
//            try {
//                Byte[] byteObject=new Byte[file.getBytes().length];
//                int i=0;
//                for(byte b:file.getBytes()){
//                    byteObject[i]=b;
//                    i++;
//                }
//                recipe.setImage(byteObject);
//                recipeReactiveRepository.save(recipe).block();
//            } catch (IOException e) {
//                log.error("An error has occurred: ",e);
//                e.printStackTrace();
//            }
//        }else {
//            log.debug("Recipe not found");
//        }
//
//        return Mono.empty();
//    }
}
