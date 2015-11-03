/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genelit;

import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.URI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Barry Young
 */
public class GeneLitJFrame extends javax.swing.JFrame {

    public HashMap<String, ArrayList<Integer>> geneMap;
    public HashMap<Integer, String> pubs;
    public HashMap<String, String> orfList;
    public Properties prefs;
    public Long SGD_Lit_date;
    public HashMap<String, ArrayList<String>> geneLists;

    /**
     * Creates new form GeneLitJFrame
     */
    public GeneLitJFrame() {
        initComponents();
        jList1.setModel(new DefaultListModel());
        jList1.setCellRenderer(new myCellRenderer());

        geneMap = new HashMap<String, ArrayList<Integer>>();
        pubs = new HashMap<Integer, String>();
        orfList = new HashMap<String, String>();
        prefs = new Properties();
        geneLists = new HashMap<String, ArrayList<String>>();

        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(new File("GeneLit.xml")));
            try {
                prefs.loadFromXML(in);
            } catch (IOException ex) {
                Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        SGD_Lit_date = Long.parseLong((prefs.getProperty("SGD_Lit_date", "0")));

        jComboBox1.removeAllItems();

        for (String p : prefs.stringPropertyNames()) {
            if (p.startsWith("GeneList")) {
                String pg = prefs.getProperty(p);
                ArrayList<String> genes = new ArrayList<String>();
                genes.addAll(Arrays.asList(pg.split(";")));
                geneLists.put(p.substring(9), genes);
                jComboBox1.addItem(p.substring(9));
                System.out.println(geneLists.toString());
            }
        }

        if (jComboBox1.getItemCount() == 0) {
            jComboBox1.addItem("Default");
        }

        try {
            URL u = new URL("http://downloads.yeastgenome.org/curation/literature/gene_literature.tab");
            try {
                HttpURLConnection huc = (HttpURLConnection) u.openConnection();
                long date = huc.getLastModified();
                if (date == 0) {
                    System.out.println("No last-modified information.");
                } else {
                    System.out.println("Last-Modified: " + new Date(date));
                }

                if (date > SGD_Lit_date) {
                    System.out.println("File needs to be updated");
                    final String filename = "gene_literature.tab";

                    in = new BufferedInputStream(u.openStream());

                    byte b[] = new byte[16384];

                    FileOutputStream fos = new FileOutputStream(filename);
                    int bytesread;
                    while ((bytesread = in.read(b)) > -1) {
                        fos.write(b, 0, bytesread);

                        b = new byte[16384];
                    }

                    in.close();
                    fos.flush();
                    fos.close();

                    SGD_Lit_date = date;
                    prefs.setProperty("SGD_Lit_date", SGD_Lit_date.toString());
                    savePrefs();
                }

            } catch (IOException ex) {
                Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        File f = new File("gene_literature.tab");
        if (f.exists()) {
            try {
                BufferedReader bin = new BufferedReader(new FileReader(f));
                String t;
                while ((t = bin.readLine()) != null) {
                    String[] sg = t.split("\t");
                    if (!sg[0].isEmpty() && sg.length > 3) {
                        String orf = sg[3];
                        String spub = sg[0];
                        Integer ipub = Integer.parseInt(spub);
                        ArrayList<Integer> pubs2 = new ArrayList<Integer>();
                        if (!geneMap.keySet().contains(orf)) {
                            pubs2.add(ipub);
                        } else {
                            pubs2 = geneMap.get(orf);
                            pubs2.add(ipub);
                        }
                        geneMap.put(orf, pubs2);

                        if (!pubs.keySet().contains(ipub)) {
                            pubs.put(ipub, sg[1]);
                        }

                    }

                }
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        f = new File("SGD_features.tab");
        if (f.exists()) {
            try {
                BufferedReader bin = new BufferedReader(new FileReader(f));
                String t;
                while ((t = bin.readLine()) != null) {
                    String[] sg = t.split("\t");
                    if (sg.length > 4 && !sg[3].isEmpty()) {
                        orfList.put(sg[3], sg[4]);
                    }

                }
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Gene List:");

        jButton1.setText("New");

        jButton4.setText("Delete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class myCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String s;

            if (orfList.keySet().contains(value.toString()) && !orfList.get(value.toString()).isEmpty()) {
                s = orfList.get(value.toString()) + " (" + value.toString() + ")";
            } else {
                s = value.toString();
            }

            Component c = super.getListCellRendererComponent(list, s, index, isSelected, cellHasFocus);

            return c;
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String s = jTextField1.getText();
        DefaultListModel slm = (DefaultListModel) jList1.getModel();

        s = s.toUpperCase();
        String glist = jComboBox1.getSelectedItem().toString();
        String myOrf = "";

        if (orfList.keySet().contains(s)) {
            myOrf = s;
            slm.addElement(s);
        } else {
            for (String orf : orfList.keySet()) {
                if (orfList.get(orf).equals(s)) {
                    myOrf = orf;
                    slm.addElement(orf);
                }
            }
        }

        if (!myOrf.isEmpty()) {
            ArrayList<String> genes = new ArrayList<String>();
            if (geneLists.keySet().contains(glist)) {
                genes = geneLists.get(glist);
                genes.add(myOrf);
            } else {
                genes.add(myOrf);
            }
            geneLists.put(glist, genes);

            StringBuilder sb = new StringBuilder();
            for (String ss : genes) {
                sb.append(ss).append(";");
            }

            prefs.setProperty("GeneList_" + glist, sb.toString());
            savePrefs();

        }

        System.out.println(geneLists.toString());

        jList1.setModel(slm);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultListModel slm = (DefaultListModel) jList1.getModel();
        List vals = jList1.getSelectedValuesList();
        for (Object o : vals) {
            slm.removeElement(o);
        }
        jList1.setModel(slm);
        jList1.setCellRenderer(new myCellRenderer());

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

        int[] sels = jList1.getSelectedIndices();
        DefaultListModel dlm = (DefaultListModel) jList1.getModel();

        int cnt = 0;
        int j = 0;

        for (int i : sels) {
            String s = dlm.getElementAt(i).toString();
            j += geneMap.get(s).size();
        }

        Object[][] tabdata = new Object[j][3];

        for (int i : sels) {

            String s = dlm.getElementAt(i).toString();

            ArrayList<Integer> pubs2 = geneMap.get(s);

            for (Integer ii : pubs2) {
                tabdata[cnt][0] = s;
                tabdata[cnt][1] = ii;
                tabdata[cnt][2] = pubs.get(ii);
                cnt++;
            }
        }

        String[] colNames = {"Gene", "ID", "Title"};
        DefaultTableModel tab = new DefaultTableModel(tabdata, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int c) {
                if (c == 1) {
                    return Integer.class;
                } else {
                    return String.class;
                }
            }

        };

        jTable1.setModel(tab);
        jTable1.setRowSorter(new TableRowSorter(tab));

    }//GEN-LAST:event_jList1ValueChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            String pmid = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
            try {
                Desktop.getDesktop().browse(URI.create("http://www.ncbi.nlm.nih.gov/pubmed/" + pmid));
            } catch (IOException ex) {
                Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getItemCount() > 0) {
            String myList = jComboBox1.getSelectedItem().toString();
            DefaultListModel dlm = new DefaultListModel();

            if (geneLists.keySet().contains(myList)) {
                for (String myGene : geneLists.get(myList)) {
                    dlm.addElement(myGene);
                }
                jList1.setModel(dlm);
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GeneLitJFrame().setVisible(true);
            }
        });

    }

    public void savePrefs() {
        try {
            String outFile = "GeneLit.xml";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outFile)));
            prefs.storeToXML(out, null);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
