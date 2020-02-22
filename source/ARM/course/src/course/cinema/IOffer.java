package course.cinema;

public interface IOffer {
    int getId();
    IMovie getMovie();
    IShowTime getShowTime();
    IRoom getRoom();
}
