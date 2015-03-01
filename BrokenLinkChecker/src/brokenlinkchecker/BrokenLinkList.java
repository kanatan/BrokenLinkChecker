package brokenlinkchecker;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BrokenLinkList extends JScrollPane {
	JTable table=new JTable();
	DefaultTableModel list;

	public BrokenLinkList() {
		String[] title={"URL","タイプ"};
		list=new DefaultTableModel(title, 0);
		table.setModel(list);
		setViewportView(table);
	}

	public void addLink(String url,String type){
		String[] row={url,type};
		list.addRow(row);
	}
}
