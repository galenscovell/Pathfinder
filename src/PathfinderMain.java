import ui.Mainframe;
import util.Constants;

import javax.swing.SwingUtilities;


public class PathfinderMain {

    public static void main(String[] args) {
        Mainframe mainFrame = new Mainframe(Constants.SCREEN_X, Constants.SCREEN_Y);
        SwingUtilities.invokeLater(mainFrame);
    }
}
