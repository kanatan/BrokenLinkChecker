package brokenlinkchecker;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BrokenLinkList extends JScrollPane {
	/*JTable table=new JTable();
	DefaultTableModel list;*/
	JTextArea html=new JTextArea();

	public BrokenLinkList() {
		/*String[] title={"URL","タイプ"};
		list=new DefaultTableModel(title, 0);
		table.setModel(list);
		setViewportView(table);*/
		setViewportView(html);
	}
}
