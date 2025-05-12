package com.zosh.dto;

public class CategoryDTO {

    private Long id;
    private String name;
    private int level;
    private Long parentCategoryId;

    // Constructors, Getters, Setters
    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, int level, Long parentCategoryId) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parentCategoryId = parentCategoryId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public Long getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(Long parentCategoryId) { this.parentCategoryId = parentCategoryId; }
}
