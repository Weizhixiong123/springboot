package com.controller;

import com.Entities.standard;
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
@RequestMapping("/Standard")
public class StandardController {

    @RequestMapping("/Index")
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("Standard/Index");
        return modelAndView;

    }

    @RequestMapping("/Form")
    public ModelAndView Form() {
        ModelAndView modelAndView = new ModelAndView("Standard/Form");
        return modelAndView;
    }

    @RequestMapping("/Visit")
    public ModelAndView Visit(){
        ModelAndView modelAndView = new ModelAndView("Standard/Visit");
        return modelAndView;
    }

    @RequestMapping("/GetList")
    public String GetList(){

        SQLHelper sqlHelper = new SQLHelper();
        List<Map<String,Object>> datas = sqlHelper.Select_List("standard");
         JSONArray jsonArray = JSONArray.fromObject(datas);
          return jsonArray.toString();
    }
  @RequestMapping("/GetSingle")
     public String GetSingle(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();
      List<Map<String, Object>>  datas = sqlHelper.Select_List("standard","*","id="+request.getParameter("id"));
      JSONArray jsonArray = JSONArray.fromObject(datas);
      return jsonArray.toString();

  }
  @RequestMapping("/Insert")
    public int Insert(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();

      JSONObject jsonObject = JSONObject.fromObject(new PageHelper().GetPost(request));
       standard entity =(standard)JSONObject.toBean(jsonObject,standard.class);

       int result = sqlHelper.Insert("standard",entity);
        return  result;
  }
  @RequestMapping("Update")
    public int Update(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();

      JSONObject object = JSONObject.fromObject((new PageHelper().GetPost(request)));
      standard  entity=(standard)JSONObject.toBean(object,standard.class);

      int result=sqlHelper.Update_Row("standard",entity,"id="+request.getParameter("id"));
      return result;
  }
  @RequestMapping("Delete")
   public int Delete(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();
       int result = sqlHelper.Delete("standard","id="+ request.getParameter("id"));
        return  result;
  }


}



























