package hospital_management;

import java.sql.*;
import java.util.Scanner;

public class Hospital_ms {
    private static final String url = "<db-server-url>";
    private static final String username = "<your-username>";
    private static final String password = "<your-password>";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1. add patient");
                System.out.println("2. view patients");
                System.out.println("3. view doctors");
                System.out.println("4. book appointment");
                System.out.println("5. exit");

                System.out.print("\n enter your choice: ");
                int choice = scanner.nextInt();
                switch(choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;

                    default:
                        System.out.println("\nenter a valid choice\n");
                        System.out.println();
                        break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.print("\nenter patient_id: ");
        int patientId = scanner.nextInt();
        System.out.print("\nenter patient_id: ");
        int doctorId = scanner.nextInt();
        System.out.print("\nenter date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                } catch (SQLException e){
                    e.printStackTrace();
                }

            }else {
                System.out.println();
            }
        }else {
            System.out.println("\nEither doctor or patient doesn't exist");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT * FROM doctors WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }
}
