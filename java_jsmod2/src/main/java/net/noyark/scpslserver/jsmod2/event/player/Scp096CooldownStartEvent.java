package net.noyark.scpslserver.jsmod2.event.player;

import net.noyark.scpslserver.jsmod2.entity.Player;
/**
 * @author kevinj
 */
public class Scp096CooldownStartEvent extends PlayerEvent {
    private boolean allow;

    public Scp096CooldownStartEvent(Player player, boolean allow) {
        super(player);
        this.allow = allow;
    }

    public Scp096CooldownStartEvent(){

    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }


}