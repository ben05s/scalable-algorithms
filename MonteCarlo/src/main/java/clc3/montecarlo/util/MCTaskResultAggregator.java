package clc3.montecarlo.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import at.hagenberg.master.montecarlo.entities.TeamSimulationResult;
import clc3.montecarlo.database.entities.MCTaskResult;

public final class MCTaskResultAggregator {

    private static Storage storage = null;

    static {
      storage = StorageOptions.getDefaultInstance().getService();
    }
    
    public static MCTaskResult aggregateTaskResults(String seasonFileName, List<MCTaskResult> taskResults) {
        MCTaskResult aggregatedTaskResult = new MCTaskResult();
        int calculatedIterations = 0;
        List<Long> computeTimes = new ArrayList<>();

        String bucketName = "clc3-project-benjamin.appspot.com";
        String promotionFileName = "simulation-results-promotion-" + seasonFileName + ".csv";
        BlobId blobId = BlobId.of(bucketName, promotionFileName);
        byte[] content = storage.readAllBytes(blobId);
        String contentPromotion = new String(content, StandardCharsets.UTF_8);

        String relegationFileName = "simulation-results-relegation-" + seasonFileName + ".csv";
        blobId = BlobId.of(bucketName, relegationFileName);
        content = storage.readAllBytes(blobId);
        String contentRelegation = new String(content, StandardCharsets.UTF_8);

        double promotionRMSE = 0.0;
        double relegationRMSE = 0.0;

        Map<String, TeamSimulationResult> aggregatedTeamResults = new HashMap<>();    
        Iterator<MCTaskResult> taskResultIt = taskResults.iterator();
        double count = 0.0;
        while(taskResultIt.hasNext()) {
            MCTaskResult taskResult = taskResultIt.next();
            for(TeamSimulationResult teamResult: taskResult.getTeamResults()) {
                TeamSimulationResult ts = aggregatedTeamResults.get(teamResult.getTeamName());
                if(ts == null) {
                    ts = teamResult;
                } else {
                    ts.aggregate(teamResult);
                }

                calculatedIterations = ts.getTotalSeasons();

                aggregatedTeamResults.put(ts.getTeamName(), ts);
            }
            
            computeTimes.addAll(taskResult.getComputeTimes());
            
            count++;
            promotionRMSE += taskResult.getPromotionRMSE();
            relegationRMSE += taskResult.getRelegationRMSE();

            contentPromotion += aggregatedTeamResults.values().stream().sorted(Comparator.comparing(TeamSimulationResult::getTeamName)).map(ts -> 
                String.format("%.4f", ts.getRatioPromotion()).replace(".", ",")).collect(Collectors.joining(";")) + ";" + String.format("%.4f", promotionRMSE / count).replace(".", ",") + ";" + calculatedIterations + "\n";
            contentRelegation += aggregatedTeamResults.values().stream().sorted(Comparator.comparing(TeamSimulationResult::getTeamName)).map(ts -> 
                String.format("%.4f", ts.getRatioRelegation()).replace(".", ",")).collect(Collectors.joining(";")) + ";" + String.format("%.4f", relegationRMSE / count).replace(".", ",") + ";" + calculatedIterations + "\n";
        }

        BlobInfo blobInfo = storage.create(
                BlobInfo
                    .newBuilder(bucketName, promotionFileName)
                    // Modify access list to allow all users with link to read file
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                    .build(),
                    new ByteArrayInputStream(contentPromotion.getBytes(StandardCharsets.UTF_8)));
        
        BlobInfo blobInfo2 = storage.create(
            BlobInfo
                .newBuilder(bucketName, relegationFileName)
                // Modify access list to allow all users with link to read file
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(),
                new ByteArrayInputStream(contentRelegation.getBytes(StandardCharsets.UTF_8)));

        List<TeamSimulationResult> sortedTeamResult = new ArrayList<>(aggregatedTeamResults.values());
        Collections.sort(sortedTeamResult);

        aggregatedTaskResult.setTeamResults(sortedTeamResult);
        aggregatedTaskResult.setPromotionRMSE(promotionRMSE / count);
        aggregatedTaskResult.setRelegationRMSE(relegationRMSE / count);
        aggregatedTaskResult.setCacluatedIterations(calculatedIterations);
        aggregatedTaskResult.setComputeTimes(computeTimes);
        return aggregatedTaskResult;
    }
}