package brokenlinkchecker;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class BrokenLinkChecker extends JFrame {
	public BrokenLinkChecker(){
		super("リンク切れチェッカー");
		setSize(600,400);
		setLocationRelativeTo(null);
		LinkSourceList lslist=new LinkSourceList();
		FileList flist=new FileList();
		BrokenLinkList bllist=new BrokenLinkList(lslist);
		add(new AdressBar(flist,bllist,lslist),BorderLayout.NORTH);
		JSplitPane pane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,bllist,lslist);
		pane.setDividerLocation(250);
		pane.setDividerSize(1);
		JSplitPane pane2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,flist,pane);
		pane2.setDividerLocation(200);
		pane2.setDividerSize(1);
		add(pane2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new BrokenLinkChecker();
	}
}
