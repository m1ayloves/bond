<script setup>
import { getContactInfo } from '@/api/contacts-api.js'
import { ref } from 'vue'

const open = ref(false)
const form = ref({})

/**
 * 标签变色逻辑
 */
function getTagColor(tag) {
  if (tag.includes('班')) return 'magenta' // 如果包含“班”，返回 magenta
  if (tag.length > 6) return 'cyan' // 如果长度超过 6，返回 cyan
  return 'green' // 默认颜色为 green
}

async function show(id) {
  const { data } = await getContactInfo(id)
  form.value = data
  open.value = true
}

defineExpose({ show })
</script>

<template>
  <a-modal v-model:open="open" title="联系人详情" :footer="null" destroyOnClose>
    <a-descriptions
      bordered
      column="3"
      layout="vertical"
      :labelStyle="{ width: '700px' }"
      :contentStyle="{ width: '1000px' }">
      <a-descriptions-item label="姓名">
        {{ form.name }}
      </a-descriptions-item>
      <a-descriptions-item label="性别">
        {{ form.sex === 'M' ? '男' : '女' }}
      </a-descriptions-item>
      <a-descriptions-item label="生日">
        {{ form.birthday }}
      </a-descriptions-item>
      <a-descriptions-item label="标签" :span="3">
        <template v-if="form.tag">
          <a-tag
            v-for="tag in form.tag.split(',')"
            :key="tag"
            :color="getTagColor(tag)">
            {{ tag.toUpperCase() }}
          </a-tag>
        </template>
      </a-descriptions-item>
      <a-descriptions-item label="联系电话">
        {{ form.phone }}
      </a-descriptions-item>
      <a-descriptions-item label="电子邮箱" span="2">
        {{ form.email }}
      </a-descriptions-item>
      <a-descriptions-item label="地址" :span="3">
        {{ form.address }}
      </a-descriptions-item>
      <a-descriptions-item label="描述" :span="3">
        {{ form.description }}
      </a-descriptions-item>
    </a-descriptions>
  </a-modal>
</template>
