package com.controller;

import com.Entities.customer;
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
@RequestMapping("/Customer")
public class CustomerController {

    @RequestMapping("/Index")
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("Customer/Index");
        return modelAndView;

    }

    @RequestMapping("/Form")
    public ModelAndView Form() {
        ModelAndView modelAndView = new ModelAndView("Customer/Form");
        return modelAndView;
    }

    @RequestMapping("/Visit")
    public ModelAndView Visit(){
        ModelAndView modelAndView = new ModelAndView("Customer/Visit");
        return modelAndView;
    }

    @RequestMapping("/GetList")
    public String GetList(){

        SQLHelper sqlHelper = new SQLHelper();
        List<Map<String,Object>> datas = sqlHelper.Select_List("customer");
         JSONArray jsonArray = JSONArray.fromObject(datas);
          return jsonArray.toString();
    }
  @RequestMapping("/GetSingle")
     public String GetSingle(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();
      List<Map<String, Object>>  datas = sqlHelper.Select_List("customer","*","id="+request.getParameter("id"));
      JSONArray jsonArray = JSONArray.fromObject(datas);
      return jsonArray.toString();

  }
  @RequestMapping("/Insert")
    public int Insert(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();

      JSONObject jsonObject = JSONObject.fromObject(new PageHelper().GetPost(request));
       customer entity =(customer)JSONObject.toBean(jsonObject,customer.class);

       int result = sqlHelper.Insert("customer",entity);
        return  result;
  }
  @RequestMapping("Update")
    public int Update(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();

      JSONObject object = JSONObject.fromObject((new PageHelper().GetPost(request)));
      customer  entity=(customer)JSONObject.toBean(object,customer.class);

      int result=sqlHelper.Update_Row("customer",entity,"id="+request.getParameter("id"));
      return result;
  }
  @RequestMapping("Delete")
   public int Delete(HttpServletRequest request){
      SQLHelper sqlHelper = new SQLHelper();
       int result = sqlHelper.Delete("customer","id="+ request.getParameter("id"));
        return  result;
  }


}



























