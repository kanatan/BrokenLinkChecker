package brokenlinkchecker;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileList extends JScrollPane {
	private DefaultMutableTreeNode node=new DefaultMutableTreeNode("URLを指定してください。");
	private JTree tree=new JTree(node);

	public FileList() {
		setViewportView(tree);
	}

	public void addPath(String path){
		if(node.getUserObject().equals("URLを指定してください。")){
			node.setUserObject(path);
		}
		else{
			String[] fp=path.split("/");
			int p=0;
			DefaultMutableTreeNode node2=node;
			while(p<fp.length){
				int p2=p;
				for(int n=0;n<node.getChildCount();n++){
					if(((DefaultMutableTreeNode)node2.getChildAt(n)).getUserObject().equals(fp[p])){
						p++;
						node2=(DefaultMutableTreeNode)node2.getChildAt(n);
						break;
					}
				}
				if(p==p2){
					break;
				}
			}
			if(p!=fp.length){
				for(;p<fp.length;p++){
					DefaultMutableTreeNode node3=new DefaultMutableTreeNode(fp[p]);
					node2.add(node3);
					node2=node3;
				}
			}
		}
		((DefaultTreeModel)tree.getModel()).reload();
	}
}
