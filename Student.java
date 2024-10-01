import java.util.ArrayList;

public class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() { return studentID; }
    public String getName() { return name; }

    public void registerCourse(Course course) {
        if (course.hasSlot()) {
            registeredCourses.add(course);
            course.registerStudent();
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.deregisterStudent();
        }
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }
}
