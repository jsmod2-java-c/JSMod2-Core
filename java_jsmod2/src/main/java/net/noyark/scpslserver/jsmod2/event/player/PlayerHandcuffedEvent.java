package net.noyark.scpslserver.jsmod2.event.player;

import net.noyark.scpslserver.jsmod2.annotations.UseForServerInit;
import net.noyark.scpslserver.jsmod2.entity.Player;

/**
 * @author kevinj
 */

public class PlayerHandcuffedEvent extends PlayerEvent {
    private boolean Handcuffed;

    private Player Owner;

    public boolean isHandcuffed() {
        return Handcuffed;
    }

    public void setHandcuffed(boolean handcuffed) {
        Handcuffed = handcuffed;
    }

    public Player getOwner() {
        return Owner;
    }

    /** java-bean */
    @UseForServerInit
    public void setOwner(Player owner) {
        Owner = owner;
    }



    public PlayerHandcuffedEvent(Player player, boolean handcuffed, Player owner) {
        super(player);
        Handcuffed = handcuffed;
        Owner = owner;
    }

    public PlayerHandcuffedEvent(){

    }
}