package filter;

public class CitizienFilter extends DefaultFilter {
    private Integer id;
    private String name;
    private String surname;

    public CitizienFilter() {
    }

    public CitizienFilter(boolean isFullBody) {
        super(isFullBody);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasIdFilter() {
        return !isNull(id);
    }

    public String getName() {
        return name;
    }

    public boolean hasNameFilter() {
        return !isNull(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean hasSurnameFilter() {
        return !isNull(surname);
    }
}
