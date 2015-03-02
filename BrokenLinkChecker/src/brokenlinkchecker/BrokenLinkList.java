package brokenlinkchecker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BrokenLinkList extends JScrollPane {
	private LinkSourceList lslist;
	private JTable table=new JTable();
	private DefaultTableModel list;

	public BrokenLinkList(LinkSourceList l) {
		lslist=l;
		String[] title={"URL","タイプ"};
		list=new DefaultTableModel(title, 0);
		table.setModel(list);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event){
				lslist.showSource((String)table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		setViewportView(table);
	}

	public void addLink(String url,String type){
		String[] row={url,type};
		list.addRow(row);
	}
}
