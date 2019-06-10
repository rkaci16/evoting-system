package filter;

public class DefaultFilter {
    private boolean isFullBody = false; // select all entries of table

    public DefaultFilter() {
    }

    public DefaultFilter(boolean isFullBody) {
        this.isFullBody = isFullBody;
    }

    public String getNotNullString(String s) {
        return s == null ? "" : s;
    }

    public boolean isNull(Object o) {
        return o == null;
    }

    public boolean isFullBody() {
        return isFullBody;
    }

    public void setFullBody(boolean fullBody) {
        isFullBody = fullBody;
    }
}
