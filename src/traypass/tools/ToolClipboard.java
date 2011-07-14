package traypass.tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;

public class ToolClipboard {

	public static void setClipboard(String text) {
		setClipboard(new StringSelection(text));
	}

	public static void setClipboard(BufferedImage image) {
		setClipboard(new ImageSelection(image));
	}

	public static void setClipboard(Transferable transferable) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
	}

	public static String getClipboardContent() {
		String result = "";
		try {
			Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
			if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	static class ImageSelection implements Transferable {

		private Image image;

		public ImageSelection(Image image) {
			this.image = image;
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
			if (flavor.equals(DataFlavor.imageFlavor) == false) {
				throw new UnsupportedFlavorException(flavor);
			}
			return image;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(DataFlavor.imageFlavor);
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}
	}

}