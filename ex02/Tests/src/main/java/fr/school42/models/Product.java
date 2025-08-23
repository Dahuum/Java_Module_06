package fr.school42.models;

public class Product {
    
    private Long identifier;
    private String name;
    private Double price;

    
    public Product() {}
    
    public Product (Long identifier, String name, Double price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }
    
    public Long getIdentifier() { return identifier; }
    public void setIdentifier(Long identifier) { this.identifier = identifier; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

}