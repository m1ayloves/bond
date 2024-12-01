<template>
  <div class="login-main">
    <div class="login-header">
      <h2>登陆账号</h2>
      <p>欢迎来到 Bond!</p>
    </div>
    <div class="login-container">
      <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
        <a-form-item label="用户名" name="username">
          <a-input v-model:value="form.username" placeholder="输入用户名" />
        </a-form-item>
        <a-form-item label="密码" name="password">
          <a-input-password
            v-model:value="form.password"
            placeholder="输入密码" />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            block
            @click="handleLogin"
            :loading="loading">
            登陆
          </a-button>
        </a-form-item>
      </a-form>
    </div>

    <div class="login-footer">
      <p>
        或
        <a @click.prevent="router.push('/register')" class="register-link">
          创建账户
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/use-user-store.js'

const router = useRouter()
const form = ref({
  username: '',
  password: '',
})
const formRef = ref()
const loading = ref(false)
const rules = {
  username: [
    {
      required: true,
      message: '请输入你的用户名',
      trigger: 'change',
    },
  ],
  password: [
    {
      required: true,
      message: '请输入你的密码',
      trigger: 'change',
    },
  ],
}

const handleLogin = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    await useUserStore().login(form.value)
    await router.push('/contacts')
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
body,
html {
  margin: 0;
  padding: 0;
  overflow: hidden; /* 禁止滚动 */
}

.login-main {
  margin: 100px auto;
}

.login-container {
  max-width: 300px;
  margin: 20px auto;
  padding: 40px 30px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-header h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.login-header p {
  margin-bottom: 20px;
  color: #666;
  font-size: 14px;
}

.login-footer {
  margin-top: 20px;
  color: #666;
  font-size: 14px;
}

.login-footer a {
  color: #1890ff;
  text-decoration: none;
}

.register-link {
  color: #1890ff;
  text-decoration: none;
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
}

/* 鼠标悬停样式 */
.register-link:hover {
  text-decoration: underline; /* 悬停时显示下划线 */
}
</style>
