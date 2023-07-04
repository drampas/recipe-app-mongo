package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.services.ImageService;
import drampas.springframework.recipeapp.services.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{recipeId}/image")
    public String getImageUploadForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(recipeId));
        return "/recipe/imageuploadform";
    }
    @PostMapping("/recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId,@RequestParam("imagefile") MultipartFile file){
        imageService.saveImage(recipeId,file);
        return "redirect:/recipe/show/"+recipeId;
    }
    @GetMapping("/recipe/{recipeId}/recipeimage")
    public void renderImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand=recipeService.findCommandById(recipeId);
        if(recipeCommand.getImage()!=null){
            byte[] image=new byte[recipeCommand.getImage().length];
            int i=0;
            for(Byte b:recipeCommand.getImage()){
                image[i] = b;
                i++;
            }
            response.setContentType("image/jpg");
            InputStream inputStream=new ByteArrayInputStream(image);
            IOUtils.copy(inputStream,response.getOutputStream());
        }
    }
}
