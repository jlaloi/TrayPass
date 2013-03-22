package traypass.tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import traypass.TrayPassObject;
import traypass.log.LogFactory;

public class ToolClipboard {
	
	private static final Logger logger = LogFactory.getLogger(ToolClipboard.class);

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
		String result = null;
		try {
			Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
			if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} else if (contents != null && contents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				List<File> files = (List) contents.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : files) {
					if (result == null) {
						result = file.getAbsolutePath();
					} else {
						result += TrayPassObject.lineSeparator + file.getAbsolutePath();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
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