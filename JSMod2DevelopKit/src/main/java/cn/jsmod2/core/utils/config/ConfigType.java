/*
Jsmod2 is a java-based scpsl server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.core.utils.config;

/**
 * 标记配置文件的属性类型
 * 在配置文件工厂可以使用
 * @author magiclu550
 */
public enum ConfigType {

    JSON(0),

    YAML(1<<1),

    OAML(1<<2),

    PROPERTIES(1<<2+1);

    private int type;

    ConfigType(int type){
        this.type = type;
    }

   private int getType(){
        return type;
   }

    public void setType(int type) {
        this.type = type;
    }
}
