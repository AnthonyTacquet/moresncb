package be.antwaan.moresncb.logica.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class HourPicker extends NumberPicker {

    public HourPicker(Context context) {
        super(context);
    }

    public HourPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public HourPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }
    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 23));
    }
}