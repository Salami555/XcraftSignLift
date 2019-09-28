package de.groovybyte.spigot.xcraftsignelevator;

import com.google.common.collect.Range;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.groovybyte.spigot.xcraftsignelevator.SignUtils.isSign;

class ElevatorFactory implements Listener {

    private static final int ELEVATOR_SIGN_LINE_INDEX = 1;
    private static final Pattern ELEVATOR_REGEX = Pattern.compile(
        "^\\[lift (up|down)]$",
        Pattern.CASE_INSENSITIVE
    );
    private static final Range<Integer> VALID_SIGN_LOCATION_RANGE = Range.closed(1, 256);

    Optional<Elevator> findElevator(Sign sign) throws IllegalArgumentException {
        if (isElevator(sign)) {
            ElevatorDirection direction = ElevatorDirection.getFromSign(sign);
            return Optional.of(
                new Elevator(sign, findMatchingSign(sign, direction))
            );
        } else {
            return Optional.empty();
        }
    }

    private Sign findMatchingSign(Sign start, ElevatorDirection direction)
        throws IllegalArgumentException {
        Block block = start.getBlock();

        do {
            block = block.getRelative(direction.face);
            if (!VALID_SIGN_LOCATION_RANGE.contains(block.getY())) {
                throw new IllegalArgumentException(Messages.MISSING_MATCHING_ELEVATOR_SIGN);
            }
        } while (!isMatchingElevator(block, direction));

        return (Sign) block.getState();
    }

    private boolean isMatchingElevator(Block block, ElevatorDirection direction) {
        return isElevator(block)
            && direction.getOpposite() == ElevatorDirection.getFromSign((Sign) block.getState());
    }

    private boolean isElevator(Block block) {
        if (isSign(block)) {
            Sign sign = (Sign) block.getState();
            return isElevator(sign);
        } else {
            return false;
        }
    }

    private boolean isElevator(Sign sign) {
        return ELEVATOR_REGEX.matcher(sign.getLine(1)).matches();
    }

    enum ElevatorDirection {
        UP(1, BlockFace.UP),
        DOWN(-1, BlockFace.DOWN);

        public static Optional<ElevatorDirection> tryGetFromSignLine(String line) {
            Matcher matcher = ELEVATOR_REGEX.matcher(line);
            if (!matcher.find()) {
                return Optional.empty();
            }
            String direction = matcher.group(1);
            return Arrays.stream(ElevatorDirection.values())
                .filter(ed -> ed.name().equalsIgnoreCase(direction))
                .findFirst();
        }

        public static ElevatorDirection getFromSignLine(String line) throws IllegalArgumentException {
            return tryGetFromSignLine(line)
                .orElseThrow(() -> new IllegalArgumentException("Unknown elevator direction"));
        }

        public static ElevatorDirection getFromSign(Sign sign) throws IllegalArgumentException {
            return getFromSignLine(sign.getLine(ELEVATOR_SIGN_LINE_INDEX));
        }

        public final int ySign;
        public final BlockFace face;

        ElevatorDirection(int ySign, BlockFace face) {
            this.ySign = ySign;
            this.face = face;
        }

        ElevatorDirection getOpposite() {
            return this == UP ? DOWN : UP;
        }
    }
}
