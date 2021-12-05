package test;

import main.java.ru.akirakozov.sd.refactoring.Main;
import org.junit.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// check minimal working server
public abstract class ServerTest {
    private final ExecutorService serverExecutor = Executors.newSingleThreadExecutor();


    @BeforeClass
    public void start() {
        serverExecutor.submit(() -> {
            try {
                Main.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @AfterClass
    public void stop() {
        serverExecutor.shutdown();
    }

    @AfterMethod
    public void cleanDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM PRODUCT");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}