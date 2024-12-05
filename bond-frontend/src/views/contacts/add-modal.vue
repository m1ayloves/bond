<script setup>
import {saveContact} from '@/api/contacts-api.js'
import {listTag} from '@/api/user-api.js'
import {ref, watch} from 'vue'

const emit = defineEmits(['close'])
const formRef = ref()
const open = ref(false)
const form = ref({})
const tagList = ref([])
const selectedTagList = ref([])
const rules = {
  name: [{required: true, message: 'Name is required', trigger: 'change'}],
}

// 初始化表单方法
function resetForm() {
  form.value = {}
  selectedTagList.value = []
}

async function show() {
  await fetchTagList()
  resetForm() // 打开表单时重置
  open.value = true
}

async function fetchTagList() {
  const {data} = await listTag()
  tagList.value = data
}

/**
 * 处理标签在表单中的"更改"逻辑
 */
function handleTagChange(tag, checked) {
  if (checked) {
    if (selectedTagList.value.indexOf(tag) === -1) {
      selectedTagList.value.push(tag)
    }
  } else {
    selectedTagList.value = selectedTagList.value.filter(
        selectedTag => selectedTag !== tag
    )
  }
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
    if (selectedTagList.value.length === 0) {
      form.value.tag = null // 或设置为其他默认值，例如 'Default'
    } else {
      form.value.tag = selectedTagList.value.join(',')
    }
    // const tag = selectedTagList.value.join(',')
    // form.value.tag = tag
    await saveContact(form.value)
    open.value = false
    resetForm() // 提交成功后重置表单
  } catch (e) {
    console.error(e)
  }
}

function afterClose() {
  emit('close')
  resetForm() // 关闭表单时重置
}

defineExpose({show})
</script>

<template>
  <a-modal
      v-model:open="open"
      :afterClose="afterClose"
      title="编辑联系人"
      :footer="null"
      destroyOnClose>
    <a-form ref="formRef" :model="form" :rules="rules" :layout="'vertical'">
      <!-- 第一行：姓名、性别、生日 -->
      <a-row :gutter="10">
        <a-col :span="8">
          <a-form-item label="姓名" name="name">
            <a-input v-model:value="form.name" placeholder="输入姓名"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="性别" name="sex">
            <a-select v-model:value="form.sex" placeholder="选择性别">
              <a-select-option value="M">男</a-select-option>
              <a-select-option value="F">女</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="生日" name="birthday">
            <a-date-picker
                v-model:value="form.birthday"
                valueFormat="YYYY-MM-DD"
                type="date"
                placeholder="选择生日"
                style="width: 100%"/>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 第二行：标签 -->
      <a-row :gutter="10" justify="start" align="top">
        <a-col :span="24">
          <a-form-item label="标签" name="tag">
            <div style="text-align: left">
              <a-checkable-tag
                  v-for="tag in tagList"
                  :key="tag"
                  :checked="selectedTagList.indexOf(tag) !== -1"
                  @change="checked => handleTagChange(tag, checked)"
                  style="
                  border: 1px solid #d9d9d9;
                  border-radius: 4px;
                  padding: 4px 8px;
                  margin-right: 4px;
                  margin-bottom: 4px;
                ">
                {{ tag }}
              </a-checkable-tag>
            </div>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 第三行：联系电话、电子邮箱 -->
      <a-row :gutter="10">
        <a-col :span="12">
          <a-form-item label="联系电话" name="phone">
            <a-input v-model:value="form.phone" placeholder="输入联系电话"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="电子邮箱" name="email">
            <a-input v-model:value="form.email" placeholder="输入电子邮箱"/>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 第四行：地址 -->
      <a-row :gutter="10">
        <a-col :span="24">
          <a-form-item label="地址" name="address">
            <a-input v-model:value="form.address" placeholder="输入地址"/>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 第五行：描述 -->
      <a-row :gutter="10">
        <a-col :span="24">
          <a-form-item label="描述" name="description">
            <a-textarea
                v-model:value="form.description"
                placeholder="输入描述"/>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 按钮 -->
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
