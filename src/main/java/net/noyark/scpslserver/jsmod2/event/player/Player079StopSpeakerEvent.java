package net.noyark.scpslserver.jsmod2.event.player;

import net.noyark.scpslserver.jsmod2.entity.Player;
import net.noyark.scpslserver.jsmod2.utils.api.Room;

public class Player079StopSpeakerEvent extends PlayerEvent {
    private Room room;
    private boolean allow;

    public Room getRoom() {
        return room;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public Player079StopSpeakerEvent(Player player, Room room, boolean allow) {
        super(player);
        this.room = room;
        this.allow = allow;
    }
}
