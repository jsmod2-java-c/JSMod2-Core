package cn.jsmod2.core.script;

import cn.jsmod2.core.log.ServerLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 编译环境的配置
 */
public class EnvPage {

    public static boolean isWrite;

    public static void loadConf(String folder,String defaultRoot){
        try{
            File file = new File(folder+"/env.conf");
            if(!file.exists())
                file.createNewFile();
            File defaultRootFile = new File(defaultRoot);
            if(!defaultRootFile.exists())
                defaultRootFile.mkdirs();
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            String rootEnv = properties.getProperty("root-make",defaultRoot);
            isWrite = Boolean.parseBoolean(properties.getProperty("console-write","true"));
            EmeraldScriptVM.getVM().getVars().put("ENV_FILE",Var.compile("ENV_FILE="+rootEnv));
            ServerLogger.getLogger().multiInfo(EmeraldScriptVM.class,"the script env is"+rootEnv,"","");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean isWrite(){
        return isWrite;
    }

}
