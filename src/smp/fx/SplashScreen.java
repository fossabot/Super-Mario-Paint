package smp.fx;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Until I can figure out how to do JavaFX preload/splash screens, 
 * this will be a placeholder class.
 * @author RehdBlob
 * @since 2012.08.17
 */
public class SplashScreen extends JFrame implements Runnable {

	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = 6705972583468020200L;
	
	private JButton loading;
	
	/**
	 * Displays a dummy window that says "Loading!"
	 */
	@Override
	public void run() {
		loading = new JButton("Loading!");
		loading.setBorder(BorderFactory.createEmptyBorder());
		loading.setContentAreaFilled(false);
		add(loading, BorderLayout.CENTER);
		setSize(new Dimension(400, 400));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

}