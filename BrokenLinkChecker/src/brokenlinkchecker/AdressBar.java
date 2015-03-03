package brokenlinkchecker;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class AdressBar extends JSplitPane implements ActionListener{
	private FileList flist;
	private BrokenLinkList bllist;
	private LinkSourceList lslist;
	private JTextField adressBar=new JTextField("http://");
	private String base;
	private Vector<URL> check=new Vector<URL>();

	public AdressBar(FileList f,BrokenLinkList b,LinkSourceList l) {
		super(HORIZONTAL_SPLIT);
		flist=f;
		bllist=b;
		lslist=l;
		adressBar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent event){
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
		if(base.indexOf("\\")!=-1){
			if(base.startsWith("file")){
				base=base.substring(0, base.lastIndexOf("\\")+1);
			}
			else{
				base="file:/"+base.substring(0, base.lastIndexOf("\\")+1);
			}
		}
		else{
			base=base.substring(0, base.lastIndexOf("/")+1);
		}
		try {
			URL url;
			if(base.startsWith("file")){
				url=new URL(new URL(base),adressBar.getText().substring(adressBar.getText().lastIndexOf("\\")+1));
			}
			else{
				url=new URL(new URL(base),adressBar.getText().substring(base.length()));
			}
			new Thread(){
				@Override
				public void run(){
					flist.addPath(adressBar.getText());
					searchURL(url,adressBar.getText());
				}
			}.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void searchURL(URL url,String source){
		System.out.println("search:"+url.toString());
		try {
			URLConnection connection = url.openConnection();
			try{
				InputStream inStream = connection.getInputStream();
				if(check.contains(url)==false &&
						(url.toString().endsWith("html") || url.toString().endsWith("htm") ||
								url.toString().endsWith("/"))){
					check.add(url);
					BufferedReader input =new BufferedReader(new InputStreamReader(inStream));
					String str="";
					while((str=input.readLine())!=null){
						if(str.indexOf("<a")!=-1){
							str=str.substring(str.indexOf("href=\"")+"href=\"".length(),
									str.indexOf("\"",str.indexOf("href=\"")+"href=\"".length()));
							URL path2=new URL(url,str);
							System.out.println("   hit:"+path2);
							System.out.println("source:"+url.toString());
							System.out.println();
							if(!str.startsWith("http")){
								flist.addPath(path2.toString().substring(base.length()));
							}
							searchURL(path2,url.toString().substring(base.length()));
						}
					}
					inStream.close();
				}
				else{
					System.out.println("  pass:"+url.toString());
					System.out.println();
				}
			} catch(FileNotFoundException e){
				bllist.addLink(url.toString().substring(base.length()),"ファイルが見つかりません。");
				lslist.addSource(url.toString().substring(base.length()), source);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
