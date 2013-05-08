package traypass.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import traypass.ressources.Factory;
import traypass.tools.ToolClipboard;
import traypass.tools.ToolImage;

public class CaptureFrame extends JDialog {

	private int height = 0;
	private BufferedImage image, original;
	private Point c1;
	private JLabel label;
	private Rectangle rectangle = new Rectangle();
	private int padX, padY;

	public static int nbCapture = 0;

	public CaptureFrame(BufferedImage inputImage) {
		super();
		getContentPane().setBackground(Color.black);
		setLayout(null);
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		image = inputImage;
		original = inputImage;
		height = Factory.captureWidth * image.getHeight() / image.getWidth();
		label.setToolTipText("Hold left click to select - Middle click to save - Right click to clipboard - Space to reset");
		add(label);
		label.setLocation(0, 0);
		add(rectangle);
		setImage();

		setUndecorated(true);
		setResizable(false);
		setIconImage(Factory.trayImageIcon);
		setVisible(true);

		padX = getSize().width - getContentPane().getSize().width;
		padY = getSize().height - getContentPane().getSize().height - padX;

		label.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					c1 = e.getPoint();
					setComponentZOrder(rectangle, 0);
					rectangle.setLocation(0, 0);
					rectangle.setVisible(true);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && c1 != null) {
					double propW = (double) image.getWidth() / label.getIcon().getIconWidth();
					double propH = (double) image.getHeight() / label.getIcon().getIconHeight();
					double x1 = Math.min(e.getX(), c1.getX());
					double y1 = Math.min(e.getY(), c1.getY());
					double x2 = Math.max(e.getX(), c1.getX());
					double y2 = Math.max(e.getY(), c1.getY());
					int x = Math.min((int) (x1 * propW), image.getWidth() - 1);
					int y = Math.min((int) (y1 * propH), image.getHeight() - 1);
					int w = Math.min((int) ((x2 - x1) * propW), image.getWidth() - 1 - x);
					int h = Math.min((int) ((y2 - y1) * propH), image.getHeight() - 1 - y);
					image = image.getSubimage(x, y, w, h);
					setImage();
					c1 = null;
					rectangle.setVisible(false);
				}
				if (e.getButton() == MouseEvent.BUTTON2 && saveFile()) {
					exit();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					ToolClipboard.setClipboard(image);
					exit();
				}
			}
		});

		label.addMouseMotionListener(new MouseMotionAdapter() {
			int x, y, x1, y1;

			public void mouseDragged(MouseEvent e) {
				if (c1 != null) {
					x = (int) Math.min(c1.getX(), e.getX());
					y = (int) Math.min(c1.getY(), e.getY());
					x1 = (int) Math.max(c1.getX(), e.getX());
					y1 = (int) Math.max(c1.getY(), e.getY());
					rectangle.setBounds(x, y + padY, x1 - x, y1 - y);
					repaint();
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					image = original;
					c1 = null;
					setImage();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					exit();
				}
			}
		});

		addWindowListener(new Close());
		requestFocus();
	}

	private void setImage() {
		int iwidth = image.getWidth();
		int iheight = image.getHeight();

		if (iwidth > Factory.captureWidth) {
			iwidth = Factory.captureWidth - 1;
			iheight = iwidth * image.getHeight() / image.getWidth();
		}

		if (iheight > height) {
			iheight = height - 1;
			iwidth = iheight * image.getWidth() / image.getHeight();
		}

		setSize(new Dimension(iwidth, iheight));
		setLocationRelativeTo(getParent());
		setTitle(image.getWidth() + "x" + image.getHeight());

		BufferedImage icon = ToolImage.resizeImage(image, iwidth, iheight);
		label.setIcon(new ImageIcon(icon));
		label.setSize(iwidth, iheight);
	}

	private boolean saveFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setSelectedFile(new File(Factory.trayConfig.getCaptureDir() + "Capture" + nbCapture + ".png"));
		nbCapture++;
		chooser.setApproveButtonText("Save");
		chooser.setDialogTitle("Save image");
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			ToolImage.saveImage(image, chooser.getSelectedFile());
			Factory.trayConfig.setCaptureDir(chooser.getSelectedFile().getParentFile().getPath() + Factory.fileSeparator);
			Factory.trayConfig.save();
			return true;
		}
		return false;
	}

	class Close extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	private void exit() {
		image = null;
		original = null;
		label = null;
		dispose();
	}

	class Rectangle extends JPanel {
		int pointSize = 4;

		public Rectangle() {
			setOpaque(false);
		}

		public void paint(Graphics g) {
			g.setColor(Color.orange);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			g.setColor(Color.GREEN);
			g.fillRect((getWidth() - pointSize) / 2 - 1, (getHeight() - pointSize) / 2 - 1, pointSize, pointSize);
		}
	}
}