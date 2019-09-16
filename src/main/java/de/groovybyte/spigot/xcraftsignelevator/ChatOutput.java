package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.command.CommandSender;

public interface ChatOutput {
    void message(CommandSender sender, String message);
}
