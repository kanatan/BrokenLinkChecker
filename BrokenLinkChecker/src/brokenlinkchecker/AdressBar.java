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
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class AdressBar extends JSplitPane implements ActionListener{
	private FileList flist;
	private BrokenLinkList bllist;
	private LinkSourceList lslist;
	private JTextField adressBar=new JTextField("http://");
	private String base;
	private Vector<URL> check=new Vector<URL>();
	class Search{
		String start,path,end,tag;

		Search(String s,String p,String e,String t){
			start=s;
			path=p;
			end=e;
			tag=t;
		}
	}
	Search[] search=new Search[6];
	int fcount=0,bcount=0,gcount=0;


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
		search[0]=new Search("<a ", "href=\"","\"", "aタグ");
		search[1]=new Search("<img ", "src=\"","\"", "imgタグ");
		search[2]=new Search("<img ", "this.src'","'", "imgタグ");
		search[3]=new Search("<iframe ", "href=\"","\"", "iframeタグ");
		search[4]=new Search("<link ", "href=\"","\"", "linkタグ");
		search[5]=new Search("<script ", "src=\"","\"", "scriptタグ");
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
					searchURL(url,adressBar.getText(),"アドレスバー");
					JOptionPane.showMessageDialog(null, "　ファイル数："+fcount+"\n"
							+"リンク切れ数："+bcount+"\n"
							+"外部リンク数："+gcount, "リンク切れチェッカー", JOptionPane.PLAIN_MESSAGE);
				}
			}.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void searchURL(URL url,String source,String tag){
		//System.out.println("search:"+url.toString());
		try {
			URLConnection connection = url.openConnection();
			try{
				InputStream inStream = connection.getInputStream();
				if(check.contains(url)==false &&
						(url.toString().endsWith("html") || url.toString().endsWith("htm") ||
								url.toString().endsWith("/"))){
					check.add(url);
					BufferedReader input =new BufferedReader(new InputStreamReader(inStream));
					String html="";
					{
						String str="";
						while((str=input.readLine())!=null){
							html+=str;
						}
					}
					inStream.close();
					while(true){
						int n=html.indexOf("<");
						if(n==-1){
							break;
						}
						html=html.substring(n);
						if((n=checkStart(html))!=-1){
							if(n==1){
								;
							}
							else{
								String p=html.substring(html.indexOf(search[n].path)+search[n].path.length(),
										html.indexOf(search[n].end,html.indexOf(search[n].path)+search[n].path.length()));
								try {
									URL path=new URL(url,p);
									//System.out.println("   hit:"+path);
									//System.out.println("source:"+url.toString());
									//System.out.println();
									if(!p.startsWith("http")){
										if(check.contains(path)==false){
											flist.addPath(path.toString().substring(base.length()));
										}
										searchURL(path,url.toString().substring(base.length()),search[n].tag);
										fcount++;
									}
									else{
										bllist.addLink(path.toString(),"外部リンク：未探索",search[n].tag);
										lslist.addSource(path.toString(), source);
										gcount++;
										//System.out.println(" pass2:"+path.toString());
										//System.out.println();
									}
								} catch (MalformedURLException e) {
									//e.printStackTrace();
									bllist.addLink(p,p.split(":")[0],search[n].tag);
									lslist.addSource(p, source);
								}
							}
						}
						html=html.substring(html.indexOf(">"));
					}
				}
				else{
					//System.out.println(" pass1:"+url.toString());
					//System.out.println();
				}
			} catch(FileNotFoundException e){
				bllist.addLink(url.toString().substring(base.length()),"ファイルが見つかりません",tag);
				lslist.addSource(url.toString().substring(base.length()), source);
				bcount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int checkStart(String html){
		for(int n=0;n<search.length;n++){
			if(html.startsWith(search[n].start)){
				return n;
			}
		}
		return -1;
	}
}
