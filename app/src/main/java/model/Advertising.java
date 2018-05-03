package model;

/**
 * Created by SherlookHohlmes on 4/1/2018.
 */

public class Advertising {

    int id, catid;
    String title, location, description, image_url, email, phone, city;

    public Advertising(int id, int catid, String title, String location, String description, String image_url, String email, String phone, String city) {
        this.id = id;
        this.catid = catid;
        this.title = title;
        this.location = location;
        this.description = description;
        this.image_url = image_url;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
