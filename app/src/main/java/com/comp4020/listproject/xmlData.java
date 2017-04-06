package com.comp4020.listproject;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;
import android.content.Context;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
/**
 * Created by Adam
 */

public class xmlData {
    ArrayList<String> list;
    ArrayList<Integer> levelList;
    String filename;
    XmlSerializer xml;
    Document d;
    Context context;
    int size;
    public xmlData(Context context)
    {
        this.context = context;
        filename = "list.xml";
        list = new ArrayList<String>();
        levelList = new ArrayList<Integer>();
        populate();
        xmlUpdate();
    }
    public xmlData(String filename)
    {
        this.filename = filename;
        list = new ArrayList<String>();
        levelList = new ArrayList<Integer>();
        populate();
    }
    public void setLevel(int position, int level)
    {
        if(position < levelList.size()) {
            if (level <= 3 && level >= 0) {
                levelList.set(position, level);
            }
        }
    }
    public int getLevel(int position)
    {
        return levelList.size() > position ? levelList.get(position) : 5;
    }
    private void populate()
    {
        size = 0;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        String file_path = context.getFilesDir() +"/" + filename;
        File xml_file = new File(file_path);
        try {
            AssetManager ass = context.getAssets();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //InputStream catDoc =  ass.open("list.xml");
            String[] l = ass.list("");

            if(!xml_file.isFile())
                d = builder.parse(ass.open("list.xml",AssetManager.ACCESS_STREAMING));
            else
                d = builder.parse(xml_file);
            d.getDocumentElement().normalize();
            Element root = d.getDocumentElement();
            NodeList nList = d.getElementsByTagName("g_list_item");
            for(int i = 0;i<nList.getLength();i++)
            {
                Node n = nList.item(i);
                Element e = (Element) n;
                list.add(e.getElementsByTagName("name").item(0).getTextContent());
                levelList.add(Integer.parseInt(e.getElementsByTagName("level").item(0).getTextContent()));
            }
            size = list.size();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getSize()
    {
        return size;
    }
    public void add(String name)
    {
        list.add(name);
        levelList.add(0);
        xmlUpdate();
    }
    public String getName(int position)
    {
        return (list.size()> position) ? list.get(position) : null;
    }
    private void xmlUpdate()
    {
        String file_path = context.getFilesDir() +"/" + filename;
        File xml_file = new File(file_path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.newDocument();
            Element root = d.createElement("g_list");
            d.appendChild(root);
            for(int i = 0;i<list.size();i++)
            {
                Element item = d.createElement("g_list_item");
                Element n = d.createElement("name");
                Element l  =d.createElement("level");
                n.appendChild(d.createTextNode(list.get(i)));
                l.appendChild(d.createTextNode(levelList.get(i).toString()));
                item.appendChild(n);
                item.appendChild(l);
                root.appendChild(item);
            }
            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer tformer = tfactory.newTransformer();
            DOMSource source = new DOMSource(d);
            StreamResult result = new StreamResult(xml_file);
            tformer.transform(source,result);

            //fileos = new FileOutputStream(filename);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public void remove(int position)
    {
        list.remove(position);
        levelList.remove(position);
        xmlUpdate();
    }
    public ArrayList<String> getList()
    {
        return list;
    }

}
