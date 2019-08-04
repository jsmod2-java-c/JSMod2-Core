/*
Jsmod2 is a java-based scpsl server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.api.event.player;

import cn.jsmod2.api.player.Player;
import cn.jsmod2.api.team.Role;

/**
 * @author kevinj
 */

public class PlayerCheckEscapeEvent extends PlayerEvent implements IPlayerCheckEscapeEvent{
    private boolean allowEscape;
    private Role changeRole;

    public PlayerCheckEscapeEvent(Player player) {
        super(player);
    }

    public PlayerCheckEscapeEvent(){

    }

    public boolean isAllowEscape() {
        return allowEscape;
    }

    public void setAllowEscape(boolean allowEscape) {
        this.allowEscape = allowEscape;
    }

    public Role getChangeRole() {
        return changeRole;
    }

    public void setChangeRole(Role changeRole) {
        this.changeRole = changeRole;
    }
}
