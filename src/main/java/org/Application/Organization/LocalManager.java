package org.Application.Organization;

import org.Application.Clients.Parent;
import org.Application.Clients.Student;
import org.Application.Organization.Group;
import org.Application.Organization.Staff;
import org.Application.Organization.Teacher;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalManager {
    public List<Student> myStudents = new ArrayList<>();
    public List<Staff> myStaff = new ArrayList<>();
    public List<Parent> myParents = new ArrayList<>();
    public List<Teacher> myTeachers = new ArrayList<>();

    public List<Student> getStudents() {
        return myStudents;
    }

    public List<Staff> getStaff() {
        return myStaff;
    }

    public List<Parent> getParents() {
        return myParents;
    }

    public void addStudent(Student student) {
        myStudents.add(student);
    }

    public void addStaff(Staff staff) {
        myStaff.add(staff);
    }

    public void addParent(Parent parent) {
        myParents.add(parent);
    }


//    TODO: Display local list of students/parents/staff
    public void displayStudents() {
        for (Student student : myStudents) {
            System.out.println(student);
        }
    }

    public void displayParents() {
        for (Parent parent : myParents) {
            System.out.println(parent);
        }
    }

    public void displayStaff() {
        for (Staff staff : myStaff) {
            System.out.println(staff);
        }
    }

    public Student getStudentById(int studentID) {
        for (Student student : myStudents) {
            if (student.getId() == studentID) {
                return student;
            }
        }
        System.out.println("No student found with ID: " + studentID);
        return null;
    }

    public Parent getParentById(int parentID) {
        for (Parent parent : myParents) {
            if (parent.getId() == parentID) {
                return parent;
            }
        }
        System.out.println("No parent found with ID: " + parentID);
        return null;
    }

    public String getStaffById(int staffID) {
        for (Staff staff : myStaff) {
            if (staff.getId() == staffID) {
                return staff.toString();

            }
        }
        System.out.println(myStaff.size());
        System.out.println("No staff member found with ID: " + staffID);
        return null;
    }

    public Student getStudentByName(String firstName, String lastName) {
        String searchName = (firstName + " " + lastName).toLowerCase();
        for (Student student : myStudents) {
            String studentFullName = (student.getFirstName() + " " + student.getLastName()).toLowerCase();
            if (studentFullName.equals(searchName)) {
                return student;
            }
        }
        System.out.println("No student found with name: " + firstName + " " + lastName);
        return null;
    }

    public Parent getParentByName(String firstName, String lastName) {
        String searchName = (firstName + " " + lastName).toLowerCase();
        for (Parent parent : myParents) {
            String parentFullName = (parent.getFirstName() + " " + parent.getLastName()).toLowerCase();
            if (parentFullName.equals(searchName)) {
                return parent;
            }
        }
        System.out.println("No parent found with name: " + firstName + " " + lastName);
        return null;
    }

    public Staff getStaffByName(String firstName, String lastName) {
        String searchName = (firstName + " " + lastName).toLowerCase();
        for (Staff staff : myStaff) {
            String staffFullName = (staff.getFirstName() + " " + staff.getLastName()).toLowerCase();
            if (staffFullName.equals(searchName)) {
                return staff;
            }
        }
        System.out.println("No staff member found with name: " + firstName + " " + lastName);
        return null;
    }

    public void sendMessage(String messageContent) {
        System.out.println("Message: " + messageContent);
    }

//    TODO: edit student/parent/staff in local list
    public void editStudent(int studentID, String newFirstName, String newLastName) {
        for (Student student : myStudents) {
            if (student.getId() == studentID) {
                student.setFirstName(newFirstName);
                student.setLastName(newLastName);
                System.out.println("Student name updated successfully.");
                return;
            }
        }
        System.out.println("No student found with the given ID.");
    }

    public void editStudent(int studentID, int newAge) {
        for (Student student : myStudents) {
            if (student.getId() == studentID) {
                student.setAge(newAge);
                System.out.println("Student age updated successfully.");
                return;
            }
        }
        System.out.println("No student found with the given ID.");
    }

    public void editStudent(int studentID, Group newGroup) {
        for (Student student : myStudents) {
            if (String.valueOf(student.getId()).equalsIgnoreCase(String.valueOf(studentID))) {
                student.setGroup(newGroup);
                System.out.println("Student group updated successfully.");
                return;
            }
        }
        System.out.println("No student found with the given ID.");
    }

    public void editStudent(int studentID, String newFirstName, String newLastName, int newAge, Group newGroup) {
        for (Student student : myStudents) {
            if (student.getId() == studentID) {
                if (newFirstName != null && newLastName != null) {
                    student.setFirstName(newFirstName);
                    student.setLastName(newLastName);
                }
                if (newAge > 0) {
                    student.setAge(newAge);
                }
                if (newGroup != null) {
                    student.setGroup(newGroup);
                }
                System.out.println("Student information updated successfully.");
                return;
            }
        }
        System.out.println("No student found with the given ID.");
    }

    public abstract void generateStudentReport(String filePath);

    public abstract void generateParentReport(String filePath);
}
