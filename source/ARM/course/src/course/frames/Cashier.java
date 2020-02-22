package course.frames;

import java.io.File;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import course.cinema.*;
import course.cinema.ICinema;
import course.cinema.Cinema;

import course.components.Room;

import static course.Checked.*;

public class Cashier extends JFrame {
    private static Cinema cinema = new Cinema();
    private static Thread cinemaThread = new Thread(cinema);

    JPanel mainPanel = new JPanel();
    JPanel ctrlPanel = new JPanel();
    JPanel roomPanel = new JPanel();

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Файл");
    JMenu helpMenu = new JMenu("Помощь");
    JMenuItem aboutMenu = new JMenuItem("О программе");
    JMenuItem newMenu = new JMenuItem("Открыть АРМ");

    JMenuItem helpItem = new JMenuItem("Как написать АРМ");
    JMenuItem LearnItem = new JMenuItem("Изучения Java");
    JMenuItem instItem = new JMenuItem("Инструкция по использовнию");
    JMenuItem exitItem = new JMenuItem("Выход");

    JButton btnCurrentMovies = new JButton("Обновить список фильмов");

    JList movieList = new JList();
    JList priceList = new JList();
    JList roomsList = new JList();
    JList sesssList = new JList();

    JScrollPane movieScroll = new JScrollPane(movieList);
    JScrollPane priceScroll = new JScrollPane(priceList);
    JScrollPane roomsScroll = new JScrollPane(roomsList);
    JScrollPane sesssScroll = new JScrollPane(sesssList);

    JButton seatButton = new JButton("Выбрать место");
    JButton viewroomButton = new JButton("Просмотреть зал");
    JButton exitButton = new JButton("Завершить сеанс");

    Room roomBox;

    public Cashier(ICinema cinema) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 500));

        seatButton.setEnabled(false);
        viewroomButton.setEnabled(false);

        movieList.setBorder(new TitledBorder("Выбор сеанса"));
        sesssList.setBorder(new TitledBorder("Выбор фильма"));
        roomsList.setBorder(new TitledBorder("Выбор кинозала"));
        priceList.setBorder(new TitledBorder("Выбор типа билета"));

        fileMenu.add(newMenu);
        fileMenu.add(instItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        helpMenu.add(LearnItem);
        helpMenu.add(helpItem);

        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        ctrlPanel.setLayout(new GridLayout(0, 1));

        ctrlPanel.setBorder(new TitledBorder("АРМ кассира кинотеатра"));
        ctrlPanel.add(btnCurrentMovies);
        ctrlPanel.add(movieScroll);
        ctrlPanel.add(sesssScroll);
        ctrlPanel.add(roomsScroll);
        ctrlPanel.add(priceScroll);
        ctrlPanel.add(seatButton);
        ctrlPanel.add(viewroomButton);
        ctrlPanel.add(exitButton);

        roomPanel.setLayout(new BorderLayout());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(ctrlPanel, BorderLayout.LINE_START);
        mainPanel.add(roomPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        try {
            priceList.setListData(cinema.getPrices());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        helpItem.addActionListener(CheckedActionListener(e ->
            Desktop.getDesktop().open(new File("out/production/course/course/assets/Metod.pdf"))
        ));

        instItem.addActionListener(CheckedActionListener(e ->
            Desktop.getDesktop().open(new File("out/production/course/course/assets/Instruction.pdf"))
        ));

        LearnItem.addActionListener(CheckedActionListener(e ->
            Desktop.getDesktop().open(new File("out/production/course/course/assets/Learn.pdf"))
        ));

        aboutMenu.addActionListener(CheckedActionListener(e ->
                JOptionPane.showOptionDialog(this,
                        "<html>"
                                + "<h1>АРМ кассира кинотеатра</h1>"
                                + "<p>Программа разработана студентом НГТУ, группы АВТ-710</p>"
                                + "<p>Покалюком Евгением Александровичем</p>"
                                + "<hr>"
                                + "<p>Обратная связь: eugene.pokalyuk@gmail.com</p>"
                                + "</html>",
                        "О программе",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon("out/production/course/course/assets/me.jpg"),
                        new String[] { "Oк"},
                        "Oк"
                ))
        );

        exitItem.addActionListener(e ->
            System.exit(0)
        );

        exitButton.addActionListener(e ->
            System.exit(0)
        );

        btnCurrentMovies.addActionListener(CheckedActionListener(e -> {
            movieList.setListData(cinema.getMovies());
            movieList.updateUI();
        }));


        movieList.addListSelectionListener(CheckedListSelectionListener(e -> {
            sesssList.setListData(cinema.getShowtimes((IMovie) movieList.getSelectedValue()));
            sesssList.updateUI();
        }));

        sesssList.addListSelectionListener(CheckedListSelectionListener(e -> {
            roomsList.setListData(cinema.getRooms((IShowTime) sesssList.getSelectedValue()));
            roomsList.updateUI();
        }));

        roomsList.addListSelectionListener(CheckedListSelectionListener(e ->{
            viewroomButton.setEnabled(true);
        }));

        priceList.addListSelectionListener(e -> {
            if (!movieList.isSelectionEmpty() && !sesssList.isSelectionEmpty() && !roomsList.isSelectionEmpty())
            seatButton.setEnabled(true);
        });

        viewroomButton.addActionListener(CheckedActionListener( ex -> {
            IMovie movie = (IMovie) movieList.getSelectedValue();
            IShowTime showtime = (IShowTime) sesssList.getSelectedValue();
            IRoom room = (IRoom) roomsList.getSelectedValue();
            IOrder[] orders = cinema.getOrders(movie, showtime, room);

            new Customer(movie, room, showtime, orders).setVisible(true);
        }));

        seatButton.addActionListener(CheckedActionListener(e -> {
            IMovie movie = (IMovie) movieList.getSelectedValue();
            IShowTime showtime = (IShowTime) sesssList.getSelectedValue();
            IRoom room = (IRoom) roomsList.getSelectedValue();
            IOrder[] orders = cinema.getOrders(movie, showtime, room);
            IPrice price = (IPrice) priceList.getSelectedValue();

            roomBox = new Room(room, orders);
            roomBox.addActionListener(l -> {
                try {
                    cinema.setOrder(new Order(0, movie, showtime, room, price, l.getRow(), l.getColumn()));
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            roomPanel.removeAll();
            roomPanel.add(roomBox, BorderLayout.CENTER);
            roomPanel.setBorder(new TitledBorder(movie.getName()));

            mainPanel.updateUI();

        }));

    }

    public static void main(String[] args) {

    }
}