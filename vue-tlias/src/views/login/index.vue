<script setup>
  import { ref } from 'vue'
  import {loginApi} from '@/api/login'
  import { ElMessage } from 'element-plus'
  import { useRouter } from 'vue-router'
  
  let loginForm = ref({username:'', password:''})

  let router = useRouter()

  //登录
  const login = async () => {
    const result = await loginApi(loginForm.value)
    if (result.code) {// 登录成功
      ElMessage.success('登录成功')
      localStorage.setItem('loginUser', JSON.stringify(result.data))
      router.push('/')// 跳转
    }else {
      ElMessage.error(result.msg)
    }
  }

  //取消
  const clear = () => {
    loginForm.value = {
      username: '',
      password: ''
    }
  }
  
</script>

<template>
  <div id="container">
    <div class="login-form">
      <el-form label-width="80px">
        <p class="title">Tlias智能学习辅助系统</p>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">登 录</el-button>
          <el-button class="button" type="info" @click="clear">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
#container {
  padding: 10%;
  height: 410px;
  background-image: url('../../assets/bg1.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}

.login-form {
  max-width: 400px;
  padding: 30px;
  margin: 0 auto;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  background-color: white;
}

.title {
  font-size: 30px;
  font-family: '楷体';
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
}

.button {
  margin-top: 30px;
  width: 120px;
}
</style>