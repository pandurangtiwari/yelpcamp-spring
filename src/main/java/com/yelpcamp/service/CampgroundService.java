package com.yelpcamp.service;

import com.yelpcamp.exception.ResourceNotFoundException;
import com.yelpcamp.model.Campground;
import com.yelpcamp.model.Image;
import com.yelpcamp.model.User;
import com.yelpcamp.model.YelpCampUser;
import com.yelpcamp.repository.CampgroundRepository;
import com.yelpcamp.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CampgroundService {

    @Autowired
    private CampgroundRepository campgroundRepository;

    @Autowired
    private ImageStorage imgStorageService;

    @Autowired
    private ReviewService reviewService;

    //Pagination to be added
    @Transactional
    public Set<Campground> getAllCampgrounds(){
        return campgroundRepository.getAllCampgrounds();
    }

    @Transactional
    public Campground addCampground(Campground campground, MultipartFile[] images,
                                    YelpCampUser loggedInUser) throws IOException {
        var author = new User(); author.setId(loggedInUser.getId());
        campground.setAuthor(author);
        addImagesToCampground(campground, images);
        return campgroundRepository.save(campground);
    }

    @Transactional
    public Campground getCampground(Long id){
        var campground = campgroundRepository.findById(id);
        if(campground.isPresent()) return campground.get();
        throw new ResourceNotFoundException("Campground Not Found");
    }

    @Transactional
    public Campground updateCampground(Campground editedCampground, MultipartFile[] images,
                                       List<String> deleteImages) throws IOException {
        var campground = getCampground(editedCampground.getId());
        editedCampground.setAuthor(campground.getAuthor());
        editedCampground.setImages(campground.getImages());
        deleteImagesFromCampground(editedCampground, deleteImages);
        addImagesToCampground(editedCampground, images);
        return campgroundRepository.save(editedCampground);
    }

    @Transactional
    public void deleteCampground(Long id) throws IOException {
        reviewService.deleteCampgroundReviews(id);
        var campground = getCampground(id);
        deleteImagesFromStorage(campground);
        campgroundRepository.deleteById(id);
    }

    private void addImagesToCampground(Campground campground, MultipartFile[] images) throws IOException {
        final int MAX_IMAGES = 3 - campground.getImages().size();
        int length = Math.min(images.length, MAX_IMAGES);
        if(emptyFilePresent(images)) length--;

        for(int i = 0; i < length; i++){
            var imageDto = imgStorageService.upload(images[i].getBytes());
            campground.addImage(new Image(imageDto));
        }
    }

    private static boolean emptyFilePresent(MultipartFile[] files) throws IOException {
        return files[0] != null && files[0].getBytes().length == 0;
    }

    private void deleteImagesFromStorage(Campground campground) throws IOException {
        for(var image : campground.getImages()){
            imgStorageService.delete(image.getFilename());
        }
    }

    private void deleteImagesFromCampground(Campground campground, List<String> filenames) throws IOException {
        if(filenames == null) return;
        campground.getImages()
                .stream()
                .filter(img -> filenames.contains(img.getFilename()))
                .collect(Collectors.toList())
                .forEach(campground::removeImage);
        deleteImagesFromStorage(filenames);
    }

    private void deleteImagesFromStorage(List<String> filenames) throws IOException {
        for(var filename : filenames){
            deleteImageFromStorage(filename);
        }
    }

    private void deleteImageFromStorage(String filename) throws IOException {
        imgStorageService.delete(filename);
    }
}
