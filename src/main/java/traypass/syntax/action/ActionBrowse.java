package traypass.syntax.action;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import traypass.ressources.Factory;
import traypass.syntax.Action;

public class ActionBrowse extends Action {

	public static final String file = "file";

	public static final String dir = "directory";

	public String doAction(List<String> parameters) {
		String result = null;
		int type = JFileChooser.FILES_ONLY;
		if (dir.equalsIgnoreCase(parameters.get(0))) {
			type = JFileChooser.DIRECTORIES_ONLY;
		}
		String title = Factory.appName;
		if(parameters.size() > 1){
			 title = parameters.get(1);
		}
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(type);
		chooser.setApproveButtonText("Select");
		chooser.setDialogTitle(title);
		int returnVal = chooser.showOpenDialog(new JDialog());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			result = chooser.getSelectedFile().getAbsolutePath();
		}
		return result;
	}

}