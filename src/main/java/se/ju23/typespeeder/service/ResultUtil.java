package se.ju23.typespeeder.service;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>ResultUtil</h2>
 * ResultUtil is a helper class to the <i>GameService</i> class.
 * This class only contains the following static methods:
 * <ul>
 *     <li>calculateNumOfCorrect</li>
 *     <li>calculateNumOfMostCorrectInOrder</li>
 *     <li>calculateNumOfQuestionMarks</li>
 *     <li>getAccuracyRoundedToTwoDigits</li>
 *     <li>calculatePointsFromAccuracy</li>
 * </ul>
 * These methods contain pure logic and are used in the <i>GameService</i> class.
 * @date 2024-02-16
 */
public class ResultUtil {

    /**
     * This method returns the number of letters which are at the correct position
     * in the user input when compared to the game content.
     * @param gameContent This is content of the game that user chooses to play.
     * @param userInput This is the input given by user.
     * @return The number of letters at the correct position in the user input.
     */
    public static int calculateNumOfCorrect(String gameContent, String userInput) {
        int correct = 0;
        int index = Math.min(gameContent.length(), userInput.length());

        for (int i = 0; i < index; i++) {
            if (gameContent.charAt(i) == userInput.charAt(i)) {
                correct++;
            }
        }
        return correct;
    }

    public static int calculateNumOfMostCorrectInOrder(String gameContent, String userInput) {
        int mostCorrect = 0;
        int index = Math.min(gameContent.length(), userInput.length());

        for (int i = 0; i < index; i++) {
            if (gameContent.charAt(i) == userInput.charAt(i)) {
                mostCorrect++;
            } else {
                break;
            }
        }
        return mostCorrect;
    }

    public static int calculateNumOfQuestionMarks(String gameContent) {
        int actualNumOfSpecialChar = 0;

        String[] parts = gameContent.split("\n");
        for (String s : parts) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '?') {                 // to calculate the no. of "?" in the given text
                    actualNumOfSpecialChar++;
                }
            }
        }

        return actualNumOfSpecialChar;
    }

    public static double getAccuracyRoundedToTwoDigits(int num, int total) {
        double accuracy = ((double)num / total) * 100;
        return (Math.round(accuracy * 100.0))/100.0;
    }

    public static int calculatePointsFromAccuracy(double accuracy) {       //accuracy has 2 decimal-digits only
        if (accuracy > 0 && accuracy <= 20) {
            return 1;
        } else if (accuracy >= 21 && accuracy <= 40) {
            return 2;
        } else if (accuracy >= 41 && accuracy <= 60) {
            return 4;
        } else if (accuracy >= 61 && accuracy <= 85) {
            return 6;
        } else if (accuracy >= 86 && accuracy <= 99.99) {
            return 8;
        } else if (accuracy == 100) {
            return 10;
        } else {
            return 0;
        }
    }
}
