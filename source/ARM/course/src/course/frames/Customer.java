package course.frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


import course.cinema.IMovie;
import course.cinema.IOrder;
import course.cinema.IRoom;
import course.cinema.IShowTime;
import course.components.Room;


public class Customer extends JFrame {
    JPanel mainPanel = new JPanel();
    JPanel ctrlPanel = new JPanel();
    JPanel roomPanel = new JPanel();

    JLabel movtLabel = new JLabel();
    JTextArea descField = new JTextArea();

    JLabel roomLabel = new JLabel();
    JTextArea roomLabelName = new JTextArea();

    JLabel shwtLabel = new JLabel();
    JTextArea durtField = new JTextArea();

    Room roomBox;


    public Customer(IMovie movie, IRoom room, IShowTime showtime, IOrder[] orders) {
        setTitle("Окно посетителя кинотеатра");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 500));

        descField.setEditable(false);
        roomLabelName.setEditable(false);

        roomBox = new Room(room, orders);

        descField.setBorder(new TitledBorder(movie.getName()));
        descField.setText(movie.getDescription());

        roomLabelName.setBorder(new TitledBorder(room.getName()));
        roomLabelName.setText(room.getDescription());
        roomLabelName.setPreferredSize(new Dimension(300, 300));

        durtField.setBorder(new TitledBorder("Продолжительность фильма"));
        durtField.setPreferredSize(new Dimension(300, 200));

        ctrlPanel.setLayout(new GridLayout(0, 1));
        roomBox.setLayout(new GridLayout(0,1));
        ctrlPanel.setBorder(new TitledBorder("Пользователь"));
        roomBox.setBorder(new TitledBorder("Кинозал"));

        ctrlPanel.add(movtLabel);
        ctrlPanel.add(descField);
        descField.setLineWrap(true);

        ctrlPanel.add(roomLabel);
        ctrlPanel.add(roomLabelName);
        roomLabelName.setLineWrap(true);

        ctrlPanel.add(shwtLabel);
        ctrlPanel.add(durtField);
        durtField.setLineWrap(true);

        roomPanel.setLayout(new BorderLayout());
        roomPanel.setBorder(new TitledBorder(movie.getName()));

        roomPanel.setLayout(new BorderLayout());
        roomPanel.setBorder(new TitledBorder(room.getName()));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(ctrlPanel, BorderLayout.LINE_START);
        mainPanel.add(roomBox, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

    }
}