package fr.school42.repositories;

import fr.school42.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;      
import java.util.List;             
import java.util.Optional;         


public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);  // ← Database kadouz hnaya
    }
    
    public final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setIdentifier(rs.getLong("identifier"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        return product;
    };
    
    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", productRowMapper);
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        List<Product> products = jdbcTemplate.query(
            "SELECT * FROM product WHERE identifier = ?",
            productRowMapper, id);
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }
    
    @Override
    public void save(Product product) {
        jdbcTemplate.update(
            "INSERT INTO product (identifier, name, price) VALUES (?, ?, ?)",
            product.getIdentifier(), product.getName(), product.getPrice());
    }
    
    @Override
    public void update(Product product) {
        int rows = jdbcTemplate.update(
            "UPDATE product SET name = ?, price = ? WHERE identifier = ?",
            product.getName(), product.getPrice(), product.getIdentifier());
        if (rows == 0) throw new RuntimeException("Product not found");
    }

    @Override
    public void delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM product WHERE identifier = ?", id);
        if (rows == 0) throw new RuntimeException("Product not found");
    }

}