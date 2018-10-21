package qu.master.blockchain.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import qu.master.blockchain.gui.Constants;
import qu.master.blockchain.gui.models.BlockChain;

public class LinearBlockChainPanel extends BlockChainPanel {
	
	private BlockChain<String> blockChain;
	private List<BlockPanel> blockPanels;
	
	private JLabel directionLabel;
	private ButtonGroup directionsButtonsGroup;
	private JRadioButton hButton;
	private JRadioButton vButton;
	private JScrollPane scroller;
	
	private ButtonGroup dirGroup;
	private JRadioButton hRadio;
	private JRadioButton vRadio;
	
	private static final int rows = 300;
	private static final int cols = 44;
	
	private static final int hGap = 50;
	private static final int vGap = 14;
	
	private static final int labelFontSize = 16;
	
	private int panelsCount;
	private char direction = 'v';
	
	public LinearBlockChainPanel(List<BlockPanel> blockPanels) {
		this.blockPanels = blockPanels;
		directionLabel = new JLabel("Chain Direction");
		directionLabel.setFont(new Font(directionLabel.getFont().getFontName(), Font.BOLD, labelFontSize));
		hButton = new JRadioButton("Horizontal");
		hButton.setActionCommand("Horizontal");
		hButton.setSelected(true);
		vButton = new JRadioButton("Vertical");
		vButton.setActionCommand("Vertical");
		
		directionsButtonsGroup = new ButtonGroup();
		directionsButtonsGroup.add(hButton);
		directionsButtonsGroup.add(vButton);
		
		dirGroup = new ButtonGroup();
		hRadio = new JRadioButton("Horizontal");
		hRadio.setActionCommand("H");
		vRadio = new JRadioButton("Vertical");
		vRadio.setActionCommand("V");
		vRadio.setSelected(true);
		
	}
	
	@Override
	public void initPanel() {
		scroller = new JScrollPane(this.getParent(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		setLayout(new GridBagLayout());
		panelsCount = blockPanels.size();
		Dimension panelSize = new Dimension(1000, 1000);
		
		JPanel dirPanel = new JPanel();
		dirPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(vGap, hGap * 2, vGap, hGap * 2);
		dirPanel.add(hRadio, gbc);
		gbc.gridx = 1;
		dirPanel.add(vRadio, gbc);
		
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		//add(dirPanel, gbc);
		
		int rowNumber = 2;
		for(BlockPanel newPanel : blockPanels) {
			
			newPanel.initPanel();
			newPanel.setPreferredSize(panelSize);
			newPanel.setMinimumSize(panelSize);
			gbc = new GridBagConstraints();
			if (rowNumber > 1) {
				//break;
			}
			addBlockPanel(newPanel, gbc, rowNumber++);
			
		}
	}
	
	public void setScroller() {
		((JPanel) this.getParent()).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		scroller = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//add(scroller);
	}
	
	private void setScrollBar() {
		if (direction == 'h') {
			scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		
		else {
			scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
	}
	
	private void addBlockPanel(BlockPanel panel, GridBagConstraints gbc, int rowNumber) {

		gbc.insets = new Insets(vGap / 3, hGap, vGap / 3, hGap);

		if (direction == 'h') {
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.weightx = rowNumber == panelsCount ? 1 : 0;
			gbc.weighty = 1;
			gbc.gridx = rowNumber - 1;
			gbc.gridy = 0;
		}
		
		else {
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1;
			gbc.weighty = rowNumber == panelsCount ? 1 : 0;
			gbc.gridx = 0;
			gbc.gridy = rowNumber - 1;
			//gbc.ipadx = 100;
			//gbc.ipady = 100;
			
			gbc.insets = new Insets(vGap / 3, hGap, vGap / 3, hGap);
			
		}
		
		panel.setBackground(Color.decode(Constants.ValidBlockColor));
		//panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		panel.setVisible(true);
		add(panel, gbc);
	}
	
	public JScrollPane getScroller() {
		return this.scroller;
	}
	
	public BlockChain<String> getBlockChain() {
		return this.blockChain;
	}
	
	public JRadioButton getHorizontalButton() {
		return this.hButton;
	}
	
	public JRadioButton getVerticalButton() {
		return this.vButton;
	}
	
	public List<BlockPanel> getBlockPanels() {
		return this.blockPanels;
	}
}
