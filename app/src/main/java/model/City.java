package model;

/**
 * Created by SherlookHohlmes on 8/27/2017.
 */

public class City {

    private String id;
    private String name;
    private String province;

    public City(String id, String name, String province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    public City() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
