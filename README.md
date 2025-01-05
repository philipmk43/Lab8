# Conference Management System

## Description
This Java-based application uses JDBC to manage attendee information for an international conference organized by CHRIST (Deemed to be University). It provides a menu-driven interface to:

- Add new attendees.
- Edit existing attendee details.
- Delete attendee records.
- Search for attendees based on specific criteria (e.g., ID, full name, or country).
- Generate statistics of attendee counts by country using a stored procedure.

## Prerequisites
- MySQL Database
- Java Development Kit (JDK) 8 or higher
- MySQL Connector/J (JDBC Driver)

## Setup Instructions
1. Create a MySQL database named `lab9`.
2. Create a table `attendees` with the following structure:
   ```sql
   CREATE TABLE attendees (
       attendee_id INT AUTO_INCREMENT PRIMARY KEY,
       full_name VARCHAR(100),
       email VARCHAR(100),
       contact_number VARCHAR(15),
       country VARCHAR(50)
   );
   ```
3. Create a stored procedure `get_attendee_statistics`:
   ```sql
   DELIMITER //
   CREATE PROCEDURE get_attendee_statistics()
   BEGIN
       SELECT country, COUNT(*) AS total_attendees
       FROM attendees
       GROUP BY country;
   END //
   DELIMITER ;
   ```
4. Update the `Connection` URL, username, and password in the code to match your MySQL configuration.

## Menu Options
The program provides the following options:
1. Add Attendee
2. Edit Attendee
3. Delete Attendee
4. Search Attendee
5. Generate Statistics
6. Exit

## Troubleshooting
- Ensure the MySQL server is running.
- Verify the database, table, and stored procedure exist as specified.
- Check the JDBC driver compatibility with your JDK version.
- Ensure correct username and password in the `DriverManager.getConnection` method.

For further assistance, consult the application's code comments or MySQL and JDBC documentation.

