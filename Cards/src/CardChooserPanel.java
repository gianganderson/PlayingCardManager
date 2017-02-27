import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CardChooserControl extends JPanel {
	private JComboBox<Face> faceList;
	private JLabel label;
	private JComboBox<Suit> suitList;

	private SuitFace savedValue;

	public CardChooserControl() {
		faceList = new JComboBox<Face>(Face.values());
		add(faceList);
		label = new JLabel("of");
		add(label);
		suitList = new JComboBox<Suit>(Suit.values());
		add(suitList);
	}

	public SuitFace getDisplayedCard() {
		return new SuitFace((Suit) suitList.getSelectedItem(), (Face) faceList.getSelectedItem());
	}

	public SuitFace getSavedCard() {
		return savedValue;
	}

	public void saveDisplayedCard() {
		savedValue = getDisplayedCard();
	}

	public void restoreDisplayedCard() {
		suitList.setSelectedItem(savedValue.suit);
		faceList.setSelectedItem(savedValue.face);
	}

}

public class CardChooserPanel extends JPanel {
	private CardChooserControl control;
	private CardImagePanel imagePanel;
	private JButton chooserbtn;
	private SuitFaceMap map;
	private CardChooserDialog dialog;

	public CardChooserPanel() {

		chooserbtn = new JButton("Choose");
		chooserbtn.setBounds(10, 10, 150, 25);
		chooserbtn.setVisible(true);
		add(chooserbtn);
		CardChooserPanel ccp = this;
		this.setLayout(null);
		dialog = new CardChooserDialog(ccp);
		dialog.setBounds(250, 250, 500, 500);

		chooserbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				dialog.setVisible(true);

				// imagePanel = new CardImagePanel();
				// map = new SuitFaceMap();
				//
				// SuitFace sf = control.getDisplayedCard();
				// System.out.println(sf.suit + " " + sf.face);
				// BufferedImage img = map.getImage(sf);
				// if(img != null) {
				// imagePanel.setImage(map.scale(map.getImage(sf), 0.4));
				// imagePanel.setVisible(true);
				// add(imagePanel);
				//
				// revalidate();
				//
				// }

			}
		});

	}

	public static void buildGUI() {
		JFrame jf = new JFrame();
		CardChooserPanel ccp = new CardChooserPanel();
		jf.setTitle("Cards");
		jf.setContentPane(ccp);
		jf.setVisible(true);
		jf.setSize(900, 900);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class CardMover extends MouseAdapter {
	private CardImagePanel cardImagePanel;
	private int startx;
	private int starty;
	
	public CardMover(CardImagePanel cardImagePanel) {
		this.cardImagePanel = cardImagePanel;
	}

	public void mousePressed(MouseEvent e) {
		startx = e.getX();
		starty = e.getY();	
	}

	public void mouseDragged(MouseEvent e) {
		cardImagePanel.setLocation(e.getLocationOnScreen().x - startx, e.getLocationOnScreen().y - starty);
		cardImagePanel.revalidate();
		cardImagePanel.repaint();
		
	}
}

class CardImagePanel extends JPanel {
	private BufferedImage img;

	public CardImagePanel() {
		super(true);
		CardMover cardMover = new CardMover(this);
        addMouseListener(cardMover);
        addMouseMotionListener(cardMover);
	}

	public void setImage(BufferedImage m) {
		img = m;
	}

	public void paintComponent(Graphics g) {
		if (img != null)
			g.drawImage(img, 0, 0, null);
	}

	public Dimension getPreferredSize() {
		if (img != null)
			return new Dimension(img.getWidth(), img.getHeight());
		else
			return new Dimension(0, 0);
	}
}

class Tester {
	public static void main(String[] args) {
		CardChooserPanel.buildGUI();
		SuitFaceMap sfm = new SuitFaceMap();

	}
}