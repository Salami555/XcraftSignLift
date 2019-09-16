package de.groovybyte.spigot.xcraftsignelevator;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class XcraftSignElevatorPlugin extends JavaPlugin implements ChatOutput {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(
            new SignClickEventListener(),
            this
        );
    }

    private BaseComponent[] chatPrefix = TextComponent.fromLegacyText(
        ChatColor.DARK_GRAY + "[" + this.getDescription().getName() + "] "
    );

    @Override
    public void message(CommandSender sender, String message) {
        sender.spigot().sendMessage(
            new ComponentBuilder("")
                .append(chatPrefix)
                .color(ChatColor.DARK_AQUA.asBungee())
                .appendLegacy(message).create()
        );
    }
}
