package se.ju23.typespeeder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.*;

/**
 * @author Sofie Van Dingenen
 * since 2024-02-19
 *
 * <h2>RankUtil</h2>
 * <p>
 * Rankutil is a class that calculates the rank of the players in TypeSpeeder, accoring to how quick they were,
 * how many they got correct and how many they got correct in a specific order.
 */

@Component
public class RankUtil {
    private PlayerRepo playerRepo;
    private ResultRepo resultRepo;

    @Autowired
    public RankUtil(PlayerRepo playerRepo, ResultRepo resultRepo) {
        this.playerRepo = playerRepo;
        this.resultRepo = resultRepo;
    }


    /**
     * Returen a map with the player's display name and the average points of the most correct in order points.
     * If a player has not played any games they are not included in the map.
     * @return Map with player's display name as key and the players average point of their most correct in order points.
     */

    /**
     * Returen a map with the player's display name and the average points of the most correct points.
     * If a player has not played any games they are not included in the map.
     *
     * @return Map with player's display name as key and the players average point of their most correct points.
     */
    private HashMap<String, Integer> getPlayerAverageMostCorrectPoints() {
        HashMap<String, Integer> playersAndPoints = new HashMap<>();
        List<Player> allPlayers = playerRepo.findAll();
        for (Player player : allPlayers) {
            int nGames = 0;
            int totalpointsForCorrect = 0;
            int id = player.getId();
            Optional<List<Result>> resultList = resultRepo.findByPlayerId(id);
            if (resultList.isPresent()) {
                for (Result result : resultList.get()) {
                    nGames++;
                    totalpointsForCorrect += result.getPointsForCorrect();
                }
            }
            if (nGames > 0) {
                int average = totalpointsForCorrect / nGames;
                playersAndPoints.put(player.getDisplayName(), average);
            }
        }
        return playersAndPoints;
    }

    /**
     * Returen a map with the player's display name and the average points of the most correct in order points.
     * If a player has not played any games they are not included in the map.
     *
     * @return Map with player's display name as key and the players average point of their most correct in order points.
     */

    private HashMap<String, Integer> getPlayerAverageMostCorrectPointsInOrder() {
        HashMap<String, Integer> playersAndPoints = new HashMap<>();
        List<Player> allPlayers = playerRepo.findAll();

        for (Player player : allPlayers) {
            int nGames = 0;
            int totalpointsForCorrectInOrder = 0;
            int id = player.getId();
            Optional<List<Result>> resultList = resultRepo.findByPlayerId(id);
            if (resultList.isPresent()) {
                for (Result result : resultList.get()) {
                    nGames++;
                    totalpointsForCorrectInOrder += result.getPointsForCorrectInOrder();
                }
            }
            if (nGames > 0) {
                int average = totalpointsForCorrectInOrder / nGames;
                playersAndPoints.put(player.getDisplayName(), average);
            }
        }

        return playersAndPoints;
    }

    /**
     * Returen a map with the player's display name and the average time. If a player has not played any games they are
     * not included in the map.
     *
     * @return Map with player's display name as key and the players average time.
     */
    private HashMap<String, Integer> getPlayerAverageTime() {
        HashMap<String, Integer> playersAndTime = new HashMap<>();
        List<Player> allPlayers = playerRepo.findAll();
        for (Player player : allPlayers) {
            int nGames = 0;
            int totalTime = 0;
            int id = player.getId();
            Optional<List<Result>> resultList = resultRepo.findByPlayerId(id);
            if (!resultList.isEmpty()) {
                for (Result result : resultList.get()) {
                    nGames++;

                    totalTime += result.getTimeTakenInMilliSec();

                }
            }
            if (nGames > 0) {
                int average = totalTime / nGames;
                playersAndTime.put(player.getDisplayName(), average);
            }
        }

        return playersAndTime;
    }

    /**
     * Returns a sorted list of a map with a players display name and their score (average points or time) .
     * The list is sorted by having the lowest score as the first element.
     *
     * @param rankingList a map with the players display name and their score.
     * @return sorted list of strings looking like; display name: score
     */
    private List<String> sortRankLowToHigh(HashMap<String, Integer> rankingList) {
        ArrayList<String> sortedList = new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(rankingList.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : entries) {
            sortedList.add(entry.getKey() + ": " + (entry.getValue()*0.001)+"s");
        }

        return sortedList;
    }

    private List<String> sortRankHighToLow(HashMap<String, Integer> rankingList) {
        ArrayList<String> sortedList = new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(rankingList.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : entries) {
            sortedList.add(entry.getKey() + ": " + entry.getValue()+"p");
        }

        return sortedList;
    }
    public ArrayList<String> calculateRank() {
        ArrayList<String> rankingList;
        List<String> mostCorrect = sortRankHighToLow(getPlayerAverageMostCorrectPoints());
        List<String> fastest = sortRankLowToHigh(getPlayerAverageTime());
        List<String> mostCorrectInOrder = sortRankHighToLow(getPlayerAverageMostCorrectPointsInOrder());
        List<String> totalRank = calculateTotalRank(getPlayerTototalPoints());

        ArrayList<String> templist = combineList(fastest, mostCorrect);
        ArrayList<String> templist2 = combineList(mostCorrectInOrder, totalRank);
        rankingList = combineList(templist, templist2);

        return rankingList;
    }

    /**
     * Returns a combined ArrayList of two lists.
     *
     * @param list1 List that will be combined, must be of the same length as the other list
     * @param list2 second list that will be combined, must have the same length as the other list
     * @return A combined ArrayList looking like: display name: score | display name: score
     */
    private ArrayList<String> combineList(List<String> list1, List<String> list2) {
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            newList.add(list1.get(i) + "    |   " + list2.get(i));
        }
        return newList;
    }

    private HashMap<String, Integer[]> getPlayerTototalPoints() {
        HashMap<String, Integer[]> playersAndPoints = new HashMap<>();
        List<Player> allPlayers = playerRepo.findAll();
        for (Player player : allPlayers) {
            int nGames = 0;
            int totalPoints = 0;
            int totaltime = 0;
            int id = player.getId();
            Optional<List<Result>> resultList = resultRepo.findByPlayerId(id);
            if (resultList.isPresent()) {
                for (Result result : resultList.get()) {
                    nGames++;
                    totalPoints += result.getPointsForCorrect() + result.getPointsForCorrectInOrder() + result.getBonusPoints() + result.getDeductedPoints();
                    totaltime += result.getTimeTakenInMilliSec();
                }
            }
            if (nGames > 0) {
                Integer[] total = {totalPoints, (int)(totaltime*0.001)};
                playersAndPoints.put(player.getDisplayName(), total);
            }
        }
        return playersAndPoints;
    }

    private List<String> calculateTotalRank(HashMap<String, Integer[]> rankMap) {

        ArrayList<String> sortedList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        List<Map.Entry<String, Integer[]>> entries = new ArrayList<>(rankMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer[]>>() {
            @Override
            public int compare(Map.Entry<String, Integer[]> o1, Map.Entry<String, Integer[]> o2) {
                if(o1.getValue()[0].compareTo(o2.getValue()[0])==0){
                    return o1.getValue()[1].compareTo(o2.getValue()[1]);
                }
                return o2.getValue()[0].compareTo(o1.getValue()[0]);
            }
        });


        Map<String, Integer[]> sordetMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer[]> entry : entries) {

            sortedList.add(entry.getKey() + ": " + entry.getValue()[0] + "p " + entry.getValue()[1]+ "s");
        }
        return sortedList;
    }





}



