package task_2.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Launch {

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/task_2?serverTimezone=Asia/Almaty&useSSL=false";

            Properties authorization = new Properties();
            authorization.put("user", "root");
            authorization.put("password", "root");

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM animal");

            System.out.println("Начальная таблица БД:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            System.out.println();

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите параметры новой записи для таблицы данных:");
            System.out.print("Кличку животного: ");
            String scannedNickName = sc.nextLine();
            System.out.print("Вид животного: ");
            String scannedKind = sc.nextLine();
            System.out.print("Возраст животного: ");
            String scannedAge = sc.nextLine();
            System.out.println();

            System.out.println("После добавления строки:");
            statement.execute("INSERT animal (NickName, AnimalKind, AnimalAge) VALUES ('" + scannedNickName + "', '" + scannedKind + "', '" + scannedAge + "')");
            table = statement.executeQuery("SELECT * FROM animal");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            System.out.println();

            System.out.println("Строку с каким id хотите удалить?");
            System.out.print("id: ");
            String scannedId = sc.nextLine();
            if (!scannedId.equals("")) {
                statement.execute("DELETE FROM animal WHERE id = " + scannedId);
            }
            System.out.println();

            System.out.println("Таблица после удаления записи:");
            table = statement.executeQuery("SELECT * FROM animal");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            System.out.println();

            System.out.println("Какую запись вы хотите изменить?");
            System.out.print("id: ");
            scannedId = sc.nextLine();
            System.out.println("Теперь вводите новые данные для данной записи");
            System.out.print("Имя животного: ");
            String scannedNickNameUp = sc.nextLine();
            System.out.print("Возраст: ");
            String scannedAnimalAgeUp = sc.nextLine();
            if (!scannedId.equals("")) {
                statement.executeUpdate("UPDATE animal SET NickName = '" + scannedNickNameUp + "' WHERE id = " + scannedId);
                statement.executeUpdate("UPDATE animal SET AnimalAge = '" + scannedAnimalAgeUp + "' WHERE id = " + scannedId);
            }

            System.out.println("Данные таблицы после изменения:");
            table = statement.executeQuery("SELECT * FROM animal");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            System.out.println();

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
////            
            System.out.print("Введите фрагмент имени для фильтрации: ");
            String filter = sc.nextLine();
            System.out.print("Введите возраст для фильтрации: ");
            String age_filter = sc.nextLine();
            

            System.out.print("ASC or DESC sort?: ");
            String por = sc.nextLine();

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            System.out.println();

            System.out.println("Данные таблицыс фильтром и сортировкой:");

            if ("DESC".equals(por)) {
                table = statement.executeQuery("SELECT * FROM animal WHERE NickName like '%"
                        + filter + "%' AND AnimalAge = " + age_filter + " ORDER BY NickName DESC");
            } else {
                table = statement.executeQuery("SELECT * FROM animal WHERE NickName like '%"
                        + filter + "%' AND AnimalAge = " + age_filter + " ORDER BY NickName ASC");
            }

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
////          

            if (table != null) {
                table.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
