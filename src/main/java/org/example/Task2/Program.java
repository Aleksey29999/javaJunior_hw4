package org.example.Task2;

import org.example.Models.Curses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Program {

    /**
     * Задача 2
     * ========
     * <p>
     *Настройте Hibernate для работы с вашей базой данных.
     * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
     * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
     * Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    public static void main(String[] args) {
        // Создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Curses.class)
                .buildSessionFactory();

        // Создание сессии
        try (Session session = sessionFactory.getCurrentSession()){

            // Начало транзакции
            session.beginTransaction();

            // Создание объекта
            Curses curses = Curses.create();

            // Сохранение объекта в базе данных
            session.save(curses);
            System.out.println("Object curses save successfully");

            // Чтение объекта из базы данных
            Curses retrievedStudent = session.get(Curses.class, curses.getId());
            System.out.println("Object curses retrieved successfully");
            System.out.println("Retrieved curses object: " + retrievedStudent);

            // Обновление объекта
            retrievedStudent.updateTitle();
            retrievedStudent.updateDuration();
            session.update(retrievedStudent);
            System.out.println("Object curses update successfully");

            // Удаление объекта
            //session.delete(retrievedStudent);
            //System.out.println("Object student delete successfully");

            // Коммит транзакции
            session.getTransaction().commit();
            System.out.println("Transaction commit successfully");

        }

    }

}
