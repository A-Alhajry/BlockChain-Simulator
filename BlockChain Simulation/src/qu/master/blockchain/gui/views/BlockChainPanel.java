package qu.master.blockchain.gui.views;

import java.util.List;

import qu.master.blockchain.gui.models.Block;

public abstract class BlockChainPanel extends AppPanel {
	
	public abstract List<BlockPanel> getBlockPanels();
}
