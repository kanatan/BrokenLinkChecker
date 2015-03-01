package brokenlinkchecker;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class BrokenLinkChecker extends JFrame {
	BrokenLinkList link=new BrokenLinkList();
	public BrokenLinkChecker(){
		super("リンク切れチェッカー");
		setSize(600,400);
		setLocationRelativeTo(null);
		add(new AdressBar(link),BorderLayout.NORTH);
		JSplitPane pane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new FileList(),link);
		pane.setDividerLocation(200);
		pane.setDividerSize(1);
		add(pane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new BrokenLinkChecker();
	}
}
