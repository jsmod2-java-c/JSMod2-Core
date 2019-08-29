/*
Jsmod2 is a java-based scpsl cn.jsmod2.server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright JavaMultiModStarterAdmin China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2;

import cn.jsmod2.command.*;
import cn.jsmod2.api.event.admin.AdminQueryEvent;
import cn.jsmod2.api.event.admin.AuthCheckEvent;
import cn.jsmod2.api.event.admin.BanEvent;
import cn.jsmod2.api.event.config.SetConfigEvent;
import cn.jsmod2.api.event.environment.*;
import cn.jsmod2.api.event.player.*;
import cn.jsmod2.api.event.server.*;
import cn.jsmod2.api.event.team.DecideRespawnQueueEvent;
import cn.jsmod2.api.event.team.SetSCPConfigEvent;
import cn.jsmod2.core.FileSystem;
import cn.jsmod2.core.RegisterTemplate;
import cn.jsmod2.core.annotations.RegisterMethod;
import cn.jsmod2.core.protocol.CommandSendPacket;
import cn.jsmod2.network.DoGetStream;
import cn.jsmod2.network.DoStream;
import cn.jsmod2.network.ServerInitPacket;
import cn.jsmod2.core.protocol.DataPacket;
import cn.jsmod2.core.ex.*;
import cn.jsmod2.api.event.team.SetNTFUnitNameEvent;
import cn.jsmod2.api.event.team.TeamRespawnEvent;
import cn.jsmod2.core.protocol.CommandRegisterPacket;
import cn.jsmod2.core.protocol.command.PlayerCommandPacket;
import cn.jsmod2.core.protocol.command.ServerCommandPacket;
import cn.jsmod2.network.protocol.event.admin.*;
import cn.jsmod2.network.protocol.event.newstream.EventValueGetStream;
import cn.jsmod2.network.protocol.event.newstream.EventValueSetStream;
import cn.jsmod2.network.protocol.item.*;
import cn.jsmod2.network.protocol.map.door.*;
import cn.jsmod2.network.protocol.map.elevator.*;
import cn.jsmod2.network.protocol.map.generator.*;
import cn.jsmod2.network.protocol.server.*;

import java.util.*;

import static cn.jsmod2.core.Server.START;

/**
 * Jsmod2的默认注册机，用于注册语言,本地命令,启动信息,数据包class对象
 * 事件id,服务器配置文件等基本信息，会在服务端运行过程中进行调用
 * @author magiclu550
 * @since 1.0.0
 * @see cn.jsmod2.core.RegisterTemplate
 */

public class Register extends RegisterTemplate {


    static final int MAX_EVENT_ID = 0x52;

    static final int FIRST_EVENT = 0x01;

    static final int SECOND_START_EVENT = 0x03;

    static final int SERVER_COMMAND = 0x55;

    static final int PLAYER_COMMAND = 0x56;

    public static final String ABOUT = "jsmod2.about";



    /**
     * 注册语言文件，在选择语言时，使得语言可以在控制台显示
     * 注册语言时，首先按照标准格式添加语言
     * 参考resources格式(zh.properties等)
     */
    @RegisterMethod
    public void registerLang(){
        registerLang.add("zh");
        registerLang.add("en");
        registerLang.add("ru");
        registerLang.add("jp");
        registerLang.add("ar");
        registerLang.add("sp");
    }


    /**
     * 注册本地的命令，前提命令必须直接继承自NativeCommand
     * 注册名称必须和NativeCommand的名称一一对应，否则会发生
     * 及其诡异的错误
     */
    @RegisterMethod
    public void registerNativeCommand(){
        nativeCommandMap.put("stop",new StopCommand());
        nativeCommandMap.put("help",new HelpCommand());
        nativeCommandMap.put("plugins",new PluginsCommand());
        nativeCommandMap.put("reload",new ReloadCommand());
        nativeCommandMap.put("tab",new TabCommand());
        nativeCommandMap.put("see",new SeeCommand());
        nativeCommandMap.put("throw",new ThrowCommand());
        nativeCommandMap.put("tps",new TPSCommand());
        nativeCommandMap.put("unload",new UnloadPluginCommand());
        nativeCommandMap.put("load",new LoadPluginCommand());
        nativeCommandMap.put("about",new AboutCommand());
        nativeCommandMap.put("multi",new MultiCommand());
        nativeCommandMap.put("download",new DownloadPluginCommand());
        nativeCommandMap.put("register",new RegisterPanelCommand());
    }


