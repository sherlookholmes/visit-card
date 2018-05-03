package model;

/**
 * Created by SherlookHohlmes on 4/3/2018.
 */

public class Category {

    public String id, name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
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

    public static String getCatWithId(int id)
    {

        String category = null;

        switch (id)
        {

            case 0:
                category = "املاک";
                break;

            case 1:
                category = "وسایل نقلیه";
                break;

            case 2:
                category = "لوازم الکترونیکی";
                break;

            case 3:
                category = "مربوط به خانه";
                break;

            case 4:
                category = "خدمات";
                break;

            case 5:
                category = "وسایل شخصی";
                break;

            case 6:
                category = "سرگرمی و فراغت";
                break;

            case 7:
                category = "اجتماعی";
                break;

            case 8:
                category = "برای کسب و کار";
                break;

            case 9:
                category = "استخدام و کاریابی";
                break;

            default:
                category = "نامشخص";

        }

        return category;

    }

}
