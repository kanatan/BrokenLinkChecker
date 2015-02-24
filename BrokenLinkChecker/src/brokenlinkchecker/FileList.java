package brokenlinkchecker;

import javax.swing.JScrollPane;
import javax.swing.JTree;

public class FileList extends JScrollPane {
	JTree tree=new JTree();

	public FileList() {
		setViewportView(tree);
	}
}
