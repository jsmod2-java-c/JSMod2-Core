/*
Jsmod2 is a java-based scpsl server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.api.event.environment;

import cn.jsmod2.core.interapi.event.IEvent;

/**
 * @author Kevinj
 * @author magiclu550
 */

public interface ISummonVehicleEvent extends IEvent {

    boolean isCI();

    void setCI(boolean CI);

    boolean isAllowSummon();

    void setAllowSummon(boolean allowSummon);


}
