package com.yelpcamp.storage;

import com.yelpcamp.model.ImageDto;

import java.io.IOException;

public interface ImageStorage {

    ImageDto upload(Object file) throws IOException;

    void delete(String filename) throws IOException;
}
