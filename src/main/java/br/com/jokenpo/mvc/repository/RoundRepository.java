package br.com.jokenpo.mvc.repository;

import br.com.jokenpo.mvc.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    Long countByPlayerWin(boolean playerWin);
    Long countByComputerWin(boolean computerWin);
}
