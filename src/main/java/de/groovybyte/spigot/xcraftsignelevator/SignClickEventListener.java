package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Logger;

class SignClickEventListener implements Listener {

    private final Elevator elevator;

    SignClickEventListener(Elevator elevator) {
        this.elevator = elevator;
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            assert block != null;
            if(isSign(block)) {
                boolean handled = onPlayerClickedSign(
                    event.getPlayer(),
                    (Sign) block.getState()
                );
                event.setCancelled(handled);
            }
        }
    }

    private boolean onPlayerClickedSign(Player player, Sign sign) {
        if(elevator.isElevator(sign)) {
            return elevator.onElevatorClicked(player, sign);
        }
        return false;
    }

    private boolean isSign(Block block) {
        return block.getState() instanceof Sign;
    }
}
