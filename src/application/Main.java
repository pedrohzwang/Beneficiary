package application;

import db.ConnectionFactory;
import db.DBUtil;
import entities.*;
import exceptions.DBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //ArrayList<User> users = new ArrayList();
        //ArrayList<User> beneficiaries = new ArrayList();
        boolean stop = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean retired = false;
        int employeeNumber = 0;
        double unemployedMonths = 0.0;
        User actualUser = null;
        Double totalValue = 0.00;
        double highestValue = 0;
        Connection conn = new ConnectionFactory().getConnection();
        Statement statement = null;
        ResultSet rs = null;

        try (Scanner sc = new Scanner(System.in)){
            statement = conn.createStatement();
            do {
                System.out.println("Informe o nome do beneficiário:\n");
                String name = sc.nextLine();
                System.out.println("Informe a data de nascimento do beneficiário (dd/MM/yyyy):\n");
                Date bornDate = sdf.parse(sc.nextLine());
                System.out.println("Informe a categoria do beneficiário:\n");
                System.out.println("1 se for empregado;\n" +
                        "2 se for empregador;\n" +
                        "3 se for desempregado;\n" +
                        "4 caso não atenda a nenhuma das anteriores.");

                int categoryId = sc.nextInt();
                switch (categoryId){
                    case 1:
                        System.out.println("Aposentado? (S/N)");
                        sc.nextLine();
                        retired = sc.nextLine().equalsIgnoreCase("S") ? true : false;
                        break;
                    case 2:
                        System.out.println("Número de funcionários:");
                        employeeNumber = sc.nextInt();
                        sc.nextLine();
                        break;
                    case 3:
                        System.out.println("Tempo em situação de desemprego (meses):");
                        unemployedMonths = sc.nextDouble();
                        sc.nextLine();
                        break;
                    case 4:
                        sc.nextLine();
                        break;
                }
                System.out.println("Informe a UF do beneficiário:");
                String state = sc.nextLine();

                switch (categoryId){
                    case 1:
                        actualUser = new Employee(name, bornDate, state, retired);
                        break;
                    case 2:
                        actualUser = new Employer(name, bornDate, state, employeeNumber);
                        break;
                    case 3:
                        actualUser = new Unemployed(name, bornDate, state, unemployedMonths);
                        break;
                    default:
                        actualUser = new NotBenefited(name, bornDate, state);
                        break;
                }

                /*if(categoryId == 1 || categoryId == 2 || categoryId == 3){
                    beneficiaries.add(actualUser);
                    users.add(actualUser);
                } else {
                    users.add(actualUser);
                }*/

                if(actualUser instanceof Employee){
                    Employee other = (Employee) actualUser;
                    totalValue += other.calculateBenefitValue();
                } else if (actualUser instanceof Employer){
                    Employer other = (Employer) actualUser;
                    totalValue += other.calculateBenefitValue();
                } else if(actualUser instanceof Unemployed){
                    Unemployed other = (Unemployed) actualUser;
                    totalValue += other.calculateBenefitValue();
                }

                if(categoryId != 4){
                    System.out.println("Nome do beneficiário: " + actualUser.getName()
                            + "\nData de nascimento: " + sdf.format(actualUser.getBornDate())
                            + "\nCategoria: " + actualUser.getCategory().getDescription()
                            + "\nTempo do benefício: " + actualUser.calculateBenefitDuration() + " meses"
                            + "\nValor do benefício: R$ " + actualUser.calculateBenefitValue());
                } else {
                    System.out.println("Infelizmente você não tem direito ao benefício.");
                }

                DBUtil.insertIntoDatabase(actualUser);


                System.out.println("Deseja deletar algum usuário do cadastro? (S/N):\n");
                if(sc.nextLine().equalsIgnoreCase("S")){
                    System.out.println("\nInforme o id do usuário a ser deletado:");
                    DBUtil.deleteFromDatabase(sc.nextInt());
                }

                System.out.println("Deseja informar um novo usuário? (S/N):\n");
                String s = sc.nextLine();
                stop = !s.equalsIgnoreCase("S");
                s = sc.nextLine();
            } while (stop != true);

            DBUtil.listAll();
            DBUtil.quantityUsers();
            DBUtil.quantityBeneficiaries();
            DBUtil.getTotalBenefitValue();
            System.out.println("Beneficiários com maior valor de benfício: \n");
            DBUtil.bestPaidBeneficiary();
            System.out.println("Beneficiários com maior duração de benfício: \n");
            DBUtil.mostLongerBenefitBeneficiary();
            /*System.out.println("Total de usuários lidos: " + users.size());
            System.out.println("Total de beneficiários: " + beneficiaries.size());
            System.out.println("Valor total concedido: " + totalValue);*/

        } catch (RuntimeException | ParseException ex){
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatemente(statement);
            DBUtil.closeConnection(conn);
        }
    }
}
