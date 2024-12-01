import { useSessionStorage } from '@vueuse/core'
import {
  login as loginApi,
  getUserInfo as getUserInfoApi,
} from '@/api/auth-api.js'
import { ref } from 'vue'

const prefix = 'bond-'

export function useUserStore() {
  const token = useSessionStorage(`${prefix}token`, '')
  const userInfo = ref(undefined)

  function login(form) {
    return new Promise(async (resolve, reject) => {
      try {
        const { data } = await loginApi(form)
        token.value = data.token
        await fetchUserInfo()
        resolve()
      } catch (e) {
        reject(e)
      }
    })
  }

  async function fetchUserInfo() {
    const { data } = await getUserInfoApi()
    userInfo.value = data
  }

  function getToken() {
    return token.value
  }

  function removeToken() {
    token.value = ''
  }

  function getUserInfo() {
    return userInfo.value
  }

  function logout() {
    removeToken()
    userInfo.value = undefined
  }

  return {
    login,
    fetchUserInfo,
    getToken,
    getUserInfo,
    logout,
  }
}
