public class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSchedule() { return schedule; }
    public int getCapacity() { return capacity; }
    public int getEnrolledStudents() { return enrolledStudents; }

    public boolean hasSlot() {
        return enrolledStudents < capacity;
    }

    public void registerStudent() {
        if(hasSlot()) {
            enrolledStudents++;
        }
    }

    public void deregisterStudent() {
        if(enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    @Override
    public String toString() {
        return code + ": " + title + " (" + enrolledStudents + "/" + capacity + ")";
    }
}
