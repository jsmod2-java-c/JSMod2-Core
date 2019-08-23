package cn.jsmod2.core.script.jni;

import cn.jsmod2.core.script.EmeraldScriptVM;
/**
 * jni script
 * 等完成具体功能再写(emerald文件夹)
 */
public class EmeraldScript {


    static {
        
    }

    /**
     * use c++
     * @param command
     * @return
     */
    public static native String parse(String command);

    public static String java_parse(String command){
        return EmeraldScriptVM.getVM().parse(command);
    }
}
