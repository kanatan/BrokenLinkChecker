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
		String[] title={"URL","タイプ","タグ"};
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

	public void addLink(String url,String type,String tag){
		String[] row={url,type,tag};
		for(int n=0;n<list.getRowCount();n++){
			if(table.getValueAt(n, 0).equals(url)&&table.getValueAt(n, 1).equals(type)
					&&table.getValueAt(n, 2).equals(tag)){
				return;
			}
		}
		list.addRow(row);
	}
}
