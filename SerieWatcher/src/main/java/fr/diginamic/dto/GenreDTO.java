package fr.diginamic.dto;

public class GenreDTO {
    private int id;
    private String name;

    // Constructors
    public GenreDTO() {}

    public GenreDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
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
}
