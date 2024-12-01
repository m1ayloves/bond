<script setup>
import { appendTag, listTag } from '@/api/user-api.js'
import { ref } from 'vue'

const formRef = ref()
const open = ref(false)
const form = ref({
  tag: undefined,
})
const tagList = ref([])
const rules = {
  tag: [{ required: true, message: '标签名不能为空', trigger: 'blur' }],
}

// 重置表单
function resetForm() {
  form.value.tag = undefined
}

async function fetchTagList() {
  const { data } = await listTag()
  tagList.value = data
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
    await appendTag(form.value)
    open.value = false
    resetForm() // 提交后重置表单
  } catch (e) {
    console.error(e)
  }
}

async function show() {
  await fetchTagList()
  resetForm() // 打开表单时重置
  open.value = true
}

defineExpose({ show })
</script>

<template>
  <a-modal v-model:open="open" title="添加标签" :footer="null" destroyOnClose>
    <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
      <a-row :gutter="10">
        <a-col :span="24">
          <a-form-item label="已有标签">
            <div>
              <template v-for="tag in tagList" :key="tag">
                <a-tag style="margin-bottom: 8px">{{ tag }}</a-tag>
              </template>
            </div>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="10">
        <a-col :span="24">
          <a-form-item label="新增标签" name="newTag">
            <a-input v-model:value="form.tag" placeholder="输入新标签名称" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24" style="text-align: left">
          <a-form-item>
            <a-button type="primary" @click="handleSubmit">确认</a-button>
            <a-button style="margin-left: 10px" @click="open = false">
              取消
            </a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
