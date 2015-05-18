package pl.kamil.poznawajkamobile.models;

public class MenuModel {
    public Integer id;
    public String name;
    public String icon;


    public MenuModel(int id, String name, String icon) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    }
    public MenuModel(int id, String name) {
    this.id = id;
    this.name = name;
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
}
