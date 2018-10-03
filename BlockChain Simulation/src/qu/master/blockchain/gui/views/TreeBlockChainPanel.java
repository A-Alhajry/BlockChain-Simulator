package qu.master.blockchain.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import qu.master.blockchain.gui.models.TreeBlock;
import qu.master.blockchain.gui.models.TreeBlockChain;

public class TreeBlockChainPanel extends BlockChainPanel {
	
	private TreeBlockChain<String> blockChain;
	private List<BlockPanel> blockPanels;
	
	private static final int rows = 1200;
	private static final int cols = 200;
	private static final int hGap = 80;
	private static final int vGap = 20;
	
	private static final int maxLevel = 4;
	
	public TreeBlockChainPanel(List<BlockPanel> blockPanels) {
		this.blockPanels = blockPanels;
	}
	
	public void initPanel() {
		setLayout(new GridBagLayout());
		for( int i = 1; i <= maxLevel; i++) {
			List<TreeBlock<String>> blocksByLevel = new ArrayList<>();
			List<BlockPanel> panelsByLevel = new ArrayList<>();
			
			
			for(int j = 0; j < this.blockPanels.size(); j++) {
				BlockPanel panel = this.blockPanels.get(j);
				TreeBlock<String> treeBlock = (TreeBlock<String>) panel.getBlock();
				
				if (treeBlock.getLevel() == i) {
					blocksByLevel.add(treeBlock);
					panelsByLevel.add(panel);
				}
			}
			
			addTreeLevelRow(panelsByLevel, blocksByLevel, i, maxLevel);
		}
		
	}
	
	private void addTreeLevelRow(List<BlockPanel> panels, List<TreeBlock<String>> blocks, int level, int totalLevels) {
		JPanel row = new JPanel();
		row.setLayout(new GridBagLayout());
		int blocksCount = panels.size();
		Dimension panelSize = new Dimension(1000, 1000);
		Dimension levelSize = new Dimension(10000, 1000 + vGap * 4);
		
		//row.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
		
		GridBagConstraints rowConstraints = new GridBagConstraints();
		rowConstraints.gridx = 0;
		rowConstraints.gridy = level - 1;
		rowConstraints.insets = new Insets(vGap / 3, hGap, vGap / 3, hGap);
		rowConstraints.fill = GridBagConstraints.HORIZONTAL;
		rowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		rowConstraints.weightx = 1;
		rowConstraints.weighty = level == totalLevels ? 1 : 0;
		row.setVisible(true);
		row.setPreferredSize(levelSize);
		row.setMinimumSize(levelSize);
		
		for(int i = 0; i < blocksCount; i++) {
			TreeBlock<String> block = blocks.get(i);
			BlockPanel panel = panels.get(i);
			panel.initPanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(vGap / 3, hGap, vGap / 3, hGap);
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.weightx = i == blocksCount - 1 ? 1 : 0;
			gbc.weighty = 1;
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.ipady = 2000;
			//panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			panel.setVisible(true);
			panel.setPreferredSize(panelSize);
			panel.setMinimumSize(panelSize);
			row.add(panel, gbc);
		}
		
		add(row, rowConstraints);

		
		
	}
	
	public List<BlockPanel> getBlockPanels() {
		return this.blockPanels;
	}
	
}
