package se.ju23.typespeeder.util;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>UserInputEvaluation</h2>
 * <p>
 *     UserInputEvaluation is a <i>record</i> to save the following:
 *     <ul>
 *         <li>number of correct positions</li>
 *         <li>number of most correct in order</li>
 *         <li>total number in the given string</li>
 *     </ul>
 * </p>
 * @date 2024-02-12
 */
public record UserInputEvaluation(int numOfCorrect, int numOfMostCorrectInOrder, int totalNumInGivenString) {
}
