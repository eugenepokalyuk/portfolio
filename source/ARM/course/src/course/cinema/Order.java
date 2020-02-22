package course.cinema;


public final class Order implements IOrder {
    final int Id;
    final IMovie Movie;
    final IShowTime Showtime;
    final IRoom Room;
    final IPrice Price;
    final int Row;
    final int Column;


    public Order(int id, IMovie movie, IShowTime showtime, IRoom room, IPrice price, int row, int column) {
        Id = id;
        Movie = movie;
        Showtime = showtime;
        Room = room;
        Price = price;
        Row = row;
        Column = column;
    }

    public int getId() {
        return Id;
    }

    public IMovie getMovie() {
        return Movie;
    }

    public IShowTime getShowtime() {
        return Showtime;
    }

    public IRoom getRoom() {
        return Room;
    }

    public IPrice getPrice() {
        return Price;
    }

    public int getRow() {
        return Row;
    }

    public int getColumn() {
        return Column;
    }
}
