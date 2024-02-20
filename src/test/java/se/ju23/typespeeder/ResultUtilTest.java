package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;
import se.ju23.typespeeder.util.ResultUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>ResultUtilTest</h2>
 * @date 2024-02-16
 */
public class ResultUtilTest {

    @Test
    public void testCalculateNumOfCorrect() {
        String gameContent = "This is test content for any game.";
        String userInput = "This is test contest for user input.";

        int numOfCorrect = ResultUtil.calculateNumOfCorrect(gameContent, userInput);

        assertEquals(24, numOfCorrect);
        assertNotEquals(14, numOfCorrect);
        assertFalse(numOfCorrect < 15);
    }

    @Test
    public void testCalculateNumOfMostCorrectInOrder() {
        String gameContent = "This is test content for any game.";
        String userInput = "This is test contest for user input.";

        int numOfMostCorrectInOrder = ResultUtil.calculateNumOfMostCorrectInOrder(gameContent, userInput);

        assertEquals(18, numOfMostCorrectInOrder);
        assertNotEquals(17, numOfMostCorrectInOrder);
        assertTrue(numOfMostCorrectInOrder > 15);
    }

    @Test
    public void testCalculateNumOfQuestionMarks() {
        String gameContent = "This is t?st content t? calc??ate n?mber of ? mar?s?";

        int numOfQuestionMarks = ResultUtil.calculateNumOfQuestionMarks(gameContent);

        assertEquals(8, numOfQuestionMarks);
        assertNotEquals(9, numOfQuestionMarks);
        assertTrue(numOfQuestionMarks > 7);
    }

    @Test
    public void testGetAccuracyRoundedToTwoDigits() {
        int num1 = 8, num2 = 5, total = 11;

        double result1 = ResultUtil.getAccuracyRoundedToTwoDigits(num1, total);
        double result2 = ResultUtil.getAccuracyRoundedToTwoDigits(num2, total);

        assertEquals(72.73, result1);
        assertEquals(45.45, result2);
        assertTrue(result1 > 72);
        assertFalse(result1 > 73);
    }

    @Test
    public void testCalculatePointsFromAccuracy() {
        double accuracy1 = 99.97, accuracy2 = 9.98, accuracy3 = -3.9, accuracy4 = 100;

        int result1 = ResultUtil.calculatePointsFromAccuracy(accuracy1);
        int result2 = ResultUtil.calculatePointsFromAccuracy(accuracy2);
        int result3 = ResultUtil.calculatePointsFromAccuracy(accuracy3);
        int result4 = ResultUtil.calculatePointsFromAccuracy(accuracy4);

        assertEquals(8, result1);
        assertEquals(-1, result2);
        assertEquals(0, result3);
        assertEquals(10, result4);
    }

    @Test
    public void testGetLevelFromPoints() {
        int points1 = 34, points2 = 200, points3 = 449;

        int result1 = ResultUtil.getLevelFromPoints(points1);
        int result2 = ResultUtil.getLevelFromPoints(points2);
        int result3 = ResultUtil.getLevelFromPoints(points3);

        assertEquals(1, result1);
        assertEquals(5, result2);
        assertEquals(9, result3);
    }

    @Test
    public void testGetMinimumPointsForLevel() {
        int level1 = 1, level2 = 5, level3 = 8;

        int result1 = ResultUtil.getMinimumPointsForLevel(level1);
        int result2 = ResultUtil.getMinimumPointsForLevel(level2);
        int result3 = ResultUtil.getMinimumPointsForLevel(level3);

        assertEquals(0, result1);
        assertEquals(200, result2);
        assertEquals(350, result3);
    }
}
