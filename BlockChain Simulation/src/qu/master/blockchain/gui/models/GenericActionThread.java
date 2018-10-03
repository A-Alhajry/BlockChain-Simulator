package qu.master.blockchain.gui.models;


public class GenericActionThread extends Thread {
	
	private Runnable validationMethod;
	
	public GenericActionThread(Runnable validationMethod) {
		this.validationMethod = validationMethod;
	}
	
	
	@Override
	public void run() {
		validationMethod.run();
	}
}
