package pl.kamil.poznawajkamobile.models;

public class ProposedPersonModel {
    public Integer id;
    public Integer age;
    public String name;
    public String surname;
    public String icon;


    public ProposedPersonModel(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public ProposedPersonModel(int id, int age, String name, String surname, String icon) {
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
