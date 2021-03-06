package menu;

import java.awt.BorderLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * A component for choosing a stroke from a list of available strokes.  This
 * class needs work.
 */
public class StrokeChooserPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** A combo for selecting the stroke. */
    private JComboBox selector;

    /**
     * Creates a panel containing a combo-box that allows the user to select
     * one stroke from a list of available strokes.
     *
     * @param current  the current stroke sample.
     * @param available  an array of 'available' stroke samples.
     */
    public StrokeChooserPanel(StrokeSample current, StrokeSample[] available) {
        setLayout(new BorderLayout());
        // we've changed the behaviour here to populate the combo box
        // with Stroke objects directly - ideally we'd change the signature
        // of the constructor too...maybe later.
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 0; i < available.length; i++) {
            model.addElement(available[i].getStroke());
        }
        this.selector = new JComboBox(model);
        this.selector.setSelectedItem(current.getStroke());
        this.selector.setRenderer(new StrokeSample(null));
        add(this.selector);
        // Changes due to focus problems!! DZ
        this.selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                getSelector().transferFocus();
            }
        });
    }


    /**
     * Returns the selector component.
     *
     * @return Returns the selector.
     */
    protected final JComboBox getSelector() {
        return this.selector;
    }

    /**
     * Returns the selected stroke.
     *
     * @return The selected stroke (possibly {@code null}).
     */
    public Stroke getSelectedStroke() {
        return (Stroke) this.selector.getSelectedItem();
    }

}