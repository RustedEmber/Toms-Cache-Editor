package com.auradevil.cachesuite.guis;

import com.auradevil.cachesuite.Main;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jagex.cache.CacheIndice;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author tom
 */
public class CacheEdit extends JInternalFrame {
	public JList files;
	public JButton replaceFileButton;
	public JButton dumpFileButton;
	public JButton removeFileButton;
	public JButton addFileButton;
	public JPanel main;
	public JLabel fileIDLabel;
	public JLabel sizeLabel;
	private final CacheIndice editingIndice;

	public CacheEdit(final int cache) {
		add(main);
		editingIndice = Main.logic.getCurrentCache().getIndice(cache);
		pack();
		String[] filesData = new String[editingIndice.getNumFiles()];
		for (int i = 0; i < editingIndice.getNumFiles(); i++) {
			filesData[i] = String.valueOf(i);
		}
		files.setListData(filesData);
		files.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				// Fill information fields
				if (files.getSelectedIndex() == -1) {
					fileIDLabel.setText("No file selected");
					sizeLabel.setText("");
					replaceFileButton.setEnabled(false);
					dumpFileButton.setEnabled(false);
					removeFileButton.setEnabled(false);
					return;
				}
				byte[] data = null;
				try {
					data = editingIndice.getFile(files.getSelectedIndex());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred whilst loading archive:\n" + e);
					e.printStackTrace();
				}
				if (data == null) {
					fileIDLabel.setText("File contains no data");
					sizeLabel.setText("");
					replaceFileButton.setEnabled(false);
					dumpFileButton.setEnabled(false);
					removeFileButton.setEnabled(false);
					return;
				}
				fileIDLabel.setText("File ID: " + files.getSelectedIndex());
				sizeLabel.setText("Size: " + data.length);
				replaceFileButton.setEnabled(true);
				dumpFileButton.setEnabled(true);
				removeFileButton.setEnabled(true);
			}
		});
		dumpFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (files.getSelectedIndex() == -1) {
					return;
				}
				byte[] data = null;
				try {
					data = editingIndice.getFile(files.getSelectedIndex());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred whilst loading archive:\n" + e);
					e.printStackTrace();
				}
				try {
					Main.logic.saveToFile(data);
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "File dump sucessful");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred dumping file:\n" + e);
					e.printStackTrace();
				}
			}
		});
		replaceFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (files.getSelectedIndex() == -1) {
					return;
				}
				try {
					byte[] data = Main.logic.loadFromFile();
					Main.logic.addOrEditFile(cache, files.getSelectedIndex(), data);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred replacing file:\n" + e);
					e.printStackTrace();
				}
			}
		});
		addFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					byte[] data = Main.logic.loadFromFile();
					if (data != null) {
						Main.logic.addOrEditFile(cache, editingIndice.getNumFiles(), data);
						updateFileList();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred adding file:\n" + e);
					e.printStackTrace();
				}
			}
		});
		removeFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingIndice.removeFile(files.getSelectedIndex());
				try {
					Main.logic.rebuildCache();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Main.logic.getSwingComponent(), "An error occurred removing file:\n" + e1);
					e1.printStackTrace();
				}
				updateFileList();
			}
		});
	}

	private void updateFileList() {
		String[] filesData = new String[editingIndice.getNumFiles()];
		for (int i = 0; i < editingIndice.getNumFiles(); i++) {
			filesData[i] = String.valueOf(i);
		}
		files.setListData(filesData);
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		main = new JPanel();
		main.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
		final JScrollPane scrollPane1 = new JScrollPane();
		main.add(scrollPane1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(75, 400), new Dimension(100, -1), new Dimension(100, -1), 0, false));
		scrollPane1.setBorder(BorderFactory.createTitledBorder("Files"));
		files = new JList();
		scrollPane1.setViewportView(files);
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		main.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(300, 75), new Dimension(217, 14), new Dimension(-1, 100), 0, false));
		panel1.setBorder(BorderFactory.createTitledBorder("Info"));
		fileIDLabel = new JLabel();
		fileIDLabel.setText("No file selected");
		panel1.add(fileIDLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		sizeLabel = new JLabel();
		sizeLabel.setText("");
		panel1.add(sizeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
		main.add(panel2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		panel2.setBorder(BorderFactory.createTitledBorder("Operations"));
		dumpFileButton = new JButton();
		dumpFileButton.setEnabled(false);
		dumpFileButton.setText("Dump File");
		dumpFileButton.setMnemonic('D');
		dumpFileButton.setDisplayedMnemonicIndex(0);
		panel2.add(dumpFileButton, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		removeFileButton = new JButton();
		removeFileButton.setEnabled(false);
		removeFileButton.setText("Remove File");
		removeFileButton.setMnemonic('R');
		removeFileButton.setDisplayedMnemonicIndex(0);
		panel2.add(removeFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		replaceFileButton = new JButton();
		replaceFileButton.setEnabled(false);
		replaceFileButton.setText("Replace File");
		replaceFileButton.setMnemonic('F');
		replaceFileButton.setDisplayedMnemonicIndex(8);
		panel2.add(replaceFileButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		addFileButton = new JButton();
		addFileButton.setText("Add File");
		addFileButton.setMnemonic('A');
		addFileButton.setDisplayedMnemonicIndex(0);
		panel2.add(addFileButton, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		panel2.add(spacer1, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final Spacer spacer2 = new Spacer();
		panel2.add(spacer2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final Spacer spacer3 = new Spacer();
		panel2.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return main;
	}
}
