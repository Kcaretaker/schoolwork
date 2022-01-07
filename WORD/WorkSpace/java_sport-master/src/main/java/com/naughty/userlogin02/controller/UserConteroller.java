package com.naughty.userlogin02.controller;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.iot.model.v20180120.DeleteProductRequest;
import com.aliyuncs.iot.model.v20180120.DeleteProductResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import java.util.*;
import com.aliyuncs.iot.model.v20180120.*;
import com.aliyun.iot20180120.models.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.iot.model.v20180120.CreateProductRequest;
import com.aliyuncs.iot.model.v20180120.CreateProductResponse;
import com.aliyuncs.iot.model.v20180120.QueryProductListRequest;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.naughty.userlogin02.bean.QueryInfo;
import com.naughty.userlogin02.bean.User;
import com.naughty.userlogin02.dao.UserDao;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import java.util.*;
import com.aliyuncs.iot.model.v20180120.*;


@RestController
public class UserConteroller {
    
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/allUser")
    public String getUserList(QueryInfo queryInfo){
        System.out.println(queryInfo);
        int numbers = userDao.getUserCounts("%"+queryInfo.getQuery()+"%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        List<User> users = userDao.getAllUser("%"+queryInfo.getQuery()+"%",pageStart,queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",users);
        System.out.println("总条数："+numbers);
        String users_json = JSON.toJSONString(res);
        return users_json;
    }

//    @RequestMapping("/userState")
//    public String updateUserState(@RequestParam("id") Integer  id,
//                                  @RequestParam("state") Boolean state){
//        int i = userDao.updateState(id, state);
//        System.out.println("用户编号:"+id);
//        System.out.println("用户状态:"+state);
//        String str = i >0?"success":"error";
//        return str;
//    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user){
        System.out.println(user);
//        user.setRole("普通用户");
//        user.setState(false);
        int i = userDao.addUser(user);
        String str = i >0?"success":"error";
        return str;
    }

    @RequestMapping("/getUpdate")
    public String getUpdateUser(int id){
        System.out.println("编号:"+id);
        User updateUser = userDao.getUpdateUser(id);
        String users_json = JSON.toJSONString(updateUser);
        return users_json;
    }

    @RequestMapping("/editUser")
    public String editUser(@RequestBody User user){
        System.out.println(user);
        int i = userDao.editUser(user);
        String str = i >0?"success":"error";
        return str;
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id){
        System.out.println(id);
        int i = userDao.deleteUser(id);
        String str = i >0?"success":"error";
        return str;
    }
    @RequestMapping("/creat")
    public void creat(@RequestBody JSONObject s){
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI5tCN8Jc9nEpUikrsqTkS", "exO14Ijsk8MOD4dpHjguIfCpakDCpy");
        IAcsClient client = new DefaultAcsClient(profile);
        System.err.println(s );
        CreateProductRequest request = new CreateProductRequest();
        request.setProductName(s.getString("name"));
        request.setNodeType(s.getInteger("type"));
        request.setIotInstanceId("iot-06z00dp0i0sriu3");
        try {
            CreateProductResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public static JSONObject getAll() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI5tCN8Jc9nEpUikrsqTkS", "exO14Ijsk8MOD4dpHjguIfCpakDCpy");
        IAcsClient client = new DefaultAcsClient(profile);

        QueryProductListRequest re = new QueryProductListRequest();
        re.setPageSize(12);
        re.setCurrentPage(1);
        re.setIotInstanceId("iot-06z00dp0i0sriu3");
        QueryProductListResponse res = new QueryProductListResponse();

        try {
            QueryProductListResponse response = client.getAcsResponse(re);
            res = response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        System.err.println(new Gson().toJson(res));
        String st = new Gson().toJson(res);

        JSONObject jsonObject = JSONObject.parseObject(st);
        System.err.println(jsonObject.getJSONObject("data").getJSONArray("list"));
        return jsonObject;
    }
    @RequestMapping("/dele")
    public static void dele(@RequestBody JSONObject s) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI5tCN8Jc9nEpUikrsqTkS", "exO14Ijsk8MOD4dpHjguIfCpakDCpy");

        IAcsClient client = new DefaultAcsClient(profile);
        System.err.println(s);
        DeleteProductRequest request = new DeleteProductRequest();
        request.setProductKey(s.getString("key"));
        request.setIotInstanceId("iot-06z00dp0i0sriu3");

        try {
            DeleteProductResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }
}
