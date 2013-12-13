package tags;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DbHelper {

    private static Connection connection;

    public static void init() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to load the JDBC driver class");
        }
        try {
            try {
                connection = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/talkiedb?useUnicode=true&characterEncoding=UTF-8", "root", "");
            } catch (Throwable e) {
                System.out.println("Database does not exist");
            }
            connection.prepareStatement("create table questions (Question varchar(255) charset utf8, " +
                                                "Answer varchar(255) charset utf8);")
                      .execute();
            connection.prepareStatement("create table answers (Answer varchar(255) charset utf8);").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addAllAnswersToDatabase() throws SQLException {
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("Ofc! No problems!");
        answers.add("Just try...");
        answers.add("Seems not legit.");
        answers.add("Be careful");
        answers.add("Are you serious?!");
        answers.add("We are tracking you. Be polite");
        for (String s : answers) {
            connection.createStatement().executeUpdate("insert into answers (answer) values (\"" + s + "\");");
        }
    }

    public static ArrayList<String> getAnswers() throws SQLException {
        if (connection == null) {
            init();
        }
        ArrayList<String> outputList = new ArrayList<String>();

        ResultSet rs = doQuesry("select * from answers;");

        if (!rs.next()) {
            addAllAnswersToDatabase();
            return getAnswers();
        } else {
            rs.beforeFirst();
            while (rs.next()) {
                outputList.add(rs.getString(1));
            }
            return outputList;
        }
    }

    private static ResultSet doQuesry(String sql) throws SQLException {
        System.out.println(sql);
        return connection.prepareStatement(sql).executeQuery();
    }

    private static void doUpdate(String sql) throws SQLException {
        System.out.println(sql);
        connection.prepareStatement(sql).executeUpdate();
    }

    public static void addQuestion(String question, String answer) throws SQLException {
        doUpdate(String.format("insert into questions (question, answer) values (\"%s\",\"%s\")", question, answer));
    }

    public static String getAnswerFor(String question) {
        if (connection == null) {
            init();
        }
        try {
            ResultSet rs = doQuesry("select Answer from questions where Question=\"" + question + "\"");
            if (!rs.next()) {
                List<String> answers = getAnswers();

                final Random random = new Random();
                final int selected = random.nextInt(answers.size());
                addQuestion(question, answers.get(selected));
                return answers.get(selected);
            } else {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}