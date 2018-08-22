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
    private UserRepository userRepository;
    private JokenpoDefault jokenpoDefault;

    @Autowired
    public RoundController(UserRepository userRepository, JokenpoDefault jokenpoDefault) {
        this.userRepository = userRepository;
        this.jokenpoDefault = jokenpoDefault;
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

        jokenpoDefault.checkTheWinner(userMove, computerMove, model);
        //Count the rounds
        rounds++;
        //it's the last round
        if (rounds == 10) {
            long countUserWins = jokenpoDefault.countByPlayerWin();
            long countComputersWins = jokenpoDefault.countByComputerWin();

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
        jokenpoDefault.deleteAllRounds();
        userRepository.deleteAll();
    }
}
