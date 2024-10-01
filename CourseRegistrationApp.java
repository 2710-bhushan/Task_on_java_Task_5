import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseRegistrationApp extends JFrame {
    private CourseDatabase courseDb;
    private StudentDatabase studentDb;
    private JTable courseTable;
    private JTextField studentIDField;
    private JTextField studentNameField;

    public CourseRegistrationApp() {
        courseDb = new CourseDatabase();
        studentDb = new StudentDatabase();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Course Registration System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Course List Section
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BorderLayout());
        String[] columnNames = {"Course Code", "Title", "Description", "Capacity", "Schedule"};
        courseTable = new JTable(getCourseData(), columnNames);
        JScrollPane courseScrollPane = new JScrollPane(courseTable);
        coursePanel.add(courseScrollPane, BorderLayout.CENTER);
        add(coursePanel, BorderLayout.CENTER);

        // Student Info and Course Registration Panel
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(3, 2));

        studentIDField = new JTextField();
        studentNameField = new JTextField();
        JButton registerButton = new JButton("Register for Course");
        JButton dropButton = new JButton("Drop Course");

        registrationPanel.add(new JLabel("Student ID:"));
        registrationPanel.add(studentIDField);
        registrationPanel.add(new JLabel("Student Name:"));
        registrationPanel.add(studentNameField);
        registrationPanel.add(registerButton);
        registrationPanel.add(dropButton);

        add(registrationPanel, BorderLayout.SOUTH);

        // Button Actions
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudentForCourse();
            }
        });

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCourseForStudent();
            }
        });
    }

    private void registerStudentForCourse() {
        String studentID = studentIDField.getText();
        String studentName = studentNameField.getText();
        int selectedRow = courseTable.getSelectedRow();

        if (studentID.isEmpty() || studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student details.");
            return;
        }

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course.");
            return;
        }

        // Fetch selected course
        String courseCode = (String) courseTable.getValueAt(selectedRow, 0);
        Course selectedCourse = getCourseByCode(courseCode);

        // Fetch or create student
        Student student = studentDb.getStudent(studentID);
        if (student == null) {
            student = new Student(studentID, studentName);
            studentDb.addStudent(student);
        }

        // Register course
        student.registerCourse(selectedCourse);
        selectedCourse.setCapacity(selectedCourse.getCapacity() - 1);

        JOptionPane.showMessageDialog(this, "Registered successfully!");
        refreshCourseTable();
    }

    private void dropCourseForStudent() {
        String studentID = studentIDField.getText();
        int selectedRow = courseTable.getSelectedRow();

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student ID.");
            return;
        }

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course.");
            return;
        }

        // Fetch selected course
        String courseCode = (String) courseTable.getValueAt(selectedRow, 0);
        Course selectedCourse = getCourseByCode(courseCode);

        // Fetch student
        Student student = studentDb.getStudent(studentID);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        // Drop course
        student.dropCourse(selectedCourse);
        selectedCourse.setCapacity(selectedCourse.getCapacity() + 1);

        JOptionPane.showMessageDialog(this, "Dropped successfully!");
        refreshCourseTable();
    }

    private Course getCourseByCode(String courseCode) {
        for (Course course : courseDb.getCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private Object[][] getCourseData() {
        Object[][] data = new Object[courseDb.getCourses().size()][5];
        int i = 0;
        for (Course course : courseDb.getCourses()) {
            data[i][0] = course.getCourseCode();
            data[i][1] = course.getTitle();
            data[i][2] = course.getDescription();
            data[i][3] = course.getCapacity();
            data[i][4] = course.getSchedule();
            i++;
        }
        return data;
    }

    private void refreshCourseTable() {
        courseTable.setModel(new javax.swing.table.DefaultTableModel(getCourseData(), new String[]{"Course Code", "Title", "Description", "Capacity", "Schedule"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseRegistrationApp app = new CourseRegistrationApp();
            app.setVisible(true);
        });
    }
}
