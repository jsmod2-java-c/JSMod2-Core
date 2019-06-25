package cn.jsmod2.core;


import cn.jsmod2.core.annotations.RegisterMethod;
import cn.jsmod2.core.command.NativeCommand;
import cn.jsmod2.core.event.Event;
import cn.jsmod2.core.protocol.DataPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定义了一个注册类的模板，在Server子类注册
 *
 * @author magiclu550
 */
public abstract class RegisterTemplate {

    public List<String> getRegisterLang(){
        return registerLang;
    }

    public Map<String, NativeCommand> getNativeCommandMap(){
        return nativeCommandMap;
    }

    public List<String> getStartInfo() {
        return startInfo;
    }


    public Map<Class<? extends Exception>, String> getEx_methods() {
        return ex_methods;
    }

    public List<String> getSuccessInfo() {
        return successInfo;
    }

    public Map<Class<? extends DataPacket>,Integer> getPackets(){
        return packets;
    }

    public Map<Integer, Class<? extends Event>> getEvents() {
        return events;
    }


    public Map<String, String> getServerProperties() {
        return serverProperties;
    }


    protected List<String> registerLang = new ArrayList<>();

    protected Map<String, NativeCommand> nativeCommandMap = new HashMap<>();

    protected List<String> startInfo = new ArrayList<>();

    protected List<String> successInfo = new ArrayList<>();

    protected Map<Class<? extends DataPacket>,Integer> packets = new HashMap<>();

    protected Map<String,String> serverProperties = new HashMap<>();

    protected Map<Integer, Class<? extends Event>> events = new HashMap<>();

    protected Map<Integer,Class<? extends DataPacket>> getPackets = new HashMap<>();

    protected Map<Class<? extends Exception>,String> ex_methods = new HashMap<>();

    public Map<Integer, Class<? extends DataPacket>> getGetPackets() {
        return getPackets;
    }

    public void setGetPackets(Map<Integer, Class<? extends DataPacket>> getPackets) {
        this.getPackets = getPackets;
    }

    @RegisterMethod
    public abstract void registerLang();
    @RegisterMethod
    public abstract void registerNativeCommand();
    @RegisterMethod
    public abstract void registerStartInfo();
    @RegisterMethod
    public abstract void registerPacket();
    @RegisterMethod
    public abstract void registerSuccessInfo();
    @RegisterMethod
    public abstract void registerServerProperties();
    @RegisterMethod
    public abstract void registerException();
    @RegisterMethod
    public abstract void registerEvents();
}