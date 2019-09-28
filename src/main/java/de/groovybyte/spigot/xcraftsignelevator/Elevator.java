package de.groovybyte.spigot.xcraftsignelevator;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

class Elevator {

    private final Sign startSign, targetSign;

    public Elevator(Sign startSign, Sign targetSign) {
        this.startSign = startSign;
        this.targetSign = targetSign;
    }

    public Sign getStartSign() {
        return startSign;
    }

    public Location getStartLocation() {
        return startSign.getLocation();
    }

    public Sign getTargetSign() {
        return targetSign;
    }

    public Location getTargetLocation() {
        return targetSign.getLocation();
    }

    public void takeRide(Player player) throws IllegalStateException {
        if (player.hasPermission("XcraftSignLift.use")) {
            Location targetLocation = findTargetLocation(player.getLocation());
            player.teleport(targetLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else {
            throw new IllegalStateException(Messages.PERMISSION_DENIED_USE);
        }

    }

    private Location findTargetLocation(Location startLocation) throws IllegalStateException {
        throw new IllegalStateException(Messages.MISSING_FREE_SPACE);
//        throw new IllegalStateException(Messages.MISSING_GROUND);
    }
}
