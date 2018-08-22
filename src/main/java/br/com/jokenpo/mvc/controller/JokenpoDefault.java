package br.com.jokenpo.mvc.controller;

import br.com.jokenpo.mvc.entity.Round;
import br.com.jokenpo.mvc.repository.RoundRepository;
import br.com.jokenpo.util.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class JokenpoDefault {
    private RoundRepository roundRepository;

    @Autowired
    public JokenpoDefault(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
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

    public long countByPlayerWin() {
        return roundRepository.countByPlayerWin(true);
    }

    public long countByComputerWin() {
        return roundRepository.countByComputerWin(true);
    }

    public void deleteAllRounds() {
        roundRepository.deleteAll();
    }
}
