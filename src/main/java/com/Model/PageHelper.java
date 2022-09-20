package com.Model;

import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PageHelper {

    public String GetMoudle() {
        SQLHelper sql = new SQLHelper();
        StringBuilder s = new StringBuilder();
        List<Map<String,Object>> ds_Father =sql.Select_List("module","*","classID=0","sort desc");

        for(Map<String,Object> maps : ds_Father) {
            s.append("<li>");
            s.append("<a href='#'>");
            s.append("<span class='nav-label'>" + maps.get("title")+"</span>");
            s.append("</a>");
            s.append("<ul class='nav nav-second-level'>\r\n");
            s.append("<li>");

            List<Map<String,Object>> ds_Child=sql.Select_List("module","*","classID="+maps.get("Id"),"sort desc");
            for(Map<String,Object> maps_child :ds_Child) {
                s.append("<li>");
                s.append("<a class='J_menuItem' href='" +maps_child.get("href") + "'>" +maps_child.get("Title") + "</a>");
                s.append("</li>\r\n");
            }
            s.append("</li>\r\n");
            s.append("</ul>\r\n");
            s.append("</li>\r\n");

        }
        return s.toString();
    }







    public String GetPost(HttpServletRequest request) {
        String s ="";
        InputStream in = null;
        BufferedInputStream bin = null;
        try {
            in = request.getInputStream();
            bin = new BufferedInputStream(in);
            int len = 0;
            byte[] b = new byte[1024];
            while((len = bin.read(b)) != -1) {
                s += new String(b,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }
    public String GetTreeContent(String TableName,String ID_Field,String Title_Field,String ClassID_Field,String TopValue,String BaseURL,String OrderBy) {
        SQLHelper sql = new SQLHelper();
        List<Map<String,Object>> ds = sql.Select_BySQL("select * from "+ TableName +" where "+ClassID_Field +"=" + TopValue +" order by "+OrderBy);

        if(ds != null) {
            StringBuilder s  = new StringBuilder();
            s.append("[");
            for(Map<String,Object> maps :ds) {
                s.append("{'name': '" + maps.get(Title_Field) +"', 'open':'true', 'url':'"+BaseURL + maps.get(ID_Field)+"', 'target':'_self'" );
                if(sql.Select_Exsit(TableName, ClassID_Field +"="+maps.get(ID_Field)))
                {
                    s.append(Tree_Children(maps.get(ID_Field).toString(),TableName,ID_Field,Title_Field,ClassID_Field,BaseURL,OrderBy));


                }
                s.append("},");
            }
            s.append("]");
            return s.toString();
        }
        else {
            return null;
        }

    }
    private String 	Tree_Children(String ParentID,String TableName,String ID_Field,String Title_Field,String ClassID_Field,String BaseURL,String OrderBy)
    {
        SQLHelper sql = new SQLHelper();
        StringBuilder s  = new StringBuilder();
        List<Map<String,Object>> ds = sql.Select_BySQL("select * from "+ TableName +" where "+ClassID_Field+"="+ParentID+" order by "+OrderBy);
        s.append(",children: [");
        for(Map<String,Object>maps :ds) {
            s.append("{'name': '" +maps.get(Title_Field)+"','open':'true', 'url':'" + BaseURL + maps.get(ID_Field) +"','target':'_self'");
            if(sql.Select_Exsit(TableName, ClassID_Field +"=" +maps.get(ID_Field))) {
                s.append(Tree_Children(maps.get(ID_Field).toString(),TableName,ID_Field,Title_Field,ClassID_Field,BaseURL,OrderBy));

            }
            s.append("},");

        }
        s.append("]");
        return s.toString();
    }
    public String GetSelect(String TableName,String ID_Field,String Title_Field,String Condition,String OrderBy) {
        SQLHelper sql = new SQLHelper();
        StringBuilder s = new StringBuilder();
        List<Map<String,Object>> ds = sql.Select_BySQL("select "+ID_Field +" as value, "+Title_Field+" as text from "+TableName+" where "+Condition);
        JSONArray json = JSONArray.fromObject(ds);
        return json.toString();
    }
}
