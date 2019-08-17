package cn.jsmod2.network.protocol.map.map;

import cn.jsmod2.api.map.Room;
import cn.jsmod2.api.map.Scp079InteractionType;


public class Get079InteractionRoomsPacket extends GetMapPacket {

    public static final int ID = 151;

    public Scp079InteractionType type;

    public Get079InteractionRoomsPacket() {
        super(ID, Room.class);
    }

    @Override
    public Room[] send() {
        return (Room[]) requester.with("type",type).get().getProtocolArray(false).toArray();
    }
}