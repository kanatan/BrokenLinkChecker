package brokenlinkchecker;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BrokenLinkList extends JScrollPane {
	private LinkSourceList lslist;
	private JTable table;
	private DefaultTableModel list;

	public BrokenLinkList(LinkSourceList l) {
		lslist=l;
		String[] title={"URL","タイプ","タグ"};
		list=new DefaultTableModel(title, 0);
		table=new JTable(new DefaultTableModel() {
			@Override public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.setModel(list);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event){
				lslist.showSource((String)table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		table.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent event){
				lslist.showSource((String)table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		setViewportView(table);
	}

	public int addLink(String url,String type,String tag){
		String[] row={url,type,tag};
		for(int r=0;r<list.getRowCount();r++){
			if(table.getValueAt(r, 0).equals(url)&&table.getValueAt(r, 1).equals(type)
					&&table.getValueAt(r, 2).equals(tag)){
				return 0;
			}
		}
		list.addRow(row);
		return 1;
	}

	public void checkExternalLinks(){
		int ecount=0,ocount=0,bcount=0;
		for(int r=0;r<list.getRowCount();r++){
			if(table.getValueAt(r, 1).equals("外部リンク：未探索")){
				ecount++;
				try {
					URL url=new URL((String)table.getValueAt(r, 0));
					URLConnection connection = url.openConnection();
					try{
						connection.getInputStream();
						table.setValueAt("外部リンク：OK", r, 1);
						ocount++;
					}catch(FileNotFoundException e){
						//e.printStackTrace();
						table.setValueAt("外部リンク：リンク切れ", r, 1);
						bcount++;
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "外部リンク数："+ecount+"\n"+"OK数："+ocount+"\n"
				+"リンク切れ数："+bcount, "リンク切れチェッカー",JOptionPane.INFORMATION_MESSAGE);
	}
}
