package cn.jsmod2.network.protocol.item;

import cn.jsmod2.network.protocol.SetPacket;

public abstract class SetItemPacket extends SetPacket {

    public SetItemPacket(int id) {
        super(id);
    }

    public String playerName;

}