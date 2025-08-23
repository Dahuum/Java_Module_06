package fr.school42.repositories;

import fr.school42.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    
    private ProductsRepository repository;
    private DataSource dataSource;
    
    // expected: hint kayn f data.sql
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = List.of(
        new Product(1L, "Laptop", 999.99),
        new Product(2L, "Mouse", 25.50),
        new Product(3L, "Keyboard", 85.00),
        new Product(4L, "Monitor", 299.99),
        new Product(5L, "Headphones", 150.75)
    );
    
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Laptop", 999.99);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Gaming Laptop", 1299.99);
    
    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("schema.sql")
            .addScript("data.sql")
            .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }
    
    @Test
    void testFindAll() {
        List<Product> products = repository.findAll();
        assertEquals(5, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Mouse", products.get(1).getName());
        assertEquals(999.99, products.get(0).getPrice());
    }
    
    @Test
    void testFindByIdExists() {
        Optional<Product> product = repository.findById(1L);
        assertTrue(product.isPresent());
        assertEquals("Laptop", product.get().getName());
        assertEquals(999.99, product.get().getPrice());
        assertEquals(1L, product.get().getIdentifier());
    }
    
    @Test
    void testFindByIdNotExists() {
        Optional<Product> product = repository.findById(999L);
        assertFalse(product.isPresent());
    }
    
    @Test
    void testSave() {
        Product newProduct = new Product(6L, "Webcam", 79.99);
        repository.save(newProduct);
        
        Optional<Product> saved = repository.findById(6L);
        assertTrue(saved.isPresent());
        assertEquals("Webcam", saved.get().getName());
        assertEquals(79.99, saved.get().getPrice());
        
        List<Product> allProducts = repository.findAll();
        assertEquals(6, allProducts.size());
    }
    
    @Test
    void testUpdate() {
        Product updated = new Product(1L, "Gaming Laptop", 1299.99);
        repository.update(updated);
        
        Optional<Product> found = repository.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("Gaming Laptop", found.get().getName());
        assertEquals(1299.99, found.get().getPrice());
    }
    
    @Test
    void testDelete() {
        repository.delete(1L);
        
        // Verify it was deleted
        Optional<Product> deleted = repository.findById(1L);
        assertFalse(deleted.isPresent());
        
        // Verify total count decreased
        List<Product> remaining = repository.findAll();
        assertEquals(4, remaining.size());
    }
}
