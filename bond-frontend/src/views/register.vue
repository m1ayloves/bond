<template>
  <div class="register-main">
    <div class="register-header">
      <h2>创建账户</h2>
      <p>欢迎来到 Bond!</p>
    </div>
    <div class="register-container">
      <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
        <!-- Username -->
        <a-form-item label="用户名" name="username">
          <a-input v-model:value="form.username" placeholder="输入用户名" />
        </a-form-item>

        <!-- Password -->
        <a-form-item label="密码" name="password">
          <a-input-password
            v-model:value="form.password"
            placeholder="输入密码" />
        </a-form-item>

        <!-- Confirm Password -->
        <a-form-item label="确认密码" name="confirmPassword">
          <a-input-password
            v-model:value="form.confirmPassword"
            placeholder="输入确认密码" />
        </a-form-item>

        <!-- Submit Button -->
        <a-form-item>
          <a-button
            type="primary"
            block
            :loading="loading"
            @click="handleRegister">
            创建
          </a-button>
        </a-form-item>
      </a-form>
    </div>

    <div class="register-footer">
      <p>
        已经注册了账号?
        <a @click.prevent="router.push('/login')" class="login-link">
          点击登录
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/auth-api.js'

const router = useRouter()
const formRef = ref(null)
const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
})
const loading = ref(false)

const validateConfirmPassword = async (_rule, value) => {
  if (value === '') {
    return Promise.reject('请再次输入密码')
  } else if (value !== form.value.password) {
    return Promise.reject('密码输入不一致')
  } else {
    return Promise.resolve()
  }
}

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
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'change' },
  ],
}

const handleRegister = async () => {
  try {
    loading.value = true
    await formRef.value.validate()
    await register(form.value)
    setTimeout(async () => {
      await router.push('/login')
    }, 1000)
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

.register-main {
  margin: 100px auto;
}

.register-container {
  max-width: 300px;
  margin: auto;
  padding: 40px 30px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.register-header h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.register-header p {
  margin-bottom: 20px;
  color: #666;
  font-size: 14px;
}

.register-footer {
  margin-top: 20px;
  color: #666;
  font-size: 14px;
}

.register-footer a {
  color: #1890ff;
  text-decoration: none;
  cursor: pointer;
}

.register-footer a:hover {
  text-decoration: underline;
}
</style>
