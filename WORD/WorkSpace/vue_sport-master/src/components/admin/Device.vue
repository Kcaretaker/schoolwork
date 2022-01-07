<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>设备管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>
      <el-row :gutter="25">
        <el-col :span="10">
          <!-- 搜索添加 -->
          <!-- <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getAll">查询产品</el-button>
          </el-input> -->
        </el-col>

        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加产品</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="getAll">查询产品</el-button>
          
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="dele = true">删除产品</el-button>
          
        </el-col>
        
      </el-row>
      
    </el-card>
    <!-- 创建新用户对话框 -->
    <el-dialog title="添加设备" :visible.sync="addDialogVisible" width="50%" >
      <!-- 内容主体区域 -->
      <el-form :model="addForm" 
        ref="addFormRef" label-width="70px">
        <!-- 用户名 -->
        <el-form-item label="产品名" prop="productname">
          <el-input v-model="addForm.productname" ref="name"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="产品type" prop="productkey">
           <el-input placeholder="输入0（设备）或者1（网关）" v-model="addForm.productkey" ref="type"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="添加设备" :visible.sync="dele" width="50%" >
      <!-- 内容主体区域 -->
      <el-form :model="addForm" 
        ref="addFormRef" label-width="70px">
        <!-- 用户名 -->
        <el-form-item label="产品KEY" prop="productname">
          <el-input v-model="addForm.productname" ref="key"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="delel">确 定</el-button>
      </span>
    </el-dialog>
  <div>
    <ul v-for="(items, index) in datalist" v-bind:key="index">
    <li>KEY ：{{items.productKey}}</li>
    <li>产品名称{{items.productName}}</li>
    </ul>
  </div>
    
  </div>
</template>
<script>
const axios = require('axios');
export default {
  data() {
    
    return {
      // 请求数据
      queryInfo: {
        query: "",
        pageNum: 1,
        pageSize: 5,
        datalist:[12,"name"]
      },

      userlist: [],// 用户列表
     
      total: 0, // 最大数据记录
      addDialogVisible: false ,// 对话框显示
      // 添加用户表单项
      addForm: {
        username:'',
        password:'',
      }, 
      // 控制修改用户对话框显示/隐藏
      editDialogVisible:false,
      dele:false,
      // 修改用户信息
      editForm:{},
    };
  },
  
  methods: {
    
     getAll() {
      var v ;
      // 调用post请求
       axios.get('/getAll').then(response => {
         this.datalist = response.data.data.list
         console.log(response)
         console.log(this.datalist);
       })
       
    },
    async addUser(){
          var da={
            "name":this.$refs.name.value,
            "type":this.$refs.type.value
          }
        const{data:res} = await this.$http.put("creat",da);
        console.log(this.$refs.name.value);
        console.log(this.$refs.type.value);
    },
    // 展示修改框
    async showEditDialog(id){
        this.editDialogVisible = true;
        this.dele = true;
    },
    // 关闭窗口
    editDialogClosed(){
      this.$refs.editFormRef.resetFields();
    },
    // 确认修改
   async delel(){
          var da={
            "key":this.$refs.key.value,
          }
        const{data:res} = await this.$http.put("dele",da);
        console.log(this.$refs.key.value);
    },
    editUserInfo(){
     
        this.editDialogVisible = false;
    },
  }
};
</script>
<style lang="less" scoped>
</style>