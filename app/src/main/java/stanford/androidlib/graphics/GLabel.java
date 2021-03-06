/*
 * @version 2016/02/15
 * - added resource ID based methods to support string resources and l10n
 */

package stanford.androidlib.graphics;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.StringRes;

/**
 * The <code>GLabel</code> class is a graphical object whose appearance
 * consists of a text string.
 */
public class GLabel extends GObject {
    /**
     * The serialization code for this class.  This value should be incremented
     * whenever you change the structure of this class in an incompatible way,
     * typically by adding a new instance variable.
     */
    static final long serialVersionUID = 1L;

    /**
     * The default font used to display strings.  You can change the font by invoking
     * the <a href="#setFont(Font)"><code>setFont</code></a> method.
     */
    public static final Typeface DEFAULT_FONT = Typeface.create("sansserif", 20);

    // private fields
    private String label;
    private Typeface labelFont;

    /**
     * Creates a new empty <code>GLabel</code> object with its top-left corner at (0, 0).
     *
     * @usage GLabel glabel = new GLabel();
     */
    public GLabel() {
        this("", 0, 0);
    }

    /**
     * Creates a new <code>GLabel</code> object at with its top-left corner (0, 0),
     * initialized to contain the specified string.
     *
     * @usage GLabel glabel = new GLabel(str);
     * @param str The initial contents of the <code>GLabel</code>
     */
    public GLabel(String str) {
        this(str, 0, 0);
    }

    /**
     * Creates a new <code>GLabel</code> object with its top-left corner at the specified position.
     *
     * @usage GLabel glabel = new GLabel(str, x, y);
     * @param str The initial contents of the <code>GLabel</code>
     * @param x The x-coordinate of the label origin
     * @param y The y-coordinate of the baseline for the label
     */
    public GLabel(String str, float x, float y) {
        paint.setStrokeWidth(0f);
        label = str;
        setFont(DEFAULT_FONT);
        setLocation(x, y);
    }

    /**
     * Creates a new <code>GLabel</code> object at with its top-left corner (0, 0),
     * initialized to contain the string with the specified resource ID.
     *
     * @usage GLabel glabel = new GLabel(id);
     * @param id The resource ID of the initial contents of the <code>GLabel</code>
     */
    public GLabel(@StringRes int id) {
        this(id, 0, 0);
    }

    /**
     * Creates a new <code>GLabel</code> object with its top-left corner at the specified position.
     *
     * @usage GLabel glabel = new GLabel(str, x, y);
     * @param id The ID of the resource string for the initial contents of the <code>GLabel</code>
     * @param x The x-coordinate of the label origin
     * @param y The y-coordinate of the baseline for the label
     */
    public GLabel(@StringRes int id, float x, float y) {
        paint.setStrokeWidth(0f);
        label = getStringFromId(id);
        setFont(DEFAULT_FONT);
        setLocation(x, y);
    }

    /**
     * Returns the font in which the <code>GLabel</code> is displayed.
     *
     * @usage Font font = glabel.getFont();
     * @return The font in use by this object
     */
    public Typeface getFont() {
        return labelFont;
    }

    /**
     * Returns this label's current font style.
     */
    public int getFontStyle() {
        return labelFont.getStyle();
    }

    /**
     * Returns the string displayed by this object.
     *
     * @usage String str = glabel.getLabel();
     * @return The string displayed by this object
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the string displayed by this object.
     *
     * @usage String str = glabel.getText();
     * @return The string displayed by this object
     */
    public String getText() {
        return label;
    }

    /**
     * Implements the <code>paint</code> operation for this graphical object.  This method
     * is not called directly by clients.
     * @noshow
     */
    public void paint(Canvas canvas) {
        // shift downward by height so that x/y coord passed represents top-left
        Paint paint = getPaint();
        canvas.drawText(this.label, getX(), getY() + getHeight(), paint);
    }

