/*
Jsmod2 is a java-based scpsl server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.api.event.team;

import cn.jsmod2.api.team.Role;
import cn.jsmod2.core.annotations.UseForServerInit;
import cn.jsmod2.core.event.Event;
import cn.jsmod2.network.PacketSender;

public class SetRoleMaxHPEvent extends Event implements ISetRoleMaxHPEvent{

    private Role role;

    private int maxHP;

    public SetRoleMaxHPEvent(Role role, int maxHP) {
        this.role = role;
        this.maxHP = maxHP;
    }

    public SetRoleMaxHPEvent() {

    }

    public Role getRole() {
        role = PacketSender.sendEventGetPacket(playerName,"Role",Role.class);
        return role;
    }


    public int getMaxHP() {
        maxHP = PacketSender.sendEventGetPacket(playerName,"MaxHP",Integer.class);
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        PacketSender.sendEventSetPacket(playerName,"MaxHP",maxHP);
        this.maxHP = maxHP;
    }
}
