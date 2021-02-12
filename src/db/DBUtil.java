package db;

import entities.BeneficiaryUtil;
import entities.User;
import exceptions.DBException;
import interfaces.IUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class DBUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Connection conn = new ConnectionFactory().getConnection();
    private static Statement st = null;
    private static ResultSet rs = null;

    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (Exception ex){
                throw new DBException("Erro ao finalizar conexão!");
            }
        }
    }

    public static void closeStatemente(Statement st){
        if(st != null) {
            try {
                st.close();
            } catch (Exception ex){
                throw new DBException(ex.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs != null) {
            try {
                rs.close();
            } catch (Exception ex){
                throw new DBException(ex.getMessage());
            }
        }
    }

    public static void insertIntoDatabase(User user){
        char beneficiary = BeneficiaryUtil.isBeneficiary(user) ? 'S' : 'N';
        String insert = "";
        try{
            st = conn.createStatement();
            if (BeneficiaryUtil.isBeneficiary(user)){
                insert = "insert into beneficiary (name, benefit_value, benefit_duration, " +
                        "category_id, born_date, state, beneficiary) values(" +
                        "\'" + user.getName() + "\'," + user.calculateBenefitValue() +
                        "," + user.calculateBenefitDuration() + "," + user.getCategory().getId() +
                        ",\'" + sdf.format(user.getBornDate()) + "\',\'" + user.getState() +
                        "\',\'" + beneficiary + "\');";
            } else {
                insert = "insert into beneficiary (name, benefit_value, benefit_duration, " +
                        "category_id, born_date, state, beneficiary) values(" +
                        "\'" + user.getName() + "\'," + 0.0 +
                        "," + 0.0 + "," + user.getCategory().getId() +
                        ",\'" + sdf.format(user.getBornDate()) + "\',\'" + user.getState() +
                        "\',\'" + beneficiary + "\');";
            }
            st.execute(insert);
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }
    }

    public static void deleteFromDatabase(int id){
        try{
            st = conn.createStatement();
            String delete = "delete from beneficiary where id = " + id;
            st.execute(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listAll(){
        try{
            st = conn.createStatement();
            String select = "select * from beneficiary";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println(rs.getString("id")
                                 + ", " + rs.getString("name")
                                 + ", " + rs.getDouble("benefit_value")
                                 + ", " + rs.getDouble("benefit_duration")
                                 + ", " + rs.getInt("category_id")
                                 + ", " + rs.getString("born_date")
                                 + ", " + rs.getString("state"));
            }
        } catch (Exception ex){
            throw new DBException(ex.getMessage());
        }
    }

    public static void quantityUsers(){
        try{
            st = conn.createStatement();
            String select = "select count(*)quantity from beneficiary";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println("Quantidade de usuários lidos: " + rs.getInt("quantity"));
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static void quantityBeneficiaries(){
        try{
            st = conn.createStatement();
            String select = "select count(*)quantity from beneficiary where beneficiary = 'S'";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println("Quantidade de beneficiários lidos: " + rs.getInt("quantity"));
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static void getTotalBenefitValue(){
        try{
            st = conn.createStatement();
            String select = "select sum(benefit_value)total_value from beneficiary";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println("Valor total concedido: R$" + rs.getDouble("total_value"));
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static void bestPaidBeneficiary(){
        try{
            st = conn.createStatement();
            String select = "select name, benefit_value from beneficiary order by benefit_value desc limit 2";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println(rs.getString("name") + ", R$ " + rs.getDouble("benefit_value"));
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static void mostLongerBenefitBeneficiary(){
        try{
            st = conn.createStatement();
            String select = "select name, benefit_duration from beneficiary order by benefit_duration desc limit 2";
            rs = st.executeQuery(select);
            while (rs.next()){
                System.out.println(rs.getString("name") + ", " +
                        rs.getDouble("benefit_duration") + " meses");
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }
}
