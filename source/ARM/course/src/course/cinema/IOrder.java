package course.cinema;

public interface IOrder {
    int getId();
    IMovie getMovie();
    IShowTime getShowtime();
    IRoom getRoom();
    IPrice getPrice();
    int getRow();
    int getColumn();
}
