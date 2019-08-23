/*
Jsmod2 is a java-based scpsl cn.jsmod2.server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.jsmod2.core.protocol;

/**
 * 用于处理一个事件对象，只要将和事件对象相互匹配的其他语言事件对象对应
 * 就可以实现事件的调用
 *
 * @author magiclu550
 */

public class EventBinaryStream extends BinaryStream {


    public EventBinaryStream(){
        super(0xffff);
    }

    public <T> T encode(Class<T> event,byte[] bytes){
        return event.cast(dataObjectDecode(bytes,event));
    }

}