    /**
     * 用于注册开启时的信息，这里的内容对应着语言文件的key值上,参见
     * resources可以知悉
     */
    @RegisterMethod
    public void registerStartInfo(){
        startInfo.add(START+".info");
        startInfo.add(START+".thanks");
        startInfo.add(START+".warn");
        startInfo.add(START+".connect");
    }

    /**
     * 注册数据包的信息，如果在定义数据包时不表明id信息，那么数据包就会从这个
     * 表里寻找它的id，如果依然找不到，则不发出id,但是proxyHandler会默认为命令
     * 的id，最终导致解析失败
     */
    @RegisterMethod
    public void registerPacket(){
        packets.put(ServerInitPacket.class,ServerInitPacket.ID);
        packets.put(CommandRegisterPacket.class,CommandRegisterPacket.ID);
        packets.put(ServerCommandPacket.class,SERVER_COMMAND);
        packets.put(PlayerCommandPacket.class,PLAYER_COMMAND);
        packets.put(DropItemPacket.class,DropItemPacket.ID);
        packets.put(RemoveItemPacket.class,RemoveItemPacket.ID);
        packets.put(SetItemInWorldPacket.class,SetItemInWorldPacket.ID);
        packets.put(SetItemKinematicPacket.class,SetItemKinematicPacket.ID);
        packets.put(SetItemPositionPacket.class,SetItemPositionPacket.ID);
        packets.put(GetComponentPacket.class,GetComponentPacket.ID);
        packets.put(GetItemKinematicPacket.class,GetItemKinematicPacket.ID);
        packets.put(GetPositionPacket.class,GetPositionPacket.ID);
        packets.put(GetPortPacket.class,GetPortPacket.ID);
        packets.put(GetPlayerPacket.class,GetPlayerPacket.ID);
        packets.put(GetIpAddressPacket.class,GetIpAddressPacket.ID);
        packets.put(GetNumPlayersPacket.class,GetNumPlayersPacket.ID);
        packets.put(GetRoundPacket.class,GetRoundPacket.ID);
        packets.put(GetMaxPlayersPacket.class,GetMaxPlayersPacket.ID);
        packets.put(AdminQueryAdminSetPacket.class,AdminQueryAdminSetPacket.ID);
        packets.put(SetMaxPlayersPacket.class,SetMaxPlayersPacket.ID);
        packets.put(GetItemInWorldPacket.class,GetItemInWorldPacket.ID);
        packets.put(GetItemTypePacket.class,GetItemTypePacket.ID);
        packets.put(AdminQueryQuerySetPacket.class,AdminQueryQuerySetPacket.ID);
        packets.put(AdminQueryQueryGetPacket.class,AdminQueryQueryGetPacket.ID);
        packets.put(CommandSendPacket.class,CommandSendPacket.ID);
        packets.put(GetDoorBlockAfterWarheadDetonationPacket.class,GetDoorBlockAfterWarheadDetonationPacket.ID);
        packets.put(GetDoorDestoryedPacket.class,GetDoorDestoryedPacket.ID);
        packets.put(GetDoorDontOpenOnWarheadPacket.class,GetDoorDontOpenOnWarheadPacket.ID);
        packets.put(GetDoorLockedPacket.class,GetDoorLockedPacket.ID);
        packets.put(GetDoorNamePacket.class,GetDoorNamePacket.ID);
        packets.put(GetDoorOpenPacket.class,GetDoorOpenPacket.ID);
        packets.put(GetDoorPermissionPacket.class,GetDoorPermissionPacket.ID);
        packets.put(GetDoorPositionPacket.class,GetDoorPositionPacket.ID);
        packets.put(SetDoorBlockAfterWarheadDetonationPacket.class,SetDoorBlockAfterWarheadDetonationPacket.ID);
        packets.put(SetDoorDestoryPacket.class,SetDoorDestoryPacket.ID);
        packets.put(SetDoorDontOpenOnWarheadPacket.class,SetDoorDontOpenOnWarheadPacket.ID);
        packets.put(SetDoorLockedPacket.class,SetDoorLockedPacket.ID);
        packets.put(SetDoorOpenPacket.class,SetDoorOpenPacket.ID);
        packets.put(GetElevatorLockedPacket.class,GetElevatorLockedPacket.ID);
        packets.put(GetElevatorLockablePacket.class,GetElevatorLockablePacket.ID);
        packets.put(GetElevatorMovingSpeedPacket.class,GetElevatorMovingSpeedPacket.ID);
        packets.put(GetElevatorPositionsPacket.class,GetElevatorPositionsPacket.ID);
        packets.put(GetElevatorStatusPacket.class,GetElevatorStatusPacket.ID);
        packets.put(GetElevatorTypePacket.class,GetElevatorTypePacket.ID);
        packets.put(SetElevatorLockablePacket.class,SetElevatorLockablePacket.ID);
        packets.put(SetElevatorLockedPacket.class,SetElevatorLockedPacket.ID);
        packets.put(SetElevatorMovingSpeedPacket.class,SetElevatorMovingSpeedPacket.ID);
        packets.put(UseElevatorPacket.class,UseElevatorPacket.ID);
        packets.put(GetGeneratorPositionPacket.class,GetGeneratorPositionPacket.ID);
        packets.put(GetGeneratorTimeLeftPacket.class,GetGeneratorTimeLeftPacket.ID);
        packets.put(GetGeneratorStartTimePacket.class,GetGeneratorStartTimePacket.ID);
        packets.put(GetGeneratorEngagedPacket.class,GetGeneratorEngagedPacket.ID);
        packets.put(GetGeneratorHasTabletPacket.class,GetGeneratorHasTabletPacket.ID);
        packets.put(GetGeneratorLockedPacket.class,GetGeneratorLockedPacket.ID);
        packets.put(GetGeneratorOpenPacket.class,GetGeneratorOpenPacket.ID);
        packets.put(SetGeneratorTimeLeftPacket.class,SetGeneratorTimeLeftPacket.ID);
        packets.put(SetGeneratorHasTabletPacket.class,SetGeneratorHasTabletPacket.ID);
        packets.put(SetGeneratorOpenPacket.class,SetGeneratorOpenPacket.ID);
        packets.put(EventValueGetStream.class,180);
        packets.put(EventValueSetStream.class,181);
        packets.put(PlayerContain106Scp106sGetPacket.class,PlayerContain106Scp106sGetPacket.ID);
        packets.put(PlayerSetRoleSetItemsPacket.class,PlayerSetRoleSetItemsPacket.ID);
        packets.put(TeamRespawnEventGetPlayersPacket.class,TeamRespawnEventGetPlayersPacket.ID);
        packets.put(TeamRespawnEventSetPlayerListPacket.class,TeamRespawnEventSetPlayerListPacket.ID);
        packets.put(DoStream.class,190);
        packets.put(DoGetStream.class,190);
        //11
        //~143 最大 下一个144
        putPackets();
    }

