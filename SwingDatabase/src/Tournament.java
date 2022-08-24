import java.sql.Date;

public class Tournament {
    private final int id;
    private final String name;
    private final Date startdate;
    private final Date enddate;

    Tournament(int id, String name, Date startdate, Date enddate) {
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartdate() {
        return startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    @Override
    public String toString() {
        return String
                .format("%s, Start %s",
                        name, startdate);
    }
}
