package hospital_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your gender: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("\n Patient added successfully");
            }else {
                System.out.println("\n Failed to add patient");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------+--------------------------+-------+-----------+");
            System.out.println("| P_Id | P_Name                   | P_Age | P_Gender  |");
            System.out.println("+------+--------------------------+-------+-----------+");
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                String gender = result.getString("gender");
                System.out.printf("| %4s | %24s | %5s | %9s |\n", id, name, age, gender);
                System.out.println("+------+--------------------------+-------+-----------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