    /**
     * 监听multiAdmin的log文件时，指定是否启动的key值
     */
    public static final String CONSOLE_LOG = "multi-admin.log";

    /**
     * 远程连接ui的netty服务端端口，这里不建议使用,默认关闭
     */
    public static final String CLIENT_PORT = "client.port";

    /**
     * 运行jsmod2时的jvm参数，只有在启动启动器(rpc调用)时，才会生效
     */
    public static final String JSMOD2_JVM_ARGS = "jsmod2.option";




    /**
     * 当开启成功时的信息
     */
    @RegisterMethod
    public void registerSuccessInfo(){
        successInfo.add("start.finish");
    }

    /**
     * 服务端的配置文件:server.properties的信息，在使用时,必须调用父类的
     * 注册信息
     */
    @RegisterMethod
    public void registerServerProperties(){
        super.registerServerProperties();
        serverProperties.put(FileSystem.SMOD2_LOG_FILE,"");
        serverProperties.put(FileSystem.SMOD2_LOG_INTERVAL,"2000");
        serverProperties.put(DownloadPluginCommand.MIRROR,"");
        serverProperties.put(CONSOLE_LOG,"");
        serverProperties.put(CLIENT_PORT,"20020");
        serverProperties.put(JSMOD2_JVM_ARGS,"");
    }


