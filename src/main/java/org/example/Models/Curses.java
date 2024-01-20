package org.example.Models;


import javax.persistence.*;
import java.util.Random;

    @Entity
    @Table(name = "curses")
    public class Curses {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String title;
        private int duration;

        private static final String[] titles = new String[] { "Английский", "Математика", "Химия", "Физика", "История", "Черчение", "Физ-ра"};
        private static final Random random = new Random();

        public Curses(String title, int duration) {
            this.title = title;
            this.duration = duration;
        }

        public Curses(int id, String title, int duration) {
            this.id = id;
            this.title = title;
            this.duration = duration;
        }

        public Curses() {

        }

        public static Curses create(){
            return new Curses(titles[random.nextInt(titles.length)], random.nextInt(20, 26));
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public void updateDuration(){
            duration = random.nextInt(20, 26);
        }

        public void updateTitle(){
            title = titles[random.nextInt(titles.length)];
        }

        @Override
        public String toString() {
            return "Curses{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", duration=" + duration +
                    '}';
        }
    }




