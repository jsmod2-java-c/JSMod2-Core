package net.noyark.scpslserver.jsmod2.utils.item;

public enum ItemType {

    NULL(-1),
    JANITOR_KEYCARD(0),
    SCIENTIST_KEYCARD(1),
    MAJOR_SCIENTIST_KEYCARD(2),
    ZONE_MANAGER_KEYCARD(3),
    GUARD_KEYCARD(4),
    SENIOR_GUARD_KEYCARD(5),
    CONTAINMENT_ENGINEER_KEYCARD(6),
    MTF_LIEUTENANT_KEYCARD(7),
    MTF_COMMANDER_KEYCARD(8),
    FACILITY_MANAGER_KEYCARD(9),
    CHAOS_INSURGENCY_DEVICE(10),
    O5_LEVEL_KEYCARD(11),
    RADIO(12),
    COM15(13),
    MEDKIT(14),
    FLASHLIGHT(15),
    MICROHID(16),
    COIN(17),
    CUP(18),
    WEAPON_MANAGER_TABLET(19),
    E11_STANDARD_RIFLE(20),
    P90 (21),
    DROPPED_5 (22),
    MP4 (23),
    LOGICER (24),
    FRAG_GRENADE (25),
    FLASHBANG (26),
    DISARMER (27),
    DROPPED_7 (28),
    DROPPED_9(29),
    USP(30);

    private int type;

    ItemType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}