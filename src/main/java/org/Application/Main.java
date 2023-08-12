package org.Application;
import org.Application.Administrator.Admin;
import org.Application.Clients.Parent;
import org.Application.Clients.Student;
import org.Application.Organization.Group;
import org.Application.Organization.RoleType;
import org.Application.Organization.Staff;
import org.Application.Organization.Teacher;
import java.util.List;
import java.util.Scanner;
import static org.Application.Organization.RoleType.*;


public class Main {
    static Admin Director = new Admin();
//        TODO: Load data from database

    static List<Parent> parentsList = Director.loadParentsFromDatabase();
    static List<Staff> staffList = Director.loadStaffFromDatabase();
    static List<Teacher> teachersList = Director.loadTeacherFromDatabase();
    static List<Student> studentsList = Director.loadStudentsFromDatabase();

    public static void main(String[] args) {

//        TODO: Generate database using the database.sql file in resources

//        TODO: Un-comment the following lines to create new objects in database

//        Staff alinaStaff = new Staff("Alina", "Rota", TEACHER, Group.SUPERHEROES, "Summer School", "0734567223");
//        Director.addStaffToDatabase(alinaStaff);
//
//        Parent robertParent = new Parent("Robert" ,"Dan", "1800405160348", "0734567776");
//        Director.addParentToDatabase(robertParent);
//
//        Student selenaStudent = new Student("Selena", "Dan", 6, Group.SUPERHEROES, robertParent);
//        Director.addStudentToDatabase(selenaStudent);

//        TODO: Members (parents, teachers, other staff) are provided their personal ID when registering in the organization
//              Some valid IDs are for example: T68, S70, P33, P35, and 1 for admin

//        TODO: Interact with user

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome!");

        System.out.println("Enter your ID: ");
        String enteredId = scanner.nextLine();

        String prefix = enteredId.substring(0, 1);
        String numericId = enteredId.substring(1);


        String parentUniqueId = "P" + numericId;
        String staffUniqueId = "S" + numericId;
        String teacherUniqueId = "T" + numericId;


        Parent authenticatedParent = null;
        Staff authenticatedStaff = null;
        Teacher authenticatedTeacher = null;

        RoleType role = null;


        switch (prefix) {
            case "P":
                authenticatedParent = parentsList.stream()
                        .filter(parent -> parent.getUniqueId().equals(parentUniqueId))
                        .findFirst()
                        .orElse(null);
                role = RoleType.PARENT;
                break;
            case "S":
                authenticatedStaff = staffList.stream()
                        .filter(staff -> staff.getUniqueId().equals(staffUniqueId))
                        .findFirst()
                        .orElse(null);
                role = RoleType.OTHER;
                break;
            case "T":
                authenticatedTeacher = teachersList.stream()
                        .filter(teacher -> teacher.getUniqueId().equals(teacherUniqueId))
                        .findFirst()
                        .orElse(null);
                role = RoleType.TEACHER;
                break;
            case "1":
                // Admin authentication id is "1"
                role = RoleType.ADMIN;
                break;
            default:
                System.out.println("Invalid id, please try again.");
                break;
        }

        if (role != null) {

            switch (role) {
                case PARENT:
                    if (authenticatedParent != null) {
                        handleParentActions(authenticatedParent, scanner);
                    }
                case TEACHER:
                    if (authenticatedTeacher != null) {
                        handleTeacherActions(authenticatedTeacher, scanner);
                    }
                    break;
                case ADMIN:
                    Admin admin = new Admin();
                    handleAdminActions(admin, scanner);
                    break;
                case OTHER:
                    if (authenticatedStaff != null) {
                        handleStaffActions(authenticatedStaff, scanner);
                    }
                    break;
                default:
                    System.out.println("Invalid role, please try again.");
            }
        }
    }

    public static void handleParentActions(Parent parent, Scanner scanner) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Choose one of the following:");
            System.out.println("1. Submit feedback");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please write feedback below: ");
                    parent.submitFeedback(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Exiting menu.");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (keepRunning) {
                System.out.println("What would you like to do next?");
                System.out.println("1. Return to menu");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int returnOrExit = scanner.nextInt();
                scanner.nextLine();

                switch (returnOrExit) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("Exiting menu.");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }

