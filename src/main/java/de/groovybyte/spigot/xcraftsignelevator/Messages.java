package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.ChatColor;

public class Messages {
    private static final String PERMISSION_DENIED = ChatColor.RED + "Du hast keine Rechte";
    static final String PERMISSION_DENIED_USE = PERMISSION_DENIED + ", Lifte zu benutzen.";
    static final String PERMISSION_DENIED_CREATE = PERMISSION_DENIED + ", Lifte zu erstellen.";

    static final String MISSING_MATCHING_ELEVATOR_SIGN = "Es existiert kein zugehöriger Lift!";
    static final String MISSING_GROUND = "Es gibt keinen Block, auf dem du stehen könntest!";
    static final String MISSING_FREE_SPACE = "Es gibt keinen freien Platz!";
}
