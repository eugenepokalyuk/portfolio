package course.cinema;


public final class Movie implements IMovie {
    final int Id;
    final String Name;
    final String Description;
    final int Percent;


    public Movie(int id, String name, String description, int percent) {
        Id = id;
        Name = name;
        Description = description;
        Percent = percent;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public int getPercent() {
        return Percent;
    }

    public String toString() {
        return getName();
    }
}
