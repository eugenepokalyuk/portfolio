package course.cinema;

public interface ICinema {
    IMovie[] getMovies() throws Exception;
    IOffer[] getOffers() throws Exception;
    IOrder[] getOrders() throws Exception;
    IOrder[] getOrders(IMovie movie, IShowTime showTime, IRoom room) throws Exception;
    void setOrder(IOrder order) throws Exception;
    IShowTime[] getShowtimes() throws Exception;
    IShowTime[] getShowtimes(IMovie movie) throws Exception;
    IRoom[] getRooms() throws Exception;
    IRoom[] getRooms(IShowTime time) throws Exception;
    IPrice[] getPrices() throws Exception;
}
