package net.noyark.scpslserver.jsmod2.event.player;

import net.noyark.scpslserver.jsmod2.entity.Player;
import net.noyark.scpslserver.jsmod2.entity.TeslaGate;


/**
 * @author kevinj
 */

public class Player079TeslaGateEvent extends PlayerEvent {
    private TeslaGate teslaGate;
    private boolean allow;
    private float apDrain;

    public Player079TeslaGateEvent(Player player, TeslaGate teslaGate, boolean allow, float apDrain) {
        super(player);
        this.teslaGate = teslaGate;
        this.allow = allow;
        this.apDrain = apDrain;
    }

    public Player079TeslaGateEvent(){

    }

    public TeslaGate getTeslaGate() {
        return teslaGate;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public float getApDrain() {
        return apDrain;
    }

    public void setApDrain(float apDrain) {
        this.apDrain = apDrain;
    }

    public void setTeslaGate(TeslaGate teslaGate) {
        this.teslaGate = teslaGate;
    }
}