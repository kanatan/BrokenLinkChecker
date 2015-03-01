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
	FileList file;
	BrokenLinkList link;
	JTextField adressBar=new JTextField("http://");
	String base;

	public AdressBar(FileList f,BrokenLinkList l) {
		super(HORIZONTAL_SPLIT);
		file=f;
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
		base=adressBar.getText();
		base=base.substring(0, base.lastIndexOf("/")+1);
		file.addPath(base);
		searchURL(base);
	}

	private void searchURL(String path){
		System.out.println(path);
		try {
			URL url;
			if(path.startsWith("http")){
				url=new URL(path);
			}
			else{
				url=new URL("file:///"+path);
			}
			URLConnection connection = url.openConnection();
			InputStream inStream = connection.getInputStream();
			BufferedReader input =new BufferedReader(new InputStreamReader(inStream));
			String str="";
			while((str=input.readLine())!=null){
				if(str.indexOf("<a")!=-1){
					str=str.substring(str.indexOf("href=\"")+"href=\"".length(),
							str.indexOf("\"",str.indexOf("href=\"")+"href=\"".length()));
					path=path.substring(0, path.lastIndexOf("/")+1)+str;
					file.addPath(path.substring(base.length()));
					searchURL(path);
				}
			}
			inStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