    public static void handleTeacherActions(Teacher teacher, Scanner scanner) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Choose one of the following:");
            System.out.println("1. Generate Parent Report");
            System.out.println("2. Generate Student Report");
            System.out.println("3. Get assigned group");
            System.out.println("4. Set assigned group");
            System.out.println("5. Submit Feedback");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    teacher.generateParentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\parents.txt");
                    break;
                case 2:
                    teacher.generateStudentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\students.txt");
                    break;
                case 3:
                    Group assignedGroup = Group.valueOf(teacher.getCorrespondingGroup().getName().toUpperCase());
                    System.out.println(assignedGroup);
                    break;
                case 4:
                    int newAssignedGroup = scanner.nextInt();
                    System.out.println("Please enter id of new group: ");
                    System.out.println("Newly assigned group id is:" + newAssignedGroup);
                    break;
                case 5:
                    System.out.println("Please write feedback below: ");
                    teacher.submitFeedback(scanner.nextLine());
                    break;
                case 6:
                    System.out.println("Exiting menu.");
                    keepRunning = false;
                    break;


                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (keepRunning) {
                System.out.println("What would you like to do next?");
                System.out.println("1. Return to menu");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int returnOrExit = scanner.nextInt();
                scanner.nextLine();

                switch (returnOrExit) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("Exiting menu.");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }

    public static void handleAdminActions(Admin admin, Scanner scanner) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Choose one of the following:");
            System.out.println("1. Generate Parent Report");
            System.out.println("2. Generate Student Report");
            System.out.println("3. Generate Feedback Report");
            System.out.println("4. Display list of students");
            System.out.println("5. Display list of parents");
            System.out.println("6. Display list of staff");
            System.out.println("7. Remove staff from database");
            System.out.println("8. Remove student from database");
            System.out.println("9. Remove parent from database");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.generateParentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\parents.txt");
                    break;
                case 2:
                    admin.generateStudentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\students.txt");
                    break;
                case 3:
                    admin.generateFeedbackReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\feedback.txt");
                    break;
                case 4:
                    System.out.println(studentsList);
                    break;
                case 5:
                    System.out.println(parentsList);
                    break;
                case 6:
                    admin.displayStaff();
                    System.out.println(staffList);
                    System.out.println(staffList.size());
                    break;
                case 7:
                    System.out.println("Please enter staff id to remove:");
                    admin.removeStaffFromDatabase(scanner.nextInt());
                    break;
                case 8:
                    System.out.println("Please enter student id to remove:");
                    admin.removeStudentFromDatabase(scanner.nextInt());
                    break;
                case 9:
                    System.out.println("Please enter parent id to remove:");
                    admin.removeParentFromDatabase(scanner.nextInt());
                    break;
                case 10:
                    System.out.println("Exiting menu.");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (keepRunning) {
                System.out.println("What would you like to do next?");
                System.out.println("1. Return to menu");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int returnOrExit = scanner.nextInt();
                scanner.nextLine();

                switch (returnOrExit) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("Exiting menu.");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }


    public static void handleStaffActions(Staff staff, Scanner scanner) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Choose one of the following:");
            System.out.println("1. Generate parent report");
            System.out.println("2. Generate student report");
            System.out.println("3. Submit feedback");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    staff.generateParentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\parents.txt");
                    break;
                case 2:
                    staff.generateStudentReport("C:\\Users\\Theodora Prutean\\Desktop\\Reports\\students.txt");
                    break;
                case 3:
                    System.out.println("Please write feedback below: ");
                    staff.submitFeedback(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Exiting menu.");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (keepRunning) {
                System.out.println("What would you like to do next?");
                System.out.println("1. Return to menu");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int returnOrExit = scanner.nextInt();
                scanner.nextLine();

                switch (returnOrExit) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("Exiting menu.");
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }
}

