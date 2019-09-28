package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import static de.groovybyte.spigot.xcraftsignelevator.SignUtils.isSign;

import java.util.Optional;

class SignClickEventListener implements Listener {

    private final ElevatorFactory elevatorFactory;
    private final ChatOutput output;

    SignClickEventListener(ElevatorFactory elevatorFactory, ChatOutput output) {
        this.elevatorFactory = elevatorFactory;
        this.output = output;
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
        try {
            Optional<Elevator> elevator = elevatorFactory.findElevator(sign);
            if(elevator.isPresent()) {
                elevator.get().takeRide(player);
                return true;
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            output.message(player, ex.getMessage());
        }
        return false;
    }
}
