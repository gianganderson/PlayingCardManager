import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;;

public class CardChooserDialog extends JDialog {
	private CardChooserPanel cp;
	private JButton btnOkay;
	private JButton btnCancel;
	private CardChooserControl[] controls;
	private int numCards = 5;
	DialogContentPane dcp;
	
	public CardChooserDialog(CardChooserPanel cp) {
		super();
		this.cp = cp;
		dcp = new DialogContentPane();
		setContentPane(dcp);
	}
	
	private class ControlPanel extends JPanel {
		public ControlPanel() {
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints consForCardChooser = new GridBagConstraints();
			GridBagConstraints consForLabel = new GridBagConstraints();
			setLayout(layout);
			
			controls = new CardChooserControl[numCards];
			
			for(int i = 0; i < controls.length; i++) {
				consForLabel.gridx = -15;
				consForLabel.gridy = i;
				consForCardChooser.gridy = i;
		        controls[i] = new CardChooserControl();
		        JLabel label = new JLabel("Card #" + (i + 1));
		        add(label, consForLabel);
		        add(controls[i], consForCardChooser);
			}
		}
	}
	
	private class ButtonPane extends JPanel {
		public ButtonPane() {
			super(true);
			btnOkay = new JButton("Okay");
			add(btnOkay);
			btnOkay.setVisible(true);
			btnOkay.setBounds(125, 375, 70, 50);
			btnCancel = new JButton("Cancel");
			add(btnCancel);
			btnCancel.setVisible(true);
			btnCancel.setBounds(300,375,80,50);
			setBorder(BorderFactory.createEtchedBorder());
			btnOkay.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Window w = SwingUtilities.getWindowAncestor(dcp);
					w.setVisible(false);

					SuitFaceMap map = new SuitFaceMap();
					int x = 10;
					int y = 50;
					for (int i = 0; i < controls.length; i++) {
						CardImagePanel cip = new CardImagePanel();

						controls[i].saveDisplayedCard();
						SuitFace sf = controls[i].getSavedCard();
						System.out.println(sf.suit + " " +   sf.face);
						BufferedImage img = map.getImage(sf);

						if(img != null) {

							cip.setImage(map.scale(map.getImage(sf), 0.3));
							cip.setBounds(x ,y,200,220);
							x+= 40;
							cip.setVisible(true);
							cp.add(cip);
							cp.revalidate();
							cp.repaint();

						}
					}

				}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Window w = SwingUtilities.getWindowAncestor(dcp);
					w.setVisible(false);
					for (CardChooserControl ccc : controls) {
						ccc.saveDisplayedCard();
						System.out.println(ccc.getSavedCard().suit);
						System.out.println(ccc.getSavedCard().face);
						ccc.restoreDisplayedCard();
					}
				}
			});
		}
		
	}
	
	private class DialogContentPane extends JPanel {
		public DialogContentPane() {
			super(true);
			setTitle("Choose cards");

			
			setLayout(new BorderLayout());
			add(new ControlPanel(), BorderLayout.CENTER);
			add(new ButtonPane(), BorderLayout.SOUTH);
		}
		
	}
	

	
	
}
