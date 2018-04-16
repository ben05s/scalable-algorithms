package clc3.webapi.mc;

import clc3.webapi.mc.requests.FileUploadRequest;
import clc3.webapi.mc.responses.ChessLeagueResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import org.apache.log4j.Level;

import at.hagenberg.master.montecarlo.entities.Player;
import at.hagenberg.master.montecarlo.entities.Team;

import java.io.StringReader;

import com.supareno.pgnparser.PGNParser;
import com.supareno.pgnparser.jaxb.Game;
import com.supareno.pgnparser.jaxb.Games;

@WebServlet(urlPatterns = "/mc/upload/pgn")
public class MCUploadServlet extends BaseServlet {

    @Override
    protected Object createEmptyParams() {
        return new FileUploadRequest();
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   
        FileUploadRequest req = (FileUploadRequest)getPostParameters(request, FileUploadRequest.class);
        
        ChessLeagueResponse resp = extractTeamsFromPgn(req.getFileContent());

        jsonResponse(response, resp);
    }

    private ChessLeagueResponse extractTeamsFromPgn(String pgnContent) {
        ChessLeagueResponse resp = new ChessLeagueResponse();

        Map<String, Team> teamMap = new HashMap<String, Team>();
        PGNParser parser = new PGNParser(Level.ALL);
        Games games = parser.parseFile(new StringReader(pgnContent));

        for (int i = 0; i < games.getGame().size(); i++) {
            Game game = games.getGame().get(i);
            
            resp.setLeagueName(game.getEvent());

            Team whiteTeam = teamMap.get(game.getWhiteTeam());
            if(whiteTeam == null) {
                whiteTeam = new Team(game.getWhiteTeam(), 0);
            }
            
            Player whitePlayer = new Player();
            whitePlayer.setName(game.getWhite());
            whitePlayer.setElo(Integer.parseInt(game.getWhiteElo()));

            if(!whiteTeam.getPlayerList().contains(whitePlayer))
                whiteTeam.addPlayer(whitePlayer);

            teamMap.put(game.getWhiteTeam(), whiteTeam);

            Team blackTeam = teamMap.get(game.getBlackTeam());
            if(blackTeam == null) {
                blackTeam = new Team(game.getBlackTeam(), 0);
            }

            Player blackPlayer = new Player();
            blackPlayer.setName(game.getBlack());
            blackPlayer.setElo(Integer.parseInt(game.getBlackElo()));

            if(!blackTeam.getPlayerList().contains(blackPlayer))
                blackTeam.addPlayer(blackPlayer);

            teamMap.put(game.getBlackTeam(), blackTeam);
        }
        resp.setTeams(new ArrayList<>(teamMap.values()));
        return resp;
    }
}