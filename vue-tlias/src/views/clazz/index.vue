<script setup>

import { queryPageApi, addApi, updateApi, deleteApi, queryInfoApi } from '@/api/clazz'
import { ref, watch, onMounted } from 'vue';
import { getMaster } from '@/api/emp';
import { ElMessage, ElMessageBox } from 'element-plus';

// 查询条件
const searchClazz = ref({
  name: '',
  begin: '',
  end: '',
  date:[]
})
// 班级信息数据
const clazzList = ref([
  {
    id: '',
    name: 'javafdsfa',
    room: '123',
    beginDate: '2023-01-01',
    endDate: '',
    masterId: '',
    subject: '',
    createTime: '',
    updateTime: '',
    masterName: '',
    status: ''
  }
]);
// 新增班级数据
const clazzForm = ref({
  id: '',
  name: '',
  subject: null,
  room: '',
  beginDate: '',
  endDate: '',
  date:[],
  masterId: ''
})

const masterList = ref([])
// 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6: 嵌入式
const subjects = ref([
  { name: 'java', value: 1 },
  { name: '前端', value: 2 },
  { name: '大数据', value: 3 },
  { name: 'python', value: 4 },
  { name: 'Go', value: 5 },
  { name: '嵌入式', value: 6 }
])
const dialogVisible = ref(false)
const dialogTitle = ref('新增班级')
const clazzFormRef = ref(null)

const rules = ref({
  name: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
  subject: [{ required: true, message: '请选择学科', trigger: 'change' }],
  room: [{ required: true, message: '请输入教室', trigger: 'blur' }],
  date: [{ required: true, message: '请选择日期', trigger: 'change' }],
  masterId: [{ required: true, message: '请选择班主任', trigger: 'change' }]
})

// 页面显示
const currentPage = ref(1) // 当前页
const pageSize = ref(10) // 每页显示条数
const total = ref(0)


onMounted(async () => {
  search()
  console.log('页面加载完成，查询班级列表'+subjects.value);
  // 获取所有班主任
  const result = await getMaster()
  if(result.code){
    masterList.value = result.data
  }
  console.log('masterList', masterList.value);
})

const handleSizeChange = (val) => {
  search()
}
const handleCurrentChange = (val) => {
  search()
}

watch(
  () => clazzForm.value.date,
  (newValue, oldValue) => {
    if (newValue.length == 2) {
      clazzForm.value.beginDate = newValue[0]
      clazzForm.value.endDate = newValue[1]
    } else {
      clazzForm.value.beginDate = ''
      clazzForm.value.endDate = ''
    }
  }
)

watch(
  () => searchClazz.value.date,
  (newValue, oldValue) => {
    if (newValue.length == 2) {
      searchClazz.value.begin = newValue[0]
      searchClazz.value.end = newValue[1]
    } else {
      searchClazz.value.begin = ''
      searchClazz.value.end = ''
    }
  }
)


// 查询班级列表
const search = async () => {
  // 调用查询接口
  console.log('查询班级列表', searchClazz.value);
  const result = await queryPageApi(searchClazz.value.name, currentPage.value, pageSize.value, searchClazz.value.begin, searchClazz.value.end)
  if(result.code){
    clazzList.value = result.data.rows
    total.value = result.data.total
  }
  console.log('查询班级列表', searchClazz.value);
}
// 重置查询条件
const reset = () => {
  searchClazz.value.name = ''
  searchClazz.value.begin = ''
  searchClazz.value.end = ''
  searchClazz.value.date = []
  search()
}

const save = async () => {
  console.log('新增班级', clazzForm.value);
  // 校验
  clazzFormRef.value.validate( async valid => {
    if(valid){
      let result
      if(clazzForm.value.id){
        // 编辑
        result = await updateApi(clazzForm.value)   
      }else{
        // 新增
        result = await addApi(clazzForm.value)
      }
      if(result.code){
        if(clazzForm.value.id){
          ElMessage.success('编辑班级成功')
        }else{
          ElMessage.success('新增班级成功')
        }
        dialogVisible.value = false
        search()
      }else{
        ElMessage.error(result.msg)
      }
      
    }
  }

  )
}

