package trackit;

import java.sql.*;
import java.util.Locale;

/**
 * Handles foreign key exceptions that are found prior to database calls.
 */
public class ForeignKeyException
        extends SQLException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private static final String MSG_TEMPLATE_GENERIC = "This object is being used by another object.  Removal is not possible at this time.";
    private static final String MSG_TEMPLATE_SPECIFIC = "%s is being used by %s \"%s\" (and possibly others as well)."
            + "  It can not be removed until first removed from those %ss.";
    private static final String MSG_TEMPLATE_ITEM = "\nRecommend reducing item to zero quantity intead.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     * Use when a generic message is appropriate.
     */
    public ForeignKeyException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    /**
     * Use when specific objects should be mentioned.
     *
     * @param rowObj A descriptor for the row selected to be removed.
     * @param parentObjType The type of object that has a foreign key reference
     * to rowObj.
     * @param parentObj A descriptor for the actual object that has a foreign
     * key reference to rowObj.
     * @param isItem True = rowObj is an (inventory or order) item; False =
     * rowObj is not an item.
     */
    public ForeignKeyException(String rowObj, String parentObjType, String parentObj, boolean isItem) {
        this.errorMessage = String.format(MSG_TEMPLATE_SPECIFIC,
                rowObj, parentObjType.toLowerCase(Locale.US),
                parentObj, parentObjType.toLowerCase(Locale.US))
                + (isItem ? MSG_TEMPLATE_ITEM : "");
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
