package com.database.sqlmanager;

import com.database.employee.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SQLObject {

    private Connection connection = null;
    private String databaseName = "employee_records";

    public void CreateStatement() {
        String query = "CREATE TABLE " + databaseName + " (EmployeeID int, Title VARCHAR (6), " +
                "FirstName VARCHAR (35), " + "MiddleInital VARCHAR (3), " + "LastName VARCHAR(35), " +
                "Gender VARCHAR (1), " + "Email (62), " + "DOB DATE, " + "DateOfJoining DATE, " + "Salary int )";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO ADD LOGGER?!?
        }
    }

    public void InsertStatement(Employee employee) {
        String query = "INSERT INTO " + databaseName + " (EmployeeID int, Title VARCHAR (6), FirstName VARCHAR (35)," +
                " MiddleInital VARCHAR (3), LastName VARCHAR(35), Gender VARCHAR (1), Email (62), DOB DATE," +
                " DateOfJoining DATE, Salary int )" +
                " VALUES (?, ?, ?,?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getTitle());
            statement.setString(3, employee.getFirstName());
            statement.setString(4, employee.getMiddleName());
            statement.setString(5, employee.getLastName());
            statement.setString(2, employee.getGender());
            statement.setString(2, employee.getEmail());
            statement.setString(2, employee.getDob());
            statement.setString(2, employee.getJoinDate());
            statement.setInt(2, employee.getSalary());
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO ADD LOGGER?!?
        }
    }

    public void establishConnection() {
        try {
            if (connection == null) {
                Properties properties = new Properties();
                properties.load(new FileReader("connection.properties"));
                String url = properties.getProperty("database_url");
                String userid = properties.getProperty("database_user");
                String password = properties.getProperty("database_password");
                connection = DriverManager.getConnection(url, userid, password);
            }
        } catch (IOException e) {
            // TODO ADD LOGGER?!?
            System.err.println("Could not load connection.properties");
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO ADD LOGGER?!?
            System.err.println("Could not establish connection, something wrong with: connection properties: dburl / dbuser / dbpassword");
            e.printStackTrace();
        }
    }
}