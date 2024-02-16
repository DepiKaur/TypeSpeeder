package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ResultService {
    @Autowired
    ResultRepo resultRepo;
    @Autowired
    PlayerRepo playerRepo;


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
    /*public ArrayList<String> getAverageTime(){
        ArrayList<String> players = new ArrayList<>();
        List<Player> allPlayers = playerRepo.findAll();
        int nGames = 0;
        int totalTime = 0;
        for (Player player: allPlayers) {
            int id = player.getId();
            Optional<List<Result>> resultList = resultRepo.findByPlayerId(id);
            if(resultList.isPresent()){
                for(Result result: resultList.get()){
                    nGames++;
                    totalTime += result.getTimeTakenInMilliSec();
                }
            }
            if (nGames > 0) {
                players.add(player.getDisplayName()+ " : " + (totalTime/nGames));
            }
    }  return players;
    }
*/

}
