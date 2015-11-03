/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genelit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Barry Young
 */
public class GeneLit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String osn = System.getProperty("os.name").toLowerCase();
            System.out.println("Sys: " + osn);
            if (osn.contains("win")) {
                System.out.println("Win");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                System.out.println("Not win");
                UIManager.setLookAndFeel("Nimbus");
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
        GeneLitJFrame.main(args);
    }

}
