package course.components;

import course.cinema.IOrder;
import course.cinema.IRoom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Room extends JPanel {
    public interface RoomEvent {
        int getRow();
        int getColumn();
    }

    public interface RoomListener {
        void actionPerformed(RoomEvent e);
    }


    ArrayList<RoomListener> listeners = new ArrayList<>();

    JPanel seatsPanel = new JPanel();
    JButton movieButton = new JButton("Экран");
    JButton[][] buttons;

    public Room(IRoom room, IOrder[] orders) {
        setLayout(new BorderLayout());

        buttons = new JButton[room.getHeight() + 1][room.getWidth() + 1];

        seatsPanel.setLayout(new GridLayout(room.getHeight(), room.getWidth()));
        for (int y = 1; y <= room.getHeight(); ++y) {
            for (int x = 1; x <= room.getWidth(); ++x) {
                final int column = x, row = y;

                buttons[y][x] = new JButton(String.format("%d:%d", y, x));
                buttons[y][x].addActionListener(e -> {
                    for (RoomListener l : listeners) {
                        l.actionPerformed(new RoomEvent() {
                            final int Column = column;
                            final int Row = row;

                            public int getColumn() {
                                return Column;
                            }

                            public int getRow() {
                                return Row;
                            }
                        });
                    }
                });

                seatsPanel.add(buttons[y][x]);
            }
        }

        for (IOrder order : orders) {
            buttons[order.getRow()][order.getColumn()].setEnabled(false);
        }


        add(movieButton, BorderLayout.NORTH);
        add(seatsPanel, BorderLayout.CENTER);


        updateUI();
    }

    public void addActionListener(RoomListener l) {
        listeners.add(l);
    }
}
