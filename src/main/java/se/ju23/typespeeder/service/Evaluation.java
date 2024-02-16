package se.ju23.typespeeder.service;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>Evaluation</h2>
 * <p>
 *     Evaluation is a <i>record</i> to save the following:
 *     <ul>
 *         <li>number of correct positions</li>
 *         <li>number of most correct in order</li>
 *         <li>total number in the given string</li>
 *     </ul>
 * </p>
 * @date 2024-02-12
 */
public record Evaluation(int numOfCorrect, int numOfMostCorrectInOrder, int totalNumInGivenString) {
}
