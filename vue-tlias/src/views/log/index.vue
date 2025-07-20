<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
// 假设这里是获取日志列表的 API
import { getLogListApi } from '@/api/log'

// 日志列表数据
const logList = ref([])
// 当前页码
const currentPage = ref(1)
// 每页显示条数
const pageSize = ref(10)
// 总数据条数
const total = ref(0)

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return ''
  return new Date(dateTime).toLocaleString()
}

// 获取日志列表
const fetchLogList = async () => {
  try {
    const result = await getLogListApi(currentPage.value, pageSize.value)
    if (result.code) {
      logList.value = result.data.rows
      total.value = result.data.total
    } else {
      ElMessage.error(result.message || '获取日志列表失败')
    }
  } catch (error) {
    console.error('获取日志列表出错:', error)
    ElMessage.error('获取日志列表出错，请稍后重试')
  }
}

// 监听页码变化
const handleSizeChange = (val) => {
  pageSize.value = val
  fetchLogList()
}

// 监听当前页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchLogList()
}

onMounted(() => {
  fetchLogList()
})
</script>

<template>
  <div>
    <el-table :data="logList" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="operateEmpName" label="操作人姓名" width="120" />
      <el-table-column prop="operateTime" label="操作时间" width="200">
        <template #default="{ row }">
          {{ formatDateTime(row.operateTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="className" label="操作类名" width="180" />
      <el-table-column prop="methodName" label="操作方法名" width="180" />
      <el-table-column prop="methodParams" label="操作方法参数" />
      <el-table-column prop="returnValue" label="操作方法返回值" />
      <el-table-column prop="costTime" label="操作耗时(ms)" width="120" />
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      layout="total, sizes, prev, pager, next, jumper"
    />
  </div>
</template>