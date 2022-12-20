package com.yelpcamp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Campgrounds")
public class Campground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title must be less than 100 letters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be less than 500 letters")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    @Size(max = 200, message = "Location must be less than 200 letters")
    private String location;

    @NotNull(message = "Price cannot be empty")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @OneToMany(
        mappedBy = "campground",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public Campground() {
    }

    public Campground(String title, String description, String location, Double price) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
    }

    public Campground(String title, String description, String location, Double price, User author) {
        this(title, description, location, price);
        this.author = author;
    }

    public void addImage(Image image){
        images.add(image);
        image.setCampground(this);
    }

    public void removeImage(Image image){
        images.remove(image);
        image.setCampground(null);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Campground{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campground that = (Campground) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
