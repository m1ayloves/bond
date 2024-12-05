<script setup>
import {
  computed,
  onMounted,
  reactive,
  ref,
  watch,
  getCurrentInstance,
} from 'vue'
import { Modal } from 'ant-design-vue'
import {
  PlusOutlined,
  CloseOutlined,
  FormOutlined,
  StarOutlined,
  EyeOutlined,
  DeleteOutlined,
  ProfileOutlined,
  FileTextOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from '@ant-design/icons-vue'

import { deleteContact, listContact } from '@/api/contacts-api.js'
import { useUserStore } from '@/store/use-user-store.js'
import { searchObjects } from '@/utils/array.js'
import { downloadFile } from '@/utils/request.js'

import AddModal from '@/views/contacts/add-modal.vue'
import AppendTagModel from '@/views/contacts/append-tag-model.vue'
import DetailModal from '@/views/contacts/detail-modal.vue'
import EditModal from '@/views/contacts/edit-modal.vue'

// <!-- 侧边栏 -->
const selectedMenuKey = ref('') // 用于控制侧边栏菜单的选中状态
const sidebarWidth = computed(() => (isSidebarCollapsed.value ? 80 : 200)) // 侧边栏宽度
const isSidebarCollapsed = ref(false) // 用于控制侧边栏是否收缩
function toggleSidebar() {
  isSidebarCollapsed.value = !isSidebarCollapsed.value
}

// <!-- 搜索框 -->
const isFocused = ref(false)
const searchBoxWidth = ref('230px')
const searchBoxLeft = computed(() => `calc(50% + ${sidebarWidth.value / 2}px)`) // 根据侧边栏宽度动态调整
function handleFocus() {
  isFocused.value = true
  searchBoxWidth.value = '530px'
}

function handleBlur() {
  isFocused.value = false
  searchBoxWidth.value = '230px'
  keyword.value = '' // 清空输入框
}

// <!-- Main -->
const { proxy } = getCurrentInstance()
const rawList = ref([])
const keyword = ref('')
const tableData = ref([])
const pagination = reactive({
  current: 1,
  size: 5,
  total: 0,
})
const columns = [
  { title: '序号', dataIndex: 'no', key: 'no' },
  { title: '姓名', dataIndex: 'name', key: 'name' },
  { title: '标签', dataIndex: 'tags', key: 'tags' },
  { title: '联系电话', dataIndex: 'phone', key: 'phone' },
  { title: '操作', key: 'action' },
]

function getTagColor(tag) {
  if (tag.includes('班')) return 'magenta'
  if (tag.length > 6) return 'cyan'
  return 'green'
}

function handleClickAddContact() {
  proxy.$refs['add-modal'].show()
}

function handleClickAppendTag() {
  proxy.$refs['append-tag-model'].show()
}

function handleClickLogout() {
  useUserStore().logout()
  window.location.href = '/login'
}

async function handleClickExportExcel() {
  await downloadFile('/contacts/export/excel', 'contacts.xlsx')
}

async function handleClickExportJson() {
  await downloadFile('/contacts/export/json', 'contacts.json')
}

function handleActionClick(event, id) {
  if (event === 'detail') {
    proxy.$refs['detail-modal'].show(id)
    return
  }
  if (event === 'edit') {
    proxy.$refs['edit-modal'].show(id)
    return
  }
  if (event === 'delete') {
    Modal.confirm({
      title: '你确定要删除此联系人吗?',
      content: ' 这个操作是不可逆的。',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        deleteContact(id).then(() => {
          fetchRawList().then(() => {
            calTableData()
          })
        })
      },
    })
    return
  }
}

watch(keyword, () => {
  pagination.current = 1
  calTableData()
})

function paginateArray(array, page, pageSize) {
  const startIndex = (page - 1) * pageSize
  const endIndex = startIndex + pageSize
  return array.slice(startIndex, endIndex)
}

function calTableData() {
  const filteredData = searchObjects(
    rawList.value,
    ['name', 'tag', 'phone'],
    keyword.value
  )
  pagination.total = filteredData.length
  tableData.value = paginateArray(
    filteredData,
    pagination.current,
    pagination.size
  )
}

async function fetchRawList() {
  const { data } = await listContact({ current: 1, size: 0x7fffffff })
  rawList.value = data.records
}

async function handleModalClose(source) {
  await fetchRawList()
  calTableData()
  if (source === 'add') {
    pagination.current = Math.ceil(pagination.total / pagination.size)
    calTableData()
  }
}

onMounted(async () => {
  await fetchRawList()
  calTableData()
})
</script>

