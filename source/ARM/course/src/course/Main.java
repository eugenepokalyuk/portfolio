package course;

import course.frames.*;
import course.cinema.Cinema;

import java.awt.*;


public class Main {
    private static Cinema cinema = new Cinema();
    private static Cinema cinema2 = new Cinema();
    private static Thread cinemaThread = new Thread(cinema);
    private static Thread cinemaThread2 = new Thread(cinema);

    public static void main(String[] args) {
        try {
            cinemaThread.run();
            Auth auth = new Auth(cinema);
            auth.setMinimumSize(new Dimension(350, 200));

            // wait for user login
            synchronized(auth) {
                auth.wait();
            }

            Cashier cashierFrame = new Cashier(cinema);
            cashierFrame.setVisible(true);
            cashierFrame.setTitle("Окно кассира кинотеатра");

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cinemaThread2.run();
            Auth auth = new Auth(cinema2);
            auth.setMinimumSize(new Dimension(350, 200));

            // wait for user login
            synchronized(auth) {
                auth.wait();
            }

            Cashier cashierFrame = new Cashier(cinema2);
            cashierFrame.setVisible(true);
            cashierFrame.setTitle("Окно кассира кинотеатра");

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//sudo service mysql start