const closeDialog = () => {
  dialogVisible.value = false
  clazzForm.value = {
    id: '',
    name: '',
    subject: null,
    room: '',
    beginDate: '',
    endDate: '',
    date:[],
    masterId: ''
  }
}

const edit = async (id) => {
  console.log('编辑班级', id);
  // 调用查询接口
  const result = await queryInfoApi(id)
  if(result.code){
    clazzForm.value = result.data
    dialogTitle.value = '编辑班级'
    dialogVisible.value = true
    // 对日期进行处理
    clazzForm.value.date = [clazzForm.value.beginDate, clazzForm.value.endDate]
  }else{
    ElMessage.error(result.msg)
  }
}

const deleteById = async (id) => {
  console.log('删除班级', id);
  ElMessageBox.confirm('确认删除该班级吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const result = await deleteApi(id)
    if(result.code){
      ElMessage.success('删除班级成功')
      search()
    }else{
      ElMessage.error(result.msg)
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

</script>

<template>
  <h1>班级管理</h1> <br>
  <!-- 班级查询条件 -->
  <el-form :inline="true" :model="searchClazz">
    <el-form-item label="班级名称">
      <el-input v-model="searchClazz.name" placeholder="请输入班级"></el-input>
    </el-form-item>

    <el-form-item label="开班日期">
      <el-date-picker v-model="searchClazz.date" type="daterange" range-separator="至" start-placeholder="开始日期"
        end-placeholder="结束日期" value-format="YYYY-MM-DD"></el-date-picker>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button @click="reset">清空</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="dialogVisible = true"> + 新增班级</el-button>
  <br><br>

  <!-- 班级列表 -->
  <el-table :data="clazzList" border style="width: 100%">
    <el-table-column label="序号" align="center">
      <template #default="scope">
        {{ scope.$index + 1 }}
      </template>
    </el-table-column>
    <el-table-column label="班级名称" prop="name" align="center"></el-table-column>
    <el-table-column label="班级教室" prop="room" align="center"></el-table-column>
    <el-table-column label="班主任" prop="masterName" align="center"></el-table-column>
    <el-table-column label="开课时间" prop="beginDate" align="center"></el-table-column>
    <el-table-column label="结课时间" prop="endDate" align="center"></el-table-column>
    <el-table-column label="状态" prop="status" align="center"></el-table-column>
    <el-table-column label="最后操作时间" prop="updateTime" width="210" align="center"></el-table-column>
    <el-table-column label="操作" fixed="right" align="center">
      <template #default="scope">
        <el-button size="small" type="primary" @click="edit(scope.row.id)">编辑</el-button>
        <el-button size="small" type="danger" @click="deleteById(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <br>

  <!-- 分页 -->
  <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
    v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 30]"
    layout="total, sizes, prev, pager, next, jumper" :total="total">
  </el-pagination>

  <!-- 新增/修改班级对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="30%">
    <el-form ref="clazzFormRef" :rules="rules" :model="clazzForm" >
      <el-form-item label="班级名称" prop="name">
        <el-input v-model="clazzForm.name" placeholder="请输入班级名称，2-20个字"></el-input>
      </el-form-item>
      <el-form-item label="班级教室" prop="room">
        <el-input v-model="clazzForm.room" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="开班日期">
        <el-date-picker v-model="clazzForm.date" type="daterange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期" value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>
      <el-form-item label="班主任" prop="masterId">
        <el-select v-model="clazzForm.masterId" placeholder="请选择班主任" style="width: 100%;">
          <el-option v-for="master in masterList" :key="master.id" :label="master.name" :value="master.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学科" prop="subject">
        <el-select v-model="clazzForm.subject" placeholder="请选择学科" style="width: 100%;">
          <el-option v-for="subject in subjects" :key="subject.name" :label="subject.name"
            :value="subject.value"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>

  </el-dialog>

</template>

<style scoped>

</style>