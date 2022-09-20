package com.controller;


import com.Entities.treeform;
import com.Model.PageHelper;
import com.Model.SQLHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TreeForm")
public class TreeFormController {
    @RequestMapping("/Index")
    public ModelAndView Index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("TreeForm/Index");
        String tree = new PageHelper().GetTreeContent("treeform", "id", "ex_TextBox", "parentID", " 0", "/TreeForm/Index?TreeID=", "id desc");
        modelAndView.addObject("TreeContent", tree);
        return modelAndView;
    }

@RequestMapping("/GetSingle")
 public String GetSingle(HttpServletRequest request){
    SQLHelper sqlHelper = new SQLHelper();
    List<Map<String,Object>> datas = sqlHelper.Select_List("treeform","*","id="+request.getParameter("id"));
    JSONArray jsonArray = JSONArray.fromObject(datas);
     return jsonArray.toString();

 }
 @RequestMapping("/GetSelect")
 public String GetSelect(HttpServletRequest request) {
     String select = new PageHelper().GetSelect("treeform", "id", "ex_TextBox","1=1", "id desc");
     return select;
 }
    @RequestMapping("/Insert")
    public int Insert(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        JSONObject object = JSONObject.fromObject(new PageHelper().GetPost(request));
        treeform entity = (treeform)JSONObject.toBean(object,treeform.class);
        int result = sql.Insert("treeform", entity);
        return result;
    }

    @RequestMapping("/Update")
    public int Update(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();

        JSONObject object = JSONObject.fromObject(new PageHelper().GetPost(request));
        treeform entity =(treeform)JSONObject.toBean(object,treeform.class);
        int result = sql.Update_Row("treeform", entity, "id=" + request.getParameter("id"));
        return result;
    }
    @RequestMapping("/Delete")
    public int Delete(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        int result = sql.Delete("treeform", "id=" + request.getParameter("id"));
        return result;
    }

}