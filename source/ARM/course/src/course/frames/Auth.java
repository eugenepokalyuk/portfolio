package course.frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import course.IAuth;


public class Auth extends JFrame{
    //127.0.0.1:3306/cinema
    JPanel ctrlPanel = new JPanel();
    JPanel ctrlPanelbtn = new JPanel();

    JLabel addHi = new JLabel();
    JLabel addLoginLbl = new JLabel();
    JTextField loginField = new JTextField ("arm");
    JLabel addPasswordLbl = new JLabel();
    JPasswordField passwordField = new JPasswordField ();

    JLabel addBaseLbl = new JLabel();
    JTextField localhostField = new JTextField ("127.0.0.1:3306/cinema");

    JLabel lMistake = new JLabel("Вы ввели неверные данные!");
    JLabel lAccept = new JLabel("Вы ввели верные данные!");

    JButton addEnterBtn = new JButton("Войти");
    JButton addExitBtn = new JButton("Выйти");

    public Auth(IAuth auth) {
        setTitle("Авторизация");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        ctrlPanel.setVisible(true);
        ctrlPanelbtn.setVisible(true);

        addCompanentsToPane(getContentPane());

        lMistake.setForeground(Color.decode("#FF0000"));
        lMistake.setVisible(false);
        lAccept.setForeground(Color.decode("#FF0000"));
        lAccept.setVisible(false);

        addHi.setAlignmentX(Component.CENTER_ALIGNMENT);
        addLoginLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPasswordLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBaseLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        localhostField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addEnterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addExitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        lAccept.setAlignmentX(Component.CENTER_ALIGNMENT);
        lMistake.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(ctrlPanel);
        ctrlPanel.add(loginField);
        ctrlPanel.add(passwordField);
        ctrlPanel.add(localhostField);
        ctrlPanel.add(lAccept);
        ctrlPanel.add(lMistake);

        add(ctrlPanelbtn);
        ctrlPanelbtn.add(addEnterBtn);
        ctrlPanelbtn.add(addExitBtn);

        loginField.setBorder(new TitledBorder("Введите имя пользователя:"));
        passwordField.setBorder(new TitledBorder("Введите пароль:"));
        localhostField.setBorder(new TitledBorder("Укажите путь к БД:"));

        ctrlPanel.setLayout(new GridLayout(0, 1));
        ctrlPanel.setBorder(new TitledBorder("Добро пожаловать в АРМ кассира кинотеатра"));

        ctrlPanelbtn.setLayout(new GridLayout(1, 1));

        ctrlPanel.add(ctrlPanelbtn, BorderLayout.LINE_START);

        addEnterBtn.addActionListener(e -> {
            try {
                // 127.0.0.1:3306/cinema
                auth.login("jdbc:mysql://" + localhostField.getText(), loginField.getText(), passwordField.getText());
                lMistake.setVisible(false);
                lAccept.setVisible(true);
                setVisible(false);

//              send user login message
                synchronized (this) {
                    notify();
                }
            }

            catch (Exception ex) {
                lMistake.setVisible(true);
                lAccept.setVisible(false);
            }
        });

        addExitBtn.addActionListener(e ->
            System.exit(0)
        );

        pack();
    }

    public static void addCompanentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    }
}