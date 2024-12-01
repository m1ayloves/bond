<template>
  <div class="center-container">
    <a-card :style="cardStyle" :body-style="{ padding: 0, overflow: 'hidden' }">
      <a-flex justify="space-between">
        <img
          alt="avatar"
          src="/first-screen.webp"
          :style="imgStyle"
          loading="lazy" />
        <a-flex
          vertical
          align="flex-end"
          justify="space-between"
          :style="{ padding: '32px' }">
          <a-typography>
            <a-typography-title :level="3">
              “Relationship, has never been so intuitive.”
              <br />
              <br />
              —— Bond
            </a-typography-title>
          </a-typography>
          <a-button @click="handleClickGetStart" type="primary" target="_blank">
            Get Start
          </a-button>
        </a-flex>
      </a-flex>
    </a-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/use-user-store.js'

const userStore = useUserStore()
const router = useRouter()
const cardStyle = {
  width: '620px',
  boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)', // 添加背景阴影
  borderRadius: '8px', // 可选：为卡片添加圆角
  backgroundColor: 'white', // 保证背景颜色一致
}
const imgStyle = {
  display: 'block',
  width: '273px',
}

function handleClickGetStart() {
  if (userStore.getToken() !== '') {
    userStore.fetchUserInfo().then(() => {
      router.push('/contacts')
    })
  } else {
    router.push('/login')
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

.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 全屏高度 */
  background-color: white; /* 可选：为页面添加一个浅灰背景 */
  margin: 0;
}
</style>
