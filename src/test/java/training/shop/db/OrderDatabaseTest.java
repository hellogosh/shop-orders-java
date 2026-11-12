package training.shop.db;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static training.shop.config.ConfigProvider.config;


@Tag("db")
public class OrderDatabaseTest {

    @Test
    void paidOrdersShouldBeReadFromDatabase() throws Exception {
        try (Connection connection = DriverManager.getConnection(config().dbUrl());
             Statement statement = connection.createStatement()) {


            statement.execute("create table orders (id varchar(20), amount int, status varchar(20))");


            statement.execute("insert into orders (id, amount, status) values ('ORD-1', 2500, 'PAID')");
            statement.execute("insert into orders (id, amount, status) values ('ORD-2', 18000, 'SHIPPED')");
            statement.execute("insert into orders (id, amount, status) values ('ORD-4', 32000, 'PAID')");


            try (ResultSet rs = statement.executeQuery("select count(*) as total from orders where status = 'PAID'")) {
                assertTrue(rs.next());
                assertEquals(2, rs.getInt("total"));
            }


            try (ResultSet rs = statement.executeQuery("select amount from orders where id = 'ORD-1'")) {
                assertTrue(rs.next(), "Строка с id = 'ORD-1' должна существовать");
                assertEquals(2500, rs.getInt("amount"));
            }
        }
    }
}