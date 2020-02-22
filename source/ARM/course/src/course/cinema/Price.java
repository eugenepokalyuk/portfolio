package course.cinema;

public class Price implements IPrice {
    final int Id;
    final String Name;
    final int Percent;


    public Price(int id, String name, int percent) {
        Id = id;
        Name = name;
        Percent = percent;
    }


    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getPercent(){
        return Percent;
    }

    public String toString(){
        return Name;
    }
}