<template>
  <append-tag-model ref="append-tag-model" />
  <add-modal ref="add-modal" @close="handleModalClose('add')" />
  <detail-modal ref="detail-modal" />
  <edit-modal ref="edit-modal" @close="handleModalClose" />

  <a-layout style="height: 100vh">
    <!-- 侧边栏 -->
    <a-layout-sider
      v-model:collapsed="isSidebarCollapsed"
      collapsible
      :trigger="null"
      style="background-color: whitesmoke">
      <div
        class="logo"
        style="
          text-align: center;
          font-weight: bold;
          font-size: 20px;
          color: black;
          padding: 20px 10px 20px 10px;
        ">
        Bond
      </div>
      <a-menu
        theme="light"
        mode="inline"
        style="background-color: whitesmoke"
        :selectedKeys="[selectedMenuKey]">
        <a-menu-item key="logout" @click="handleClickLogout">
          <CloseOutlined />
          <span>退出登录</span>
        </a-menu-item>
        <a-menu-item key="newTag" @click="handleClickAppendTag">
          <StarOutlined />
          <span>新建标签</span>
        </a-menu-item>
        <a-menu-item key="newContact" @click="handleClickAddContact">
          <PlusOutlined />
          <span>新建联系人</span>
        </a-menu-item>
        <a-menu-item key="exportExcel" @click="handleClickExportExcel">
          <ProfileOutlined />
          <span>导出为 Excel</span>
        </a-menu-item>
        <a-menu-item key="exportJson" @click="handleClickExportJson">
          <FileTextOutlined />
          <span>导出为 JSON</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>

    <!-- Main -->
    <a-layout>
      <!-- 头 -->
      <a-layout-header
        style="
          background: whitesmoke;
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 0 20px;
        ">
        <div @click="toggleSidebar" style="cursor: pointer">
          <a-button type="default">
            <template v-if="isSidebarCollapsed">
              <menu-unfold-outlined />
            </template>
            <template v-else>
              <menu-fold-outlined />
            </template>
          </a-button>
        </div>
        <div
          id="searchbox"
          :style="{ width: searchBoxWidth, left: searchBoxLeft }">
          <input
            v-model="keyword"
            autocomplete="off"
            type="text"
            placeholder="搜索内容"
            @focus="handleFocus"
            @blur="handleBlur" />
        </div>
      </a-layout-header>

      <!-- Content -->
      <a-layout>
        <!-- 内容区 -->
        <a-layout-content
          style="
            padding: 0 16px 16px 16px;
            background: whitesmoke;
            flex: 1;
            display: flex;
            flex-direction: column;
          ">
          <!-- 滚动区域 -->
          <div
            style="
              flex: 1;
              overflow-y: auto;
              background: white;
              border-radius: 10px;
              box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
              padding: 20px;
            ">
            <a-table
              :columns="columns"
              :data-source="tableData"
              :pagination="false"
              row-key="key">
              <template #bodyCell="{ column, record, index }">
                <template v-if="column.key === 'no'">
                  <span>
                    {{ (pagination.current - 1) * pagination.size + index + 1 }}
                  </span>
                </template>
                <template v-else-if="column.key === 'name'">
                  <a-avatar style="color: #f56a00; background-color: #fde3cf">
                    {{ record.name.charAt(0).toUpperCase() }}
                  </a-avatar>
                  <a-divider type="vertical" />
                  {{ ' ' + record.name }}
                </template>
                <template v-else-if="column.key === 'tags'">
                  <template v-if="record.tag && record.tag !== ''">
                    <a-tag
                      v-for="tag in record.tag.split(',')"
                      :key="tag"
                      :color="getTagColor(tag)">
                      {{ tag.toUpperCase() }}
                    </a-tag>
                  </template>
                </template>
                <template v-else-if="column.key === 'action'">
                  <a-button
                    type="primary"
                    @click="handleActionClick('detail', record.no)">
                    <EyeOutlined />
                  </a-button>
                  <a-divider type="vertical" />
                  <a-button
                    ghost
                    type="primary"
                    @click="handleActionClick('edit', record.no)">
                    <FormOutlined />
                  </a-button>
                  <a-divider type="vertical" />
                  <a-button
                    ghost
                    danger
                    type="primary"
                    @click="handleActionClick('delete', record.no)">
                    <DeleteOutlined />
                  </a-button>
                </template>
              </template>
            </a-table>
          </div>
          <br />
          <!-- 分页器 -->
          <a-pagination
            v-model:current="pagination.current"
            v-model:pageSize="pagination.size"
            :page-size-options="[
              '5',
              '10',
              '15',
              '20',
              '25',
              '30',
              '35',
              '40',
              '45',
              '50',
            ]"
            show-size-changer
            :show-total="total => `共 ${total} 条记录`"
            :total="pagination.total"
            @change="calTableData" />
        </a-layout-content>

        <!-- footer -->
      </a-layout>
      <a-layout-footer
        style="
          text-align: center;
          background: #f5f5f5;
          padding: 10px;
          bottom: 0;
          left: 0;
          right: 0;
        ">
        © 2024 Bond, made with ❤️
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<style>
body,
html {
  margin: 0;
  padding: 0;
  overflow: hidden; /* 禁止滚动 */
}

#searchbox {
  display: flex;
  align-items: center;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  max-width: 80%;
  height: 40px;
  border-radius: 30px;
  color: #fff;
  background-color: rgba(255, 255, 255, 0.25);
  box-shadow: rgb(0 0 0 / 20%) 0 0 10px;
  backdrop-filter: blur(10px);
  overflow: hidden;
  transition:
    width 0.25s,
    left 0.25s;
}

#searchbox input {
  outline: 0;
  border: none;
  width: 100%;
  height: 100%;
  color: black;
  background-color: transparent;
  font-size: 14px;
  text-align: center;
}

#searchbox input::placeholder {
  color: black;
}
</style>
