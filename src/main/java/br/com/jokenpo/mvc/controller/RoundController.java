package br.com.jokenpo.mvc.controller;

import br.com.jokenpo.mvc.entity.Round;
import br.com.jokenpo.mvc.repository.RoundRepository;
import br.com.jokenpo.mvc.repository.UserRepository;
import br.com.jokenpo.util.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Random;

@Controller
@RequestMapping("/round")
public class RoundController {
    private int rounds = 0;
    private RoundRepository roundRepository;
    private UserRepository userRepository;

    @Autowired
    public RoundController(RoundRepository roundRepository, UserRepository userRepository) {
        this.roundRepository = roundRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCountRounds(){
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postRound(Move userMove, Model model) {
        if (userMove == null) {
            model.addAttribute("noChoose", true);
            return "index2";
        }
        Move computerMove = computerMove();
        //Information to the screen
        model.addAttribute("userChoice", userMove.getDescription());
        model.addAttribute("computerChoice", computerMove.getDescription());

        checkTheWinner(userMove, computerMove, model);
        //Count the rounds
        rounds++;
        //it's the last round
        if (rounds == 10) {
            long countUserWins = roundRepository.countByPlayerWin(true);
            long countComputersWins = roundRepository.countByComputerWin(true);

            if (countUserWins > countComputersWins) {
                model.addAttribute("finishPlayerWins", String.valueOf(countUserWins));
            } else if (countUserWins < countComputersWins) {
                model.addAttribute("finishComputerWins", String.valueOf(countComputersWins));
            } else {
                model.addAttribute("finishDraw", true);
            }
            //clear databases
            clearDatabase();
            //Clear the rounds
            rounds = 0;
            return "index";
        }
        return "index2";
    }

    /**
     * Verify by the move of each one who wins or if it's a dran
     * @param userMove User movement
     * @param computerMove Computer movement
     * @param model model to send information to the screen
     */
    public void checkTheWinner(Move userMove, Move computerMove, Model model) {
        if (!userMove.equals(computerMove)) {
            //when computer wins
            if ((userMove.equals(Move.PAPER) && computerMove.equals(Move.SCISSORS)) ||
                    (userMove.equals(Move.ROCK) && computerMove.equals(Move.PAPER)) ||
                    (userMove.equals(Move.SCISSORS) && computerMove.equals(Move.ROCK))) {
                model.addAttribute("computer", true);
                roundRepository.saveAndFlush(new Round(false, true));
            } else {
                //When player win
                model.addAttribute("user", true);
                roundRepository.saveAndFlush(new Round(true, false));
            }
        } else {
            //when it's a draw
            model.addAttribute("draw", true);
            roundRepository.saveAndFlush(new Round(true, true));
        }
    }

    /**
     * Do a random move.
     */
    public Move computerMove() {
        Random random = new Random();
        int moveId = random.nextInt(2);
        if (moveId == Move.SCISSORS.getId()) {
            return Move.SCISSORS;
        } else if (moveId == Move.ROCK.getId()) {
            return Move.ROCK;
        } else if (moveId == Move.PAPER.getId()) {
            return Move.PAPER;
        } else {
            return null;
        }
    }

    /**
     * Clear the tables from M2
     */
    public void clearDatabase(){
        roundRepository.deleteAll();
        userRepository.deleteAll();
    }
}
