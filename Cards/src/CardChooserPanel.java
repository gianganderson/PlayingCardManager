import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


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
		return new SuitFace(
			(Suit) suitList.getSelectedItem(),
			(Face) faceList.getSelectedItem()
		);
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
	private String carddirname = "C:\\Users\\giang_000\\eclipse_workspace\\PlayingCardManager\\src\\Images";

	
	public CardChooserPanel() {
		this.setLayout(null);
        control = new CardChooserControl();
        control.setVisible(true);
        add(control);
		
		chooserbtn = new JButton("Choose Card");
		chooserbtn.setVisible(true);
		add(chooserbtn);

		
		chooserbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel = new CardImagePanel();
				map = new SuitFaceMap();

				SuitFace sf = control.getDisplayedCard();
				System.out.println(sf.suit + " " +   sf.face);
				BufferedImage img = map.getImage(sf);
				if(img != null) {
					imagePanel.setImage(map.scale(map.getImage(sf), 0.4));
					imagePanel.setVisible(true);
					add(imagePanel);
					
					revalidate();

				}

				
				
				
//				fileChooser = new JFileChooser();
//				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				fileChooser.setCurrentDirectory(new File("."));
//				String imagesPath = fileChooser.getCurrentDirectory() + "\\src\\Images";
//				fileChooser.setCurrentDirectory(new File(imagesPath));
//				System.out.println(fileChooser.getCurrentDirectory());
//				
//				fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "png"));
//				fileChooser.showOpenDialog(chooserbtn);		
//				File image = fileChooser.getSelectedFile();
//				System.out.println(image);
//				try {
//					BufferedImage img = ImageIO.read(image);
//					imagePanel = new CardImagePanel();
//					if (img != null) {
//						imagePanel.setImage(img);
//						imagePanel.paintComponent(img.getGraphics());
//						imagePanel.setVisible(true);
//						add(imagePanel);
//						revalidate();
//						repaint();
//					}
//					
//
//				} catch (IOException e1) {
//					e1.printStackTrace();
				
				
//				}
			}
		});
		
		
	}
	
	public static void buildGUI() {
		JFrame jf = new JFrame();
		CardChooserPanel ccp = new CardChooserPanel();
		jf.setContentPane(ccp);
		jf.setVisible(true);
		jf.setSize(500, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class CardMover extends MouseAdapter {
	private int startx;
	private int starty;
	
	public void mousePressed(MouseEvent e) {
		startx = e.getX();
		starty = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
}

class CardImagePanel extends JPanel {
	private BufferedImage img;
	
	public CardImagePanel() {		
		super(true);
		CardMover cardMover = new CardMover();
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
			return new Dimension(0,0);
	}
}



class Tester {
	public static void main(String[] args) {
		CardChooserPanel.buildGUI();
		SuitFaceMap sfm = new SuitFaceMap();

	}
}