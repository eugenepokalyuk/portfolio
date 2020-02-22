package course.cinema;

import java.sql.*;
import java.util.ArrayList;
import java.util.function.Function;

import course.*;

import static course.Checked.*;


public class Cinema implements Runnable, ICinema, IAuth {
    final String movies_query    = "SELECT * FROM movies";
    final String offers_query    = "SELECT * FROM offers";
    final String orders_query    = "SELECT * FROM orders";
    final String prices_query    = "SELECT * FROM prices";
    final String rooms_query     = "SELECT * FROM rooms";
    final String showtimes_query = "SELECT * FROM showtimes";

    String Url;
    String User;
    String Password;


    // Runnable
    public void run() {
    }

    <T> ArrayList<T> performQuery(String sql, Function<ResultSet, T> create) throws SQLException { //Подключение БД
        ArrayList<T> values = new ArrayList<>();

        try(var connection = DriverManager.getConnection(Url, User, Password)) {
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                values.add(create.apply(rs));
            }
        }

        return values;
    }


    public IMovie[] getMovies() throws SQLException {
        return performQuery(
            movies_query,
            Checked((ResultSet rs) ->
                new Movie(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("percent")
                )
            )
        ).toArray(IMovie[]::new);
    }

    public IOffer[] getOffers() throws SQLException {
        return performQuery(
            offers_query,
            Checked((ResultSet rs) ->
                new Offer(
                    rs.getInt("id"),
                    getMovie(rs.getInt("movie_id")),
                    getShowTime(rs.getInt("showtime_id")),
                    getRoom(rs.getInt("room_id"))
                )
            )
        ).toArray(IOffer[]::new);
    }

    public IOrder[] getOrders() throws SQLException {
        return performQuery(orders_query,
            Checked((ResultSet rs) ->
                new Order(
                    rs.getInt("id"),
                    getMovie(rs.getInt("movie_id")),
                    getShowTime(rs.getInt("showtime_id")),
                    getRoom(rs.getInt("room_id")),
                    getPrice(rs.getInt("price_id")),
                    rs.getInt("row"),
                    rs.getInt("column")
                )
            )
        ).toArray(IOrder[]::new);
    }

    public IOrder[] getOrders(IMovie movie, IShowTime showTime, IRoom room) throws SQLException {
        if(movie == null || showTime == null || room == null)
            return new IOrder[0];

        return performQuery(
            String.format("%s WHERE movie_id = %d AND showtime_id = %d AND room_id = %d", orders_query, movie.getId(), showTime.getId(), room.getId()),
            Checked((ResultSet rs) ->
                new Order(
                    rs.getInt("id"),
                    getMovie(rs.getInt("movie_id")),
                    getShowTime(rs.getInt("showtime_id")),
                    getRoom(rs.getInt("room_id")),
                    getPrice(rs.getInt("price_id")),
                    rs.getInt("row"),
                    rs.getInt("column")
                )
            )
        ).toArray(IOrder[]::new);
    }

    public void setOrder(IOrder order) throws SQLException {
        try(var connection = DriverManager.getConnection(Url, User, Password)) {
            ResultSet rs = connection.createStatement().executeQuery("SELECT MAX(id) FROM orders"); //Подключение БД
            rs.next();

            int max_id = rs.getInt("MAX(id)");

            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (`id`, `movie_id`, `showtime_id`, `room_id`, `price_id`, `row`, `column`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, max_id + 1);
            ps.setInt(2, order.getMovie().getId());
            ps.setInt(3, order.getShowtime().getId());
            ps.setInt(4, order.getRoom().getId());
            ps.setInt(5, order.getPrice().getId());
            ps.setInt(6, order.getRow());
            ps.setInt(7, order.getColumn());

            ps.execute();
        }
    }

    public IPrice[] getPrices() throws SQLException {
        return performQuery(
            prices_query,
            Checked((ResultSet rs) ->
                new Price(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("percent")
                )
            )
        ).toArray(IPrice[]::new);
    }

    public IRoom[] getRooms() throws SQLException {
        return performQuery(
            rooms_query,
            Checked((ResultSet rs) ->
                new Room(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("width"),
                    rs.getInt("height")
                )
            )
        ).toArray(IRoom[]::new);
    }

    public IRoom[] getRooms(IShowTime time) throws SQLException {
        if(time == null)
            return new IRoom[0];

        return performQuery(
            String.format("%s WHERE id IN (SELECT room_id FROM offers WHERE showtime_id = %d)", rooms_query, time.getId()),
            Checked((ResultSet rs) ->
                new Room(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("width"),
                    rs.getInt("height")
                )
            )
        ).toArray(IRoom[]::new);
    }

    public IShowTime[] getShowtimes() throws SQLException {
        return performQuery(
            showtimes_query,
            Checked((ResultSet rs) ->
                new ShowTime(
                    rs.getInt("id"),
                    rs.getInt("hours"),
                    rs.getInt("minutes"),
                    rs.getInt("percent")
                )
            )
        ).toArray(IShowTime[]::new);
    }

    public IShowTime[] getShowtimes(IMovie movie) throws SQLException {
        if(movie == null)
            return new IShowTime[0];

        return performQuery(
            String.format("%s WHERE id IN (SELECT showtime_id FROM offers WHERE movie_id = %d) ORDER BY hours", showtimes_query, movie.getId()),
            Checked((ResultSet rs) ->
                new ShowTime(
                    rs.getInt("id"),
                    rs.getInt("hours"),
                    rs.getInt("minutes"),
                    rs.getInt("price")
                )
            )
        ).toArray(IShowTime[]::new);
    }

    // IAuth
    public void login(String url, String user, String password) throws Exception {
        var connection = DriverManager.getConnection(url, user, password); //БД
        Url = url;
        User = user;
        Password = password;
        connection.close();
    }

    public void logout() throws Exception {
    }


    IMovie getMovie(int id) throws SQLException {
        return performQuery(
            String.format("%s WHERE id = %d", movies_query, id),
            Checked((ResultSet rs) ->
                new Movie(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("percent")
                )
            )
        ).toArray(IMovie[]::new)[0];
    }

    IPrice getPrice(int id) throws SQLException {
        return performQuery(
            String.format("%s WHERE id = %d", prices_query, id),
            Checked((ResultSet rs) ->
                new Price(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("percent")
                )
            )
        ).toArray(IPrice[]::new)[0];
    }

    IRoom getRoom(int id) throws SQLException {
        return performQuery(
            String.format("%s WHERE id = %d", rooms_query, id),
            Checked((ResultSet rs) ->
                new Room(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("width"),
                    rs.getInt("height")
                )
            )
        ).toArray(IRoom[]::new)[0];
    }

    IShowTime getShowTime(int id) throws SQLException {
        return performQuery(
            String.format("%s WHERE id = %d", showtimes_query, id),
            Checked((ResultSet rs) ->
                new ShowTime(
                    rs.getInt("id"),
                    rs.getInt("hours"),
                    rs.getInt("minutes"),
                    rs.getInt("price")
                )
            )
        ).toArray(IShowTime[]::new)[0];
    }
}
