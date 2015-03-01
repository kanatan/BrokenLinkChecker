package brokenlinkchecker;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class AdressBar extends JSplitPane implements ActionListener{
	BrokenLinkList link;
	JTextField adressBar=new JTextField("http://");

	public AdressBar(BrokenLinkList l) {
		super(HORIZONTAL_SPLIT);
		link=l;
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
		try {
			URL url;
			if(adressBar.getText().startsWith("http")){
				url=new URL(adressBar.getText());
			}
			else{
				url=new URL("file:///"+adressBar.getText());
			}
			URLConnection connection = url.openConnection();
			InputStream inStream = connection.getInputStream();
			BufferedReader input =new BufferedReader(new InputStreamReader(inStream));
			String html="";
			String str="";
			while((str=input.readLine())!=null){
				html+=str+"\n";
			}
			link.html.setText(html);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