    /**
     * 注册异常，必须是ServerRuntimeException的子类，在发生时，会找到value值，作为该
     * 异常的解决方案给用户参考
     */
    @RegisterMethod
    public void registerException(){
        ex_methods.put (TypeErrorException.class,"* your configuration file type may have some problems, please see your configuration file type*\n\t refer to cn.jsmod2.core.utils.cn.jsmod2.config.ConfigQueryer class\n\t or cn.jsmod2.configs.ConfigType class");
        ex_methods.put (ProtocolException.class,"* when transferring protocol, you did not transfer data in accordance with an accurate or correct jsmod2 * \n \t reference grammar ID - {main json}, field chain: {mapping JSON value} ~ tail request (optional, to mark subsidiary information, or ownership information");
        ex_methods.put (PluginException.class, "*There is a serious problem in initializing plug-in objects. Please check that the main class of your configuration file is filled in correctly*");
        ex_methods.put (NoSuchPluginNameException. class, "* when using the PluginManager. getPlugin method, there is no plug-in name caused by *");
        ex_methods.put (NoSuchPlayerException. class, "* can't find this object *when the PowerPool class of administrative permissions has problems, deletes or adds permissions");
        ex_methods.put (MainClassErrorException. class, "* main class name error, if there is no such class *");
        ex_methods.put (EventException. class, "* caused by a problem with the name of the cn.jsmod2.event method parameter, or there is no cn.jsmod2.event *");
    }

