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
 *
 * Rankutil is a class that calculates the rank of the players in TypeSpeeder, accoring to how quick they were,
 * how many they got correct and how many they got correct in a specific order.
 */

@Component
public class RankUtil {
    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    ResultRepo resultRepo;


    /**
     * Returen a map with the player's display name and the average points of the most correct in order points.
     * If a player has not played any games they are not included in the map.
     * @return Map with player's display name as key and the players average point of their most correct in order points.
     */

    /**
     * Returen a map with the player's display name and the average points of the most correct points.
     * If a player has not played any games they are not included in the map.
     * @return Map with player's display name as key and the players average point of their most correct points.
     */
    private HashMap<String, Integer> getPlayerAverageMostCorrectPoints() {
        HashMap<String, Integer> players = new HashMap<>();
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
                players.put(player.getDisplayName(), average);
            }
        }
        return players;
    }

    /**
     * Returen a map with the player's display name and the average points of the most correct in order points.
     * If a player has not played any games they are not included in the map.
     * @return Map with player's display name as key and the players average point of their most correct in order points.
     */

    private HashMap<String, Integer> getPlayerAverageMostCorrectPointsInOrder() {
        HashMap<String, Integer> players = new HashMap<>();
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
                players.put(player.getDisplayName(), average);
            }
        }

        return players;
    }

    /**
     * Returen a map with the player's display name and the average time. If a player has not played any games they are
     * not included in the map.
     * @return Map with player's display name as key and the players average time.
     */
    private HashMap<String, Integer> getPlayerAverageTime() {
        HashMap<String, Integer> players = new HashMap<>();
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
                players.put(player.getDisplayName(), average);
            }
        }

        return players;
    }

    /**
     * Returns a sorted list of a map with a players display name and their score (average points or time) .
     * The list is sorted by having the lowest score as the first element.
     * @param rankingList a map with the players display name and their score.
     * @return sorted list of strings looking like; display name: score
     */
    private List<String> sortRank(HashMap<String, Integer> rankingList) {
        ArrayList<String> sortedList = new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(rankingList.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<String, Integer> entry: entries){
            sortedList.add(entry.getKey() +": "+ entry.getValue());
        }

        return sortedList;
    }

    private List<String> sortRankHighToLow(List<String> list){
        Collections.reverse(list);
        return list;
    }

    public ArrayList<String> calculateRank(){
        ArrayList<String> rankingList;
        List<String> mostCorrect = sortRankHighToLow(sortRank(getPlayerAverageMostCorrectPoints()));
        List< String> fastest = sortRank(getPlayerAverageTime());
        List< String> mostCorrectInOrder = sortRankHighToLow(sortRank(getPlayerAverageMostCorrectPointsInOrder()));

        ArrayList<String> templist =combine(fastest, mostCorrect);
        rankingList = combine(templist, mostCorrectInOrder);

        return rankingList;
    }

    /**
     * Returns a combined ArrayList of two lists.
     * @param list1 List that will be combined, must be of the same length as the other list
     * @param list2 second list that will be combined, must have the same length as the other list
     * @return A combined ArrayList looking like: display name: score | display name: score
     */
    private ArrayList<String> combine(List<String> list1, List<String> list2){
        ArrayList<String> newList = new ArrayList<>();
        for(int i=0; i <list1.size(); i++){
            newList.add(list1.get(i)+"    |   " + list2.get(i));
        }
        return newList;
    }



}
