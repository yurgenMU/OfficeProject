package init;

import DAO.UserDAO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class InitClass {

    public static void main(String[] args) {
        Properties properties = getProperties("src/main/resources/init_param.properties");
        String initPath = "src/main/resources/init.sql";
        String databaseName = properties.getProperty("databaseName");
        String url = properties.getProperty("url");
        String username = properties.getProperty("userName");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS "+ databaseName);
            statement.execute("USE " + databaseName);

            createInitialState(statement, initPath);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static Properties getProperties(String path){
        Properties properties = new Properties();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static void createInitialState(Statement statement, String initPath) throws SQLException {
        String createTableSQL = getInitQuieries(initPath);
        Arrays.stream(createTableSQL.split(";")).filter(x -> (!x.equals("\n"))).forEach(x -> {
            try {
                statement.execute(x);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }


    private static String getInitQuieries(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List lines = Files.readAllLines(Paths.get(path));
            lines.stream().forEach(line -> stringBuilder.append(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
