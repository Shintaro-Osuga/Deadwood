import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;

public class Listener extends JFrame implements ChangeListener{

    static JFrame frame;

    static JSlider slider;

    static JLabel label;

    public Listener(){
        frame = new JFrame("frame");

        Listener listen = this;

        label = new JLabel();

        JPanel panel = new JPanel();

        slider = new JSlider(2,8,2);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);

        panel.add(slider);
        panel.add(label);

        frame.add(panel);

        label.setText("-- Player Count --");

        frame.setSize(300,300);

        frame.show();
    }


    public int getPlayerCount()
    {
        return slider.getValue();
    }


    public void stateChanged(ChangeEvent e)
    {
        label.setText("value of Slider is =" + slider.getValue());
    }
    
}
