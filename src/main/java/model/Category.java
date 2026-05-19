package model;

public class Category {
    private int id;
    private String name;
    private String nameEn; 

    public Category() {
    }

    public Category(int id, String name, String nameEn) { 
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}