package se.ju23.typespeeder.util;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>PointsEvaluation</h2>
 * <p>
 *     PointsEvaluation is a record to save the number of bonus points and points to be deducted
 *     depending upon user's performance.
 * </p>
 * @date 2024-02-18
 */
public record PointsEvaluation(int bonusPoints, int pointsToBeDeducted) {
}
