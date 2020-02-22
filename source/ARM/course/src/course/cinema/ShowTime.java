package course.cinema;


public final class ShowTime implements IShowTime {
    final int Id;
    final int Hours;
    final int Minutes;
    final int Price;


    public ShowTime(int id, int hours, int minutes, int price) {
        Id = id;
        Hours = hours;
        Minutes = minutes;
        Price = price;
    }

    public int getId() {
        return Id;
    }

    public int getHours() {
        return Hours;
    }

    public int getMinutes() {
        return Minutes;
    }

    public int getPrice() {
        return Price;
    }

    public String toString() {
        return Hours + ":" + Minutes;
    }
}
