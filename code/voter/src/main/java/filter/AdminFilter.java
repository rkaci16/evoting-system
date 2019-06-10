package filter;

public class AdminFilter extends DefaultFilter {
    private Integer id;
    private String fullname;
    private String username;
    private String password;

    public AdminFilter() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public AdminFilter(boolean isFullBody) {
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
    public boolean hasNameFilter() {
        return !isNull(fullname);
    }
    public boolean hasUsernameFilter(){return !isNull(username);}
    public boolean hasPasswordFilter(){return !isNull(password);}

}
