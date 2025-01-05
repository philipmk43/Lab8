package lab9;

import java.sql.*;
import java.util.Scanner;

public class lab9 {

    public void addAttendee(Connection con, String fullName, String email, String contactNumber, String country) throws SQLException {
        String query = "INSERT INTO attendees (full_name, email, contact_number, country) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, fullName);
        pstmt.setString(2, email);
        pstmt.setString(3, contactNumber);
        pstmt.setString(4, country);
        pstmt.executeUpdate();
        System.out.println("Attendee added successfully!");
    }

    public void editAttendee(Connection con, int attendeeId, String email, String contactNumber) throws SQLException {
        String query = "UPDATE attendees SET email = ?, contact_number = ? WHERE attendee_id = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, contactNumber);
        pstmt.setInt(3, attendeeId);
        pstmt.executeUpdate();
        System.out.println("Attendee information updated successfully!");
    }

    public void deleteAttendee(Connection con, int attendeeId) throws SQLException {
        String query = "DELETE FROM attendees WHERE attendee_id = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, attendeeId);
        pstmt.executeUpdate();
        System.out.println("Attendee deleted successfully!");
    }

    public void searchAttendee(Connection con, String searchKey, String searchValue) throws SQLException {
        String query = "SELECT * FROM attendees WHERE " + searchKey + " = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, searchValue);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("attendee_id") +
                    ", Name: " + rs.getString("full_name") +
                    ", Email: " + rs.getString("email") +
                    ", Contact: " + rs.getString("contact_number") +
                    ", Country: " + rs.getString("country"));
        }
    }

    public void generateStatistics(Connection con) throws SQLException {
        CallableStatement cstmt = con.prepareCall("{CALL get_attendee_statistics()}");
        ResultSet rs = cstmt.executeQuery();

        while (rs.next()) {
            System.out.println("Country: " + rs.getString("country") +
                    ", Total Attendees: " + rs.getInt("total_attendees"));
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab9", "root", "root");

            lab9 app = new lab9();
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- Conference Attendee Management ---");
                System.out.println("1. Add Attendee");
                System.out.println("2. Edit Attendee");
                System.out.println("3. Delete Attendee");
                System.out.println("4. Search Attendee");
                System.out.println("5. Generate Statistics");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Full Name: ");
                        String fullName = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Contact Number: ");
                        String contactNumber = scanner.nextLine();
                        System.out.print("Enter Country: ");
                        String country = scanner.nextLine();
                        app.addAttendee(con, fullName, email, contactNumber, country);
                        break;

                    case 2:
                        System.out.print("Enter Attendee ID to Edit: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter New Email: ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Enter New Contact Number: ");
                        String newContact = scanner.nextLine();
                        app.editAttendee(con, editId, newEmail, newContact);
                        break;

                    case 3:
                        System.out.print("Enter Attendee ID to Delete: ");
                        int deleteId = scanner.nextInt();
                        app.deleteAttendee(con, deleteId);
                        break;

                    case 4:
                        System.out.print("Enter Search Key (e.g., full_name, country): ");
                        String searchKey = scanner.nextLine();
                        System.out.print("Enter Search Value: ");
                        String searchValue = scanner.nextLine();
                        app.searchAttendee(con, searchKey, searchValue);
                        break;

                    case 5:
                        app.generateStatistics(con);
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 6);

            scanner.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
