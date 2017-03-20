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
        try {
            AssetManager ass = context.getAssets();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //InputStream catDoc =  ass.open("list.xml");
            String[] l = ass.list("");


            d = builder.parse(ass.open("list.xml",AssetManager.ACCESS_STREAMING));
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
        xmlAdd(list.size()-1,name,3);
    }
    public String getName(int position)
    {
        return (list.size()> position) ? list.get(position) : null;
    }
    private void xmlAdd(int position,String name, int level)
    {
        FileOutputStream fileos = null;
        try{
            fileos = new FileOutputStream(filename);

        } catch(FileNotFoundException e) {
            Log.e("FileNotFoundException",e.toString());
        }
    }
    public void remove(int position)
    {
        list.remove(position);
        levelList.remove(position);
        xmlDelete(position);
    }
    private void xmlDelete(int position)
    {

    }
    public ArrayList<String> getList()
    {
        return list;
    }

}
