package fr.school42.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    
    private DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.HSQL)
                    .addScript("schema.sql")
                    .addScript("data.sql")
                    .build();
        
    }
    
    @Test
    void testGetConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
        }
    }
	
}