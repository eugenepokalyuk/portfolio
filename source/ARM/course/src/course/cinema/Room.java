package course.cinema;


public final class Room implements IRoom {
    final int Id;
    final String Name;
    final String Description;
    final int Width;
    final int Height;


    public Room(int id, String name, String description, int width, int height) {
        Id = id;
        Name = name;
        Description = description;
        Width = width;
        Height = height;
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

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    public String toString() {
        return Name;
    }
}
