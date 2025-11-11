package Exception;

import java.text.MessageFormat;

public class UnitPositionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Invalid unit position: {0}. Allowed values: 1-4.";

    public UnitPositionException(String message) {
        super(message);
    }

    public UnitPositionException(int position) {
        super(MessageFormat.format(MESSAGE_TEMPLATE, position));
    }

    public static void throwIfInvalidPosition(int position) {
        if (position < 1 || position > 4) {
            throw new UnitPositionException(position);
        }
    }
}
