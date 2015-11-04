/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genelit;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    public HashMap<String, ArrayList<Integer>> readPapers;

    private static final String READ_PAPERS_ = "ReadPapers_";
    private static final String SGD_FEATURESTAB = "SGD_features.tab";
    private static final String GENE_LIST_ = "GeneList_";

    public updateJFrame ujf;

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
        readPapers = new HashMap<String, ArrayList<Integer>>();

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
            if (p.startsWith(GENE_LIST_)) {
                String pg = prefs.getProperty(p);
                ArrayList<String> genes = new ArrayList<String>();
                genes.addAll(Arrays.asList(pg.split(";")));
                geneLists.put(p.substring(9), genes);
                jComboBox1.addItem(p.substring(9));
            } else if (p.startsWith(READ_PAPERS_)) {
                String pg = prefs.getProperty(p, "");
                if (!pg.isEmpty()) {
                    ArrayList<Integer> paps = new ArrayList<Integer>();
                    for (String ss : pg.split(";")) {
                        Integer ii = Integer.parseInt(ss);
                        paps.add(ii);
                    }
                    readPapers.put(p.substring(11), paps);
                }
            }
        }

        if (jComboBox1.getItemCount() == 0) {
            jComboBox1.addItem("Default");
        }

        ujf = new updateJFrame();

        Action updateCheck = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Timer running");
                if (needUpdate()) {
                    new fileUpdater().execute();
                }
            }
        };

        Timer t = new Timer(3600000, updateCheck);
        t.start();

        if (needUpdate()) {
            new fileUpdater().execute();
        } else {
            updateDataFiles();
            updatedatafiles2();
        }

    }

    private boolean needUpdate() {
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

                File f = new File(GENE_LITERATURETAB);

                if (date > SGD_Lit_date || !f.exists()) {
                    ujf.setVisible(true);
                    System.out.println("File needs to be updated");
                    return true;
                }
            } catch (IOException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
        jTable1 = new javax.swing.JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = getValueAt(rowIndex, 2).toString();
                } catch (RuntimeException e1) {
                    //catch null pointer exception if mouse is over an empty line
                }

                return tip;
            }

            public TableCellRenderer getCellRenderer(int row, int column) {

                TableCellRenderer t=new myRenderer(row,column);
                return t;
            }

        };
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GeneLit");

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Gene", "PMID", "Title", "New?"
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

        jButton5.setText("Catch-up all listed");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Catch-up selected (toggle)");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Mark as new all listed");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class myCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String s = orfString(value.toString());

            Component c = super.getListCellRendererComponent(list, s, index, isSelected, cellHasFocus);

            return c;
        }
    }

    public String orfString(String orf) {
        if (orfList.keySet().contains(orf) && !orfList.get(orf).isEmpty()) {
            return orfList.get(orf) + " (" + orf + ")";
        } else {
            return orf;
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

            prefs.setProperty(GENE_LIST_ + glist, sb.toString());
            savePrefs();

        }

        System.out.println(geneLists.toString());

        jList1.setModel(slm);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String glist = jComboBox1.getSelectedItem().toString();
        DefaultListModel slm = (DefaultListModel) jList1.getModel();
        List vals = jList1.getSelectedValuesList();

        if (vals.size() > 0) {

            ArrayList<String> genes = geneLists.get(glist);
            for (Object o : vals) {
                slm.removeElement(o);
                genes.remove(o.toString());
            }

            geneLists.put(glist, genes);

            jList1.setModel(slm);
            jList1.setCellRenderer(new myCellRenderer());

            StringBuilder sb = new StringBuilder();
            for (String ss : genes) {
                sb.append(ss).append(";");
            }
            prefs.setProperty(GENE_LIST_ + glist, sb.toString());

            savePrefs();
        }

    }//GEN-LAST:event_jButton3ActionPerformed


    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        updateTable();
    }

    public void updateTable() {

        int[] sels = jList1.getSelectedIndices();
        DefaultListModel dlm = (DefaultListModel) jList1.getModel();

        int cnt = 0;
        int j = 0;

        for (int i : sels) {
            String s = dlm.getElementAt(i).toString();
            j += geneMap.get(s).size();
        }

        Object[][] tabdata = new Object[j][4];

        for (int i : sels) {

            String s = dlm.getElementAt(i).toString();
            ArrayList<Integer> pubs2 = geneMap.get(s);

            for (Integer ii : pubs2) {
                String newp = "New";
                ArrayList<Integer> rps = readPapers.get(s);
                if (rps != null) {
                    if (rps.contains(ii)) {
                        newp = "Old";
                    }
                }

                tabdata[cnt][0] = s;
                tabdata[cnt][1] = ii;
                tabdata[cnt][2] = pubs.get(ii);
                tabdata[cnt][3] = newp;
                cnt++;
            }
        }

        String[] colNames = {"Gene", "ID", "Title", "New?"};
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String orf = jTable1.getValueAt(i, 0).toString();
            Integer pmid = (Integer) jTable1.getValueAt(i, 1);

            ArrayList<Integer> rps = new ArrayList<Integer>();

            if (readPapers.keySet().contains(orf)) {
                rps = readPapers.get(orf);
            }

            if (!rps.contains(pmid)) {
                rps.add(pmid);
            }

            readPapers.put(orf, rps);

        }
        updateReadList();
        updateTable();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            System.out.println("i: " + i);
            String orf = jTable1.getValueAt(i, 0).toString();
            Integer pmid = (Integer) jTable1.getValueAt(i, 1);

            ArrayList<Integer> rps = new ArrayList<Integer>();

            if (readPapers.keySet().contains(orf)) {
                rps = readPapers.get(orf);
            }

            if (rps.contains(pmid)) {
                rps.remove(pmid);
            }

            readPapers.put(orf, rps);

        }
        updateReadList();
        updateTable();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int[] sel = jTable1.getSelectedRows();
        if (sel.length > 0) {
            System.out.println(Arrays.toString(sel));
            Arrays.sort(sel);
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (Arrays.binarySearch(sel, i) > -1) {
                    System.out.println("i: " + i);
                    String orf = jTable1.getValueAt(i, 0).toString();
                    Integer pmid = (Integer) jTable1.getValueAt(i, 1);

                    ArrayList<Integer> rps = new ArrayList<Integer>();

                    if (readPapers.keySet().contains(orf)) {
                        rps = readPapers.get(orf);
                    }

                    if (rps.contains(pmid)) {
                        rps.remove(pmid);
                    } else {
                        rps.add(pmid);
                    }

                    readPapers.put(orf, rps);

                }
            }
        }
        updateReadList();
        updateTable();
    }//GEN-LAST:event_jButton6ActionPerformed

    public void updateReadList() {
        for (String s : readPapers.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (Integer ii : readPapers.get(s)) {
                sb.append(ii.toString()).append(";");
            }
            prefs.setProperty(READ_PAPERS_ + s, sb.toString());
        }
        savePrefs();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GeneLitJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
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

    public class fileUpdater extends SwingWorker<String, Void> {

        @Override
        protected String doInBackground() throws Exception {
            try {
                URL u = new URL("http://downloads.yeastgenome.org/curation/literature/gene_literature.tab");
                try {
                    HttpURLConnection huc = (HttpURLConnection) u.openConnection();
                    long date = huc.getLastModified();

                    ujf.setVisible(true);
                    System.out.println("File needs to be updated");
                    setEnabled(false);
                    new sgdUpdater().execute();
                    final String filename = GENE_LITERATURETAB;

                    BufferedInputStream in = new BufferedInputStream(u.openStream());
                    long sz = u.openConnection().getContentLengthLong();

                    byte b[] = new byte[16384];

                    FileOutputStream fos = new FileOutputStream(filename);
                    int bytesread;
                    int cnt = 0;
                    long total = 0;
                    while ((bytesread = in.read(b)) > -1) {
                        fos.write(b, 0, bytesread);

                        b = new byte[16384];
                        if (cnt == 32) {
                            ujf.setProgress((int) (100 * total / sz));
                            cnt = 0;
                        }
                        cnt++;
                        total += bytesread;
                    }

                    ujf.setProgress(100);

                    in.close();
                    fos.flush();
                    fos.close();

                    SGD_Lit_date = date;
                    prefs.setProperty("SGD_Lit_date", SGD_Lit_date.toString());
                    savePrefs();

                } catch (IOException ex) {
                    Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(GeneLitJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (ujf != null) {
                ujf.dispose();
            }

            setEnabled(true);
            setVisible(true);

            updateDataFiles();
            jList1.repaint();

            return "";
        }

    }

    public class sgdUpdater extends SwingWorker<String, Void> {

        @Override
        protected String doInBackground() throws Exception {
            URL u = new URL("http://downloads.yeastgenome.org/curation/chromosomal_feature/SGD_features.tab");
            BufferedInputStream in = new BufferedInputStream(u.openStream());
            long sz = u.openConnection().getContentLengthLong();

            byte b[] = new byte[16384];
            final String filename = SGD_FEATURESTAB;
            FileOutputStream fos = new FileOutputStream(filename);
            int bytesread;
            int cnt = 0;
            long total = 0;
            while ((bytesread = in.read(b)) > -1) {
                fos.write(b, 0, bytesread);

                b = new byte[16384];
                if (cnt == 10) {
                    ujf.setProgress2((int) (100 * total / sz));
                    cnt = 0;
                }
                cnt++;
                total += bytesread;
            }
            ujf.setProgress2(100);

            in.close();
            fos.flush();
            fos.close();
            updatedatafiles2();
            jList1.repaint();
            return "";
        }
    }

    private static final String GENE_LITERATURETAB = "gene_literature.tab";

    private void updateDataFiles() {
        File f = new File(GENE_LITERATURETAB);
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

    }

    private void updatedatafiles2() {

        File f = new File(SGD_FEATURESTAB);
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

    public class myRenderer extends DefaultTableCellRenderer {

        int r, c;

        public myRenderer(int row, int col) {
            super();
            r = row;
            c = col;
            setOpaque(true);
        }

        public void setAl(int al) {
            setHorizontalAlignment(al);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            try {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

            if (column == 0 && value != null) {
                setText(orfString(value.toString()));
            }

            if (jTable1.getValueAt(row, 3) == "New") {
                setForeground(java.awt.Color.green.darker());
            }

            return this;
        }

//        @Override
//        @SuppressWarnings("unchecked")
//        public void setValue(Object value) {
//
//            if (value == null) {
//                return;
//            }
//
//            if (c == COL_INDEX) {
//                setForeground(Color.white);
//                setBackground(Color.black);
//                setText("#");
//            }
//
//            if (value instanceof Double) {
//                NumberFormat nf = NumberFormat.getNumberInstance();
//                nf.setMaximumFractionDigits(4);
//                setText(nf.format(value));
//            } else if (value.getClass().equals(new TreeSet<String>().getClass())) {
//                StringBuilder outString = new StringBuilder();
//
//                for (String s : (TreeSet<String>) value) {
//                    outString.append(s).append(", ");
//                }
//
//                if (outString.length() > 2) {
//                    setText(outString.substring(0, outString.length() - 2));
//                }
//            } else {
//                setText(value.toString());
//            }
//
//        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
