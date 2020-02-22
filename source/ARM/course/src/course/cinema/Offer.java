package course.cinema;


public final class Offer implements IOffer {
    final int Id;
    final IMovie Movie;
    final IShowTime ShowTime;
    final IRoom Room;


    public Offer(int id, IMovie movie, IShowTime showtime, IRoom room) {
        Id = id;
        Movie = movie;
        ShowTime = showtime;
        Room = room;
    }

    public int getId() {
        return Id;
    }

    public IMovie getMovie() {
        return Movie;
    }

    public IShowTime getShowTime() {
        return ShowTime;
    }

    public IRoom getRoom() {
        return Room;
    }
}
