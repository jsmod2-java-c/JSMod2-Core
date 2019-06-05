package net.noyark.scpslserver.jsmod2.event.environment;

import net.noyark.scpslserver.jsmod2.annotations.UseForServerInit;
import net.noyark.scpslserver.jsmod2.event.Event;
import net.noyark.scpslserver.jsmod2.utils.api.Generator;
/**
 * @author Kevinj
 * @author magiclu550
 */

public class GeneratorFinishEvent extends Event {
    private Generator generator;

    public GeneratorFinishEvent(Generator generator) {
        this.generator = generator;
    }

    public GeneratorFinishEvent(){

    }

    public Generator getGenerator() {
        return generator;
    }


    /** java-bean */
    @UseForServerInit
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }
}