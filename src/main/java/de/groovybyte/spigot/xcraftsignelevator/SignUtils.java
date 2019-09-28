package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignUtils {

    public static boolean isSign(Block block) {
        return block.getState() instanceof Sign;
    }
}
