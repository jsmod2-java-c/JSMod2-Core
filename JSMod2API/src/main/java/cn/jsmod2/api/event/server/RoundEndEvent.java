/*
Jsmod2 is a java-based scpsl server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.api.event.server;

import cn.jsmod2.api.server.ROUND_END_STATUS;
import cn.jsmod2.api.server.Round;
import cn.jsmod2.api.server.Smod2Server;
import cn.jsmod2.core.annotations.UseForServerInit;


public class RoundEndEvent extends ServerEvent implements IRoundEndEvent{

    private Round round = new Round();

    private ROUND_END_STATUS status;


    public RoundEndEvent(Round round, ROUND_END_STATUS status, Smod2Server smod2Server){
        super(smod2Server);
    }

    public Round getRound() {
        return round;
    }


    public ROUND_END_STATUS getStatus() {
        return status;
    }

    public RoundEndEvent(){

    }
    /** java-bean */
    @UseForServerInit
    public void setStatus(ROUND_END_STATUS status) {
        this.status = status;
    }
}
