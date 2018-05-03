package model;

/**
 * Created by Tohid on 2/9/2017 AD.
 */

public class Province {

    private String id,name;
    public Province(String id,String name){
        this.id = id;
        this.name = name;
    }

    public Province() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
