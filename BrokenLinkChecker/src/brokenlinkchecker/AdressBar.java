package brokenlinkchecker;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class AdressBar extends JSplitPane implements ActionListener{
	JTextField adressBar=new JTextField("http://");

	public AdressBar() {
		super(HORIZONTAL_SPLIT);
		adressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event){
				adressBar.selectAll();
			}
		});
		adressBar.setCaretPosition(7);
		adressBar.addActionListener(this);
		setLeftComponent(adressBar);
		JButton go=new JButton("Go");
		go.setMargin(new Insets(0, 0, 0, 0));
		go.addActionListener(this);
		setRightComponent(go);
		setDividerLocation(550);
		setDividerSize(0);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		;
	}
}
