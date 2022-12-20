package com.yelpcamp.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.yelpcamp.model.Image;
import com.yelpcamp.model.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService implements ImageStorage {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ImageDto upload(Object file) throws IOException {
        var result = cloudinary
                .uploader()
                .upload(file, getUploadOptions());
        return getImageDto(result);
    }

    @Override
    public void delete(String filename) throws IOException {
        cloudinary.uploader().destroy(filename, ObjectUtils.emptyMap());
    }

    private static Map<String, String> getUploadOptions(){
        final String OPTION_DIR = "folder";
        final String DIR_NAME = "YelpCamp_Spring";
        Map<String, String> options = new HashMap<>();
        options.put(OPTION_DIR, DIR_NAME);
        return options;
    }

    private static ImageDto getImageDto(Map result) {
        var uploadResponse = (Map<String, String>)result;
        var url = uploadResponse.get("secure_url");
        var filename = uploadResponse.get("public_id");
        return new ImageDto(url, filename);
    }
}