    /**
     * Returns the width of this string, as it appears on the display.
     *
     * @usage float width = glabel.getWidth();
     * @return The width of this object
     */
    @Override
    public float getWidth() {
        return getPaint().measureText(label);
    }

    /**
     * Returns the height of this string, as it appears on the display.
     *
     * @usage float height = glabel.getHeight();
     * @return The height of this string
     */
    @Override
    public float getHeight() {
        Rect bounds = new Rect();
        getPaint().getTextBounds(label, 0, label.length(), bounds);
        return bounds.height();
    }

    /**
     * Returns the font size currently used by this label.
     */
    public float getFontSize() {
        return getPaint().getTextSize();
    }

    /**
     * Sets this label to use the given font style,
     * such as TypeFace.BOLD.
     */
    public void setFontStyle(int style) {
        verifyFontStyle(style);
        getPaint().setTypeface(Typeface.create(this.labelFont, style));
        repaint();
    }

    /**
     * Changes the font used to display the <code>GLabel</code>.  This call will
     * usually change the size of the displayed object and will therefore affect
     * the result of calls to <a href="GObject.html#getSize()"><code>getSize</code></a>
     * and <a href="GObject.html#getBounds()"><code>getBounds</code></a>.
     *
     * @usage glabel.setFont(font);
     * @param font A <code>Font</code> object indicating the new font
     */
    public void setFont(Typeface font) {
        labelFont = font;
        getPaint().setTypeface(font);
        repaint();
    }

    /**
     * Sets this GLabel to use the given font at the given size.
     */
    public void setFont(Typeface font, float size) {
        labelFont = font;
        getPaint().setTypeface(font);
        setFontSize(size);
    }

    /**
     * Sets this GLabel to use the given font at the given style and size.
     */
    public void setFont(Typeface fontFamily, int style, float size) {
        verifyFontStyle(style);
        labelFont = Typeface.create(fontFamily, style);
        setFontSize(size);
    }

    // helper to make sure font style is not bogus
    private void verifyFontStyle(int style) {
        if (style != Typeface.NORMAL
                && style != Typeface.ITALIC
                && style != Typeface.BOLD
                && style != Typeface.BOLD_ITALIC) {
            throw new IllegalArgumentException("invalid font style (" + style + "); did you mix up the order of the style and size parameters?");
        }
    }

    /**
     * Changes the font used to display the <code>GLabel</code> as specified by
     * the string <code>str</code>.
     *
     * @usage glabel.setFont(str);
     * @param str A <code>String</code> specifying the new font
     */
    public void setFont(String str) {
        setFont(Typeface.create(str, 12));
    }

    /**
     * Sets this label to use a font of the given size.
     */
    public void setFontSize(float size) {
        getPaint().setTextSize(size);
        repaint();
    }

    /**
     * Changes the string stored within the <code>GLabel</code> object, so that
     * a new text string appears on the display.
     *
     * @usage glabel.setLabel(R.string.someID);
     * @param id The new string to display's resource ID
     */
    public void setLabel(@StringRes int id) {
        label = getStringFromId(id);
        repaint();
    }

    /**
     * Changes the string stored within the <code>GLabel</code> object, so that
     * a new text string appears on the display.
     *
     * @usage glabel.setLabel(str);
     * @param str The new string to display
     */
    public void setLabel(String str) {
        label = str;
        repaint();
    }

    /**
     * Changes the string stored within the <code>GLabel</code> object, so that
     * a new text string appears on the display.
     *
     * @usage glabel.setText(R.string.someID);
     * @param id The new string to display's ID
     */
    public void setText(@StringRes int id) {
        setLabel(id);
    }

    /**
     * Changes the string stored within the <code>GLabel</code> object, so that
     * a new text string appears on the display.
     *
     * @usage glabel.setText(str);
     * @param str The new string to display
     */
    public void setText(String str) {
        setLabel(str);
    }

    // helper to convert string resource ID into that string
    private String getStringFromId(@StringRes int id) {
        Context context = GCanvas.__getCanvasContext();
        return context.getResources().getString(id);
    }
}
