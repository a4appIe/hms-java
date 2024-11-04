package hospital_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private Connection connection;
    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors(){
        String query = "SELECT * FROM doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------+--------------------------+-------------------+");
            System.out.println("| D_Id | D_Name                   | Specialization    |");
            System.out.println("+------+--------------------------+-------------------+");
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                String specialization = result.getString("specialization");
                System.out.printf("| %4s | %24s | %17s |\n", id, name, specialization);
                System.out.println("+------+--------------------------+-------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
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
