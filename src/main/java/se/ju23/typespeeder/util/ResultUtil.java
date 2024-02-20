package se.ju23.typespeeder.util;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>ResultUtil</h2>
 * ResultUtil is a helper class to the <i>GameService</i> class.
 * This class contains the following static methods:
 * <ul>
 *     <li>calculateNumOfCorrect</li>
 *     <li>calculateNumOfMostCorrectInOrder</li>
 *     <li>calculateNumOfQuestionMarks</li>
 *     <li>getAccuracyRoundedToTwoDigits</li>
 *     <li>calculatePointsFromAccuracy</li>
 *     <li>getLevelFromPoints</li>
 *     <li>getMinimumPointsForLevel</li>
 * </ul>
 * These methods contain pure logic and are used in the <i>GameService</i> class.
 * @date 2024-02-16
 */
public class ResultUtil {

    /**
     * This method returns the number of letters which are at the correct position
     * in the user input when compared to the game content.
     * @param gameContent This is content of the game chosen by the user.
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

    /**
     * This method calculates the num of letters which are at the correct position
     * in the user input till a mismatch occurs when compared to the chosen game's
     * content.
     * @param gameContent This is content of the game chosen by the user.
     * @param userInput This is the input given by user.
     * @return The number of letters most correct in order.
     */
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

    /**
     * This method calculates the number of ? present in the content of the chosen game.
     * @param gameContent This is the content of the game being played.
     * @return The number of ? in the content of the game.
     */
    public static int calculateNumOfQuestionMarks(String gameContent) {
        int numOfQuestionMarks = 0;

        String[] parts = gameContent.split("\n");
        for (String s : parts) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '?') {
                    numOfQuestionMarks++;
                }
            }
        }
        return numOfQuestionMarks;
    }

    /**
     * This method calculates accuracy from two given numbers and returns its value rounded to two decimal places.
     * @param num Any integer, but here it is the number of correct/most correct in order in the user's input.
     * @param total Any integer, but here it is the total number of characters in the chosen game's content.
     * @return The accuracy calculated to two decimal places.
     */
    public static double getAccuracyRoundedToTwoDigits(int num, int total) {
        double accuracy = ((double)num / total) * 100;
        return (Math.round(accuracy * 100.0))/100.0;
    }

    /**
     * This method calculates points depending upon the given value of accuracy.
     * <i>Note that if the accuracy is below 10, then the user gets -1 point.</i>
     * @param accuracy It is a double value, but rounded-off to two decimal places.
     * @return Points as an integer value.
     */
    public static int calculatePointsFromAccuracy(double accuracy) {
        if (accuracy > 0 && accuracy <= 10) {
            return -1;
        } else if (accuracy > 10 && accuracy <= 25) {
            return 1;
        } else if (accuracy > 25 && accuracy <= 40) {
            return 2;
        } else if (accuracy > 40 && accuracy <= 60) {
            return 4;
        } else if (accuracy > 60 && accuracy <= 85) {
            return 6;
        } else if (accuracy > 85 && accuracy < 100) {
            return 8;
        } else if (accuracy == 100) {
            return 10;
        } else {
            return 0;
        }
    }

    /**
     * This method returns the level number depending upon the points earned by the user.
     * @param points These are the total points earned by the user which decide the user's current level.
     * @return Level number according to the given points.
     */
    public static int getLevelFromPoints(int points) {
        if (points >= 0 && points < 50) {
            return 1;
        } else if (points >= 50 && points < 100) {
            return 2;
        } else if (points >= 100 && points < 150) {
            return 3;
        } else if (points >= 150 && points < 200) {
            return 4;
        } else if (points >= 200 && points < 250) {
            return 5;
        } else if (points >= 250 && points < 300) {
            return 6;
        } else if (points >= 300 && points < 350) {
            return 7;
        } else if (points >= 350 && points < 400) {
            return 8;
        } else if (points >= 400 && points < 450) {
            return 9;
        } else if (points >= 450 && points < 500) {
            return 10;
        }  else if (points >= 500 && points < 600) {
            return 11;
        } else {
            return 0;
        }
    }

    /**
     * This method returns the minimum number of points needed to stay at a specific level.
     * @param level Level number
     * @return Minimum points needed to stay on the given level.
     */
    public static int getMinimumPointsForLevel(int level) {
        switch(level) {
            case 1 -> {
                return 0;
            }
            case 2 -> {
                return 50;
            }
            case 3 -> {
                return 100;
            }
            case 4 -> {
                return 150;
            }
            case 5 -> {
                return 200;
            }
            case 6 -> {
                return 250;
            }
            case 7 -> {
                return 300;
            }
            case 8 -> {
                return 350;
            }
            case 9 -> {
                return 400;
            }
            case 10 -> {
                return 450;
            }
            case 11 -> {
                return 500;
            }
            default -> {
                return -1;
            }
        }
    }
}
