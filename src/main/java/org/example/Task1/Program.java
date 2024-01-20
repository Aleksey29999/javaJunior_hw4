package org.example.Task1;

import org.example.Models.Curses;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

//Создайте базу данных (например, SchoolDB).
//        В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
//        Настройте Hibernate для работы с вашей базой данных.
//        Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
//        Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
//        Убедитесь, что каждая операция выполняется в отдельной транзакции.
public class Program {
    private final static Random random = new Random();
    public static void main(String[] args) {


        String url = "jdbc:mysql://localhost:3307/SchoolDB";
        String user = "root";
        String password = "password";
        try {
            // Подключение к базе данных
            Connection connection = DriverManager.getConnection(url, user, password);

            // Создание базы данных
            createDatabase(connection);
            System.out.println("Database created successfully");

            // Использование базы данных
            useDatabase(connection);
            System.out.println("Use database successfully");

            // Создание таблицы
            createTable(connection);
            System.out.println("Create table successfully");

            // Вставка данных
            int count = random.nextInt(5, 11);
            for (int i = 0; i < count; i++) {
                insertData(connection, Curses.create());
            }
            System.out.println("Insert data successfully");

            // Чтение данных
            Collection<Curses> curses = readData(connection);
            for (var curs : curses)
                System.out.println(curs);
            System.out.println("Read data successfully");

            // Обновление данных
            for (var curs : curses) {
                curs.updateTitle();
                curs.updateDuration();
                updateData(connection, curs);
            }
            System.out.println("Update data successfully");

            // Удаление данных
            /*for (var curs : curses)
                deleteData(connection, curs.getId());
            System.out.println("Delete data successfully");*/

            // Закрытие соединения
            connection.close();
            System.out.println("Database connection close successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //region Вспомогательные методы

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS SchoolDB;";
        PreparedStatement statement = connection.prepareStatement(createDatabaseSQL);
        statement.execute();
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL = "USE SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS curses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    /**
     * Добавление данных в таблицу curses
     *
     * @param connection Соединение с БД
     * @param curses    курс
     * @throws SQLException Исключение при выполнении запроса
     */
    private static void insertData(Connection connection, Curses curses) throws SQLException {
        String insertDataSQL = "INSERT INTO curses (title, duration) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, curses.getTitle());
            statement.setInt(2, curses.getDuration());
            statement.executeUpdate();
        }
    }

    /**
     * Чтение данных из таблицы curses
     *
     * @param connection Соединение с БД
     * @return Коллекция curses
     * @throws SQLException Исключение при выполнении запроса
     */
    private static Collection<Curses> readData(Connection connection) throws SQLException {
        ArrayList<Curses> cursesList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM curses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                cursesList.add(new Curses(id, title, duration));
            }
            return cursesList;
        }
    }

    /**
     * Обновление данных в таблице curses по идентификатору
     *
     * @param connection Соединение с БД
     * @param curses    курсы
     * @throws SQLException Исключение при выполнении запроса
     */
    private static void updateData(Connection connection, Curses curses) throws SQLException {
        String updateDataSQL = "UPDATE curses SET title=?, duration=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, curses.getTitle());
            statement.setInt(2, curses.getDuration());
            statement.setInt(3, curses.getId());
            statement.executeUpdate();
        }
    }

    /**
     * Удаление записи из таблицы students по идентификатору
     *
     * @param connection Соединение с БД
     * @param id         Идентификатор записи
     * @throws SQLException Исключение при выполнении запроса
     */
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM curses WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    //endregion

}

