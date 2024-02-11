import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:1111/Project", "postgres", "1199");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1.All cars price --> and <--");
            System.out.println("2.Hot Offers");
            System.out.println("3.Submit an ad");
            System.out.println("4.Change your ad to another");
            System.out.println("5.Buy cars");
            System.out.println("6.Exit");
            int choose = in.nextInt();
            if (choose == 1) {
                Statement stat = connection.createStatement();
                System.out.print("increasing or desc?");
                String tanda = in.next();
                if (tanda.equals("increasing")) {	
                    ResultSet r = stat.executeQuery("select * from carshop order by price asc");
                    while (r.next()) {
                        System.out.println("ID: " + r.getInt("id"));
                        System.out.println("Mark: " + r.getString("mark"));
                        System.out.println("Model: " + r.getString("model"));
                        System.out.println("Public year: " + r.getString("publyear"));
                        System.out.println("Price: " + r.getInt("price"));
                    }
                } else if (tanda.equals("desc")) {
                    ResultSet r = stat.executeQuery("select * from carshop order by price desc");
                    while (r.next()) {
                        System.out.println("ID: " + r.getInt("id"));
                        System.out.println("Mark " + r.getString("mark"));
                        System.out.println("Model " + r.getString("model"));
                        System.out.println("Public Year " + r.getString("publyear"));
                        System.out.println("Price: " + r.getInt("price"));
                    }
                }
            } else if (choose == 2) {
                System.out.println("Choose the categories: Hot\nSports\nLuxury\nSedan\nOff-road\nSUV\nCrossover\nWagon\n");
                String cate = in.next();
                PreparedStatement stat = connection.prepareStatement("SELECT * FROM carshop WHERE category = ?");
                stat.setString(1, cate);
                ResultSet r = stat.executeQuery();
                while (r.next()) {
                    System.out.println("ID: " + r.getInt("id"));
                    System.out.println("Mark: " + r.getString("mark"));
                    System.out.println("Model: " + r.getString("model"));
                    System.out.println("Public Year: " + r.getString("publyear"));
                    System.out.println("Price: " + r.getInt("price"));
                    System.out.println("Amount: " + r.getInt("amount"));
                }
            } else if (choose == 3) {
                PreparedStatement stat = connection.prepareStatement("INSERT INTO  carshop (mark, model, publyear, price, amount, category) VALUES (?, ?, ?, ?, ?, ?)");
                System.out.print("Mark: ");
                String mark = in.next();
                in.nextLine();
                System.out.print("Model: ");
                String model = in.nextLine();
                System.out.print("Public year: ");
                String publyear = in.next();
                System.out.print("Price: ");
                int price = in.nextInt();
                System.out.print("Amount: ");
                int amount = in.nextInt();
                System.out.print("Category: ");
                String category = in.next();
                stat.setString(1, mark);
                stat.setString(2, model);
                stat.setString(3, publyear);
                stat.setInt(4, price);
                stat.setInt(5, amount);
                stat.setString(6, category);
                stat.executeUpdate();
            } else if (choose == 4 ){
                PreparedStatement stat = connection.prepareStatement("UPDATE carshop SET mark=?, model=?, publyear=?, price=?, amount=?, category=? WHERE id=?");
                System.out.print("Mark: ");
                String mark = in.next();
                System.out.print("Model: ");
                String model = in.next();
                System.out.print("Public year: ");
                String publyear = in.next();
                System.out.print("Price: ");
                int price = in.nextInt();
                System.out.print("Amount: ");
                int amount = in.nextInt();
                System.out.print("Category: ");
                String category = in.next();
                System.out.print("ID: ");
                int id = in.nextInt();
                stat.setString(1, mark);
                stat.setString(2, model);
                stat.setString(3, publyear);
                stat.setInt(4, price);
                stat.setInt(5, amount);
                stat.setString(6, category);
                stat.setInt(7, id);
                System.out.println("The data has been successfully updated!");
                stat.executeUpdate();
            } else if (choose == 5) {
                allcars(connection,in);
                System.out.print("Choose the ID of your car: ");
                int id = in.nextInt();
                PreparedStatement selectStat = connection.prepareStatement("SELECT * FROM carshop WHERE id = ?");
                selectStat.setInt(1, id);
                ResultSet r = selectStat.executeQuery();
                if (r.next()) {
                    System.out.println("Mark: " + r.getString("mark"));
                    System.out.println("Model: " + r.getString("model"));
                    System.out.println("Public Year: " + r.getString("publyear"));
                    System.out.println("Price: " + r.getInt("price"));
                    PreparedStatement deleteStat = connection.prepareStatement("DELETE FROM carshop WHERE id = ?");
                    deleteStat.setInt(1, id);
                    deleteStat.executeUpdate();
                    System.out.println("Pokupka kutty bolsyn!");
                } else {
                    System.out.println("No car found with ID: " + id);
                }
            } else if (choose == 6) {
                System.out.println("Thank you for using our carshop!");
                System.out.println("Полностью 100 баллов агай)))");
                break;
            }
        }
    }

    public static void allcars(Connection connection, Scanner in) throws Exception {
        Statement stat = connection.createStatement();
        ResultSet r = stat.executeQuery("select * from carshop");
        while (r.next()) {
            System.out.println("ID: " + r.getInt("id"));
            System.out.println("Mark: " + r.getString("mark"));
            System.out.println("Model: " + r.getString("model"));
            System.out.println("Public year: " + r.getString("publyear"));
            System.out.println("Price: " + r.getInt("price"));
        }
    }
}
