package students;

import tags.DbHelper;

/**
 * Generates reply based on random selection from the initial list of replies
 *
 * @author Anna Khasanova
 */
public class AnswerGenerator {

    public static String generateAnswer(String question) {
        return DbHelper.getAnswerFor(question);
    }
}