    /**
     * 格式:
     * put(事件id,事件名.class)
     * 事件id对应数据包id
     * 这里注册了全部的事件，在发送事件触发数据包时，会从这里寻找Event类对象，并
     * 创建类对象，但是构造器不得有参数
     */
    @RegisterMethod
    public void registerEvents(){
        events.put(0x01, AdminQueryEvent.class);//packet 1
        events.put(0x03, AuthCheckEvent.class);//packet 1
        events.put(0x04, BanEvent.class);//packet 1
        events.put(0x05, SetConfigEvent.class);//packet 1
        events.put(0x06, GeneratorFinishEvent.class);//p  \1
        events.put(0x07, LCZDecontaminateEvent.class);//p
        events.put(0x08, SCP914ActivateEvent.class);//p
        events.put(0x09, ScpDeathAnnouncementEvent.class);//p
        events.put(0x0a, SummonVehicleEvent.class);//p
        events.put(0x0b,WarheadChangeLeverEvent.class);//p 1
        events.put(0x0c,WarheadDetonateEvent.class);//p
        events.put(0x0d,WarheadKeycardAccessEvent.class);//p
        events.put(0x0e,WarheadStartEvent.class);//p
        events.put(0x0f, Player079AddExpEvent.class);//p
        events.put(0x10, Player079CameraTeleportEvent.class);//p
        events.put(0x11, Player079DoorEvent.class);
        events.put(0x12, Player079ElevatorTeleportEvent.class);
        events.put(0x13, Player079LevelUpEvent.class);
        events.put(0x14,Player079LockdownEvent.class);
        events.put(0x15,Player079LockEvent.class);
        events.put(0x16,Player079StartSpeakerEvent.class);
        events.put(0x17,Player079StopSpeakerEvent.class);
        events.put(0x18,Player079TeslaGateEvent.class);
        events.put(0x19,Player079UnlockDoorsEvent.class);
        events.put(0x1a,Player106CreatePortalEvent.class);
        events.put(0x1b,Player106TeleportEvent.class);
        events.put(0x1c,PlayerCallCommandEvent.class);
        events.put(0x1d,PlayerCheckEscapeEvent.class);
        events.put(0x1e,PlayerContain106Event.class);
        events.put(0x1f,PlayerDeathEvent.class);
        events.put(0x20,PlayerDropItemEvent.class);
        events.put(0x21,PlayerElevatorUseEvent.class);
        events.put(0x22,PlayerGeneratorAccessEvent.class);
        events.put(0x23,PlayerGeneratorEjectTabletEvent.class);
        events.put(0x24,PlayerGeneratorInsertTabletEvent.class);
        events.put(0x25,PlayerGeneratorUnlockEvent.class);
        events.put(0x26,PlayerGrenadeExplosion.class);
        events.put(0x27,PlayerGrenadeHitPlayer.class);
        events.put(0x28,PlayerHandcuffedEvent.class);
        events.put(0x29,PlayerHurtEvent.class);
        events.put(0x2a,PlayerInitialAssignTeamEvent.class);
        events.put(0x2b,PlayerIntercomCooldownCheckEvent.class);
        events.put(0x2c,PlayerIntercomEvent.class);
        events.put(0x2d,PlayerJoinEvent.class);// 1
        events.put(0x2e,PlayerLureEvent.class);
        events.put(0x2f,PlayerMakeNoiseEvent.class);
        events.put(0x30,PlayerMedkitUseEvent.class);
        events.put(0x31,PlayerPickupItemEvent.class);// 1
        events.put(0x32,PlayerPickupItemLateEvent.class);// 1
        events.put(0x33,PlayerPocketDimensionEnterEvent.class);
        events.put(0x34,PlayerPocketDimensionExitEvent.class);
        events.put(0x35,PlayerRadioSwitchEvent.class);
        events.put(0x36,PlayerRecallZombieEvent.class);
        events.put(0x37,PlayerReloadEvent.class);
        events.put(0x38,PlayerSCP914ChangeKnobEvent.class);
        events.put(0x39,PlayerSetRoleEvent.class);
        events.put(0x3a,PlayerShootEvent.class);
        events.put(0x3b,PlayerSpawnEvent.class);
        events.put(0x3c,PlayerSpawnRagdollEvent.class);
        events.put(0x3d,PlayerThrowGrenade.class);
        events.put(0x3e,PlayerTriggerTeslaEvent.class);
        events.put(0x3f,Scp096CooldownEndEvent.class);
        events.put(0x40,Scp096CooldownStartEvent.class);
        events.put(0x41,Scp096EnrageEvent.class);
        events.put(0x42,Scp096PanicEvent.class);
        events.put(0x43, ConnectEvent.class);
        events.put(0x44, DisconnectEvent.class);
        events.put(0x45, FixedEvent.class);
        events.put(0x46, LateDisconnectionEvent.class);
        events.put(0x47, LateUpEvent.class);
        events.put(0x48,RoundEndEvent.class);
        events.put(0x49,RoundReStartEvent.class);
        events.put(0x4a,RoundStartEvent.class);
        events.put(0x4b,SceneChangedEvent.class);
        events.put(0x4c,SetServerNameEvent.class);
        events.put(0x4d,UpdateEvent.class);
        events.put(0x4e,WaitingForPlayersEvent.class);
        events.put(0x4f, DecideRespawnQueueEvent.class);
        events.put(0x50, SetNTFUnitNameEvent.class);
        events.put(0x51, SetSCPConfigEvent.class);
        events.put(0x52, TeamRespawnEvent.class);
        events.put(301,CheckRoundEndEvent.class);
        events.put(302,PlayerInfectedEvent.class);
        events.put(303,PlayerDoorAccessEvent.class);
        events.put(304,PlayerNicknameSetEvent.class);
        events.put(305,WarheadStopEvent.class);
        //82 events
    }


    /**
     * 将数据包信息反向的放入表里，以用于调用
     */
    private void putPackets(){
        Set<Map.Entry<Class<? extends DataPacket>,Integer>> dataPackets = packets.entrySet();
        for(Map.Entry<Class<? extends DataPacket>,Integer> packet:dataPackets){
            getPackets.put(packet.getValue(),packet.getKey());
        }
    }

}
