package poznawajkamobile.pz2.aplicationandroid.models;

public class FriendItemModel {
    public Integer id;
    public Integer age;
    public String name;
    public String surname;
    public String icon;
    public Float gps_x;
    public Float gps_y;

    public Float getGps_x() {
        return gps_x;
    }

    public void setGps_x(Float gps_x) {
        this.gps_x = gps_x;
    }

    public Float getGps_y() {
        return gps_y;
    }

    public void setGps_y(Float gps_y) {
        this.gps_y = gps_y;
    }

    public FriendItemModel(int id, int age, String name, String surname, String icon, Float gps_x, Float gps_y) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.surname = surname;
        this.icon = icon;
        this.gps_x = gps_x;
        this.gps_y = gps_y;
    }

    public FriendItemModel(int id, int age, String name, String surname, String icon) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.surname = surname;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
