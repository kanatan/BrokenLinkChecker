package brokenlinkchecker;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class LinkSourceList extends JScrollPane {
	private JList<String> list=new JList<String>();
	private HashMap<String,Vector<String>> map=new HashMap<String, Vector<String>>();

	public LinkSourceList() {
		setViewportView(list);
	}

	public void addSource(String path,String source){
		Vector<String> sources=map.get(path);
		if(sources==null){
			sources=new Vector<String>();
			map.put(path, sources);
		}
		sources.add(source);
	}

	public void showSource(String path){
		list.removeAll();
		list.setListData(map.get(path));
	}
}
