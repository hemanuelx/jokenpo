package br.com.jokenpo.mvc.entity;

import javax.persistence.*;

@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private boolean playerWin;

    @Column
    private boolean computerWin;

    public Round(){
    }

    public Round(boolean playerWin, boolean computerWin) {
        this.playerWin = playerWin;
        this.computerWin = computerWin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    public boolean isComputerWin() {
        return computerWin;
    }

    public void setComputerWin(boolean computerWin) {
        this.computerWin = computerWin;
    }
}
