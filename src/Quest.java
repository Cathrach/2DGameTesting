/**
 * Created by amy on 6/3/17.
 */
public class Quest {
    private String name;
    private String description;
    private int status;
    static final int NOT_STARTED = 0;
    static final int ONGOING = 1;
    static final int COMPLETED = 2;
    private String ongoingTrigger;
    private String completedTrigger;

    public Quest(String n, String d, int s, String t1, String t2) {
        name = n;
        description = d;
        status = s;
        ongoingTrigger = t1;
        completedTrigger = t2;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        String statusString = "";
        if (status == NOT_STARTED) { statusString = "Not started"; }
        else if (status == ONGOING) { statusString = "Ongoing"; }
        else if (status == COMPLETED) { statusString = "Completed"; }
        return statusString;
    }

    public void setStatus(int s) {
        status = s;
    }

    public String getOngoingTrigger() { return ongoingTrigger; }

    public String getCompletedTrigger() { return completedTrigger; }
}
