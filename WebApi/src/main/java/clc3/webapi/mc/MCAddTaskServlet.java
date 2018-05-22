package clc3.webapi.mc;

import clc3.montecarlo.database.entities.*;
import clc3.montecarlo.database.daos.*;
import clc3.webapi.mc.requests.MCAddTaskRequest;
import clc3.webapi.mc.responses.MCTaskResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import at.hagenberg.master.montecarlo.parser.PgnAnalysis;
import at.hagenberg.master.montecarlo.entities.Team;
import at.hagenberg.master.montecarlo.exceptions.PgnParserException;
import at.hagenberg.master.montecarlo.prediction.ChessPredictionModel;
import at.hagenberg.master.montecarlo.simulation.HeadToHeadMatch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@WebServlet(urlPatterns = "/mc/task/add")
public class MCAddTaskServlet extends BaseServlet {

    private MCTaskDao taskDao = serviceLocator.getMcTaskDao();
    
    private static Storage storage = null;

    static {
      storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    protected Object createEmptyParams() {
        return new MCAddTaskRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MCAddTaskRequest req = (MCAddTaskRequest)getPostParameters(request, MCAddTaskRequest.class);
        
        MCSettings settings = req.getMCSettings();

        RandomGenerator randomGenerator = new Well19937c();
        ChessPredictionModel predictionModel = new ChessPredictionModel(settings.isUseEloRating(), settings.isUseAdvWhite(), settings.isUseStrengthTrend(), settings.isUseStats(), settings.isUseRegularization());
        predictionModel.statsFactor = 2;
        BlobId blobId = BlobId.of("clc3-project-benjamin.appspot.com", "historicData" + settings.getSeasonFileName() + ".pgn");
        byte[] content = storage.readAllBytes(blobId);
        String historicDataContent = new String(content, UTF_8);

        PgnAnalysis analysis = null;
        try{
            analysis = new PgnAnalysis(settings.getFileSeasonToSimulateContent(), historicDataContent, settings.getRoundsPerSeason(), settings.getGamesPerMatch());
            analysis.fillGamesFromSeasonToSimulate(randomGenerator, predictionModel);
        } catch(PgnParserException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        predictionModel.setStatistics(analysis);
        
        settings.setPredictionModel(predictionModel);

        List<MCTeam> mcTeams = new ArrayList<>();
        List<Team> teams = analysis.getTeams();        
        for (int i = 0; i < teams.size(); i++) {
            mcTeams.add(new MCTeam(teams.get(i), settings.getGamesPerMatch()));
        }

        //settings.setTeams(mcTeams);

        Map<String, List<MCHeadToHeadMatch>> rounds = new HashMap<>();
        for(Map.Entry<Integer, List<HeadToHeadMatch>> entry : analysis.getRoundGameResults().entrySet()) {
            List<MCHeadToHeadMatch> list = new ArrayList<>();
            for (int i = 0; i < entry.getValue().size(); i++) {
                list.add(new MCHeadToHeadMatch(entry.getValue().get(i)));
            }
            rounds.put(entry.getKey().toString(), list);
        }

        //settings.setRoundGameResults(rounds);

        MCTask mc = new MCTask();
        mc.setName(req.getName());
        mc.setIterations(req.getIterations());
        mc.setConcurrentWorkers(req.getConcurrentWorkers());
        mc.setMCSettings(settings);
        mc.setTeams(mcTeams);
        mc.setRoundGameResults(rounds);
        mc.setCreated(new Date());
        mc.setQueueTaskNames(new ArrayList<String>());

        taskDao.save(mc);

        // create iteration results file
        String bucketName = "clc3-project-benjamin.appspot.com";
        String fileName = "simulation-results-promotion-" + mc.getName() + ".csv";
        String fileName2 = "simulation-results-relegation-" + mc.getName() + ".csv";
        String header = teams.stream().sorted(Comparator.comparing(Team::getName)).map(t -> t.getName()).collect(Collectors.joining(";")) + ";rmse;iterations\n";
        header += teams.stream().map(t -> "0").collect(Collectors.joining(";")) + ";0;0\n";

        BlobInfo blobInfo = storage.create(
                BlobInfo
                    .newBuilder(bucketName, fileName)
                    // Modify access list to allow all users with link to read file
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                    .build(),
                    new ByteArrayInputStream(header.getBytes(StandardCharsets.UTF_8)));
        
        BlobInfo blobInfo2 = storage.create(
            BlobInfo
                .newBuilder(bucketName, fileName2)
                // Modify access list to allow all users with link to read file
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(),
                new ByteArrayInputStream(header.getBytes(StandardCharsets.UTF_8)));

        jsonResponse(response, new MCTaskResponse(mc));
    }
}