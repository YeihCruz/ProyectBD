package models;

public class Gender {

    private int genderId;
    private String description;


    public Gender(int genderId,
                  String description) {

        this.genderId = genderId;
        this.description = description;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
