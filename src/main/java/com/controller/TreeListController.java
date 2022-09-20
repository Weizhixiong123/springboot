package com.controller;


import com.Entities.treelist;
import com.Model.PageHelper;
import com.Model.SQLHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.Model.SQLHelper;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/TreeList")
public class TreeListController {
    @RequestMapping("/Index")
    public ModelAndView Index(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("TreeList/Index");
        String tree = new PageHelper().GetTreeContent("treeform", "id", "ex_TextBox", "parentID"," 0", "/TreeList/Index?TreeID=", "id desc");
        mv.addObject("TreeContent",tree);
        return mv;
    }
    @RequestMapping("/Form")
    public ModelAndView Form() {
        ModelAndView mv = new ModelAndView("TreeList/Form");
        return mv;
    }
    @RequestMapping("/GetList")
    public String GetList(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        List<Map<String,Object>>datas= sql.Select_List("treelist","treeID="+request.getParameter("id"));

        JSONArray json = JSONArray.fromObject(datas);
        return json.toString();
    }
    @RequestMapping("/GetSingle")

    public String GetSingle(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        List<Map<String,Object>> datas = sql.Select_List("treelist","*","id="+request.getParameter("id"));
        JSONArray json = JSONArray.fromObject(datas);
        return json.toString();

    }
    @RequestMapping("/Insert")
    public int Insert(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        JSONObject object = JSONObject.fromObject(new PageHelper().GetPost(request));
        treelist entity = (treelist)JSONObject.toBean(object,treelist.class);
        int result = sql.Insert("treelist", entity);
        return result;
    }

    @RequestMapping("/Update")
    public int Update(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();

        JSONObject object = JSONObject.fromObject(new PageHelper().GetPost(request));
        System.out.println(request.getParameter("id"));
        treelist entity =(treelist)JSONObject.toBean(object,treelist.class);
        int result = sql.Update_Row("treelist", entity, "id=" + request.getParameter("id"));
        return result;
    }
    @RequestMapping("/Delete")
    public int Delete(HttpServletRequest request) {
        SQLHelper sql = new SQLHelper();
        int result = sql.Delete("treelist", "id=" + request.getParameter("id"));
        return result;
    }


}
