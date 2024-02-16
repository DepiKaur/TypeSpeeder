package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.*;

/**
 * @author Sofie Van Dingenen
 * @version 1.1.0
 * since 2024-02-15
 *
 * ResultService
 *
 * ResultService class has method sto get the resluts from all players and caluclate the average.
 */
@Component
public class ResultService {
    @Autowired
    ResultRepo resultRepo;
    @Autowired
    PlayerRepo playerRepo;


    /**
     * Returen a map with the player's display name and the average points of the most correct points.
     * If a player has not played any games they are not included in the map.
     * @return Map with player's display name as key and the players average point of their most correct points.
     */
    public HashMap<String, Integer> getPlayerAverageMostCorrectPoints() {
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

    public HashMap<String, Integer> getPlayerAverageMostCorrectPointsInOrder() {
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
    public HashMap<String, Integer> getPlayerAverageTime() {
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
    public List<String> sortRank(HashMap<String, Integer> rankingList) {
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


}
