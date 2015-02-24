package brokenlinkchecker;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class BrokenLinkChecker extends JFrame {
	public BrokenLinkChecker(){
		super("リンク切れチェッカー");
		setSize(600,400);
		setLocationRelativeTo(null);
		add(new AdressBar(),BorderLayout.NORTH);
		JSplitPane pane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new FileList(),new BrokenLinkList());
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
