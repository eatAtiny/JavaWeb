<script setup>
  import { ref, watch, onMounted } from 'vue'
  import { ElMessageBox, ElMessage } from 'element-plus'
  import { queryPageApi } from '@/api/student'


  /** 列表查询展示 ************************************************************/
  // 搜索数据
  const searchFrom = ref({
    name: '',
    degree: '',
    classId: ''
  })
  //分页数据
  const pageSize = ref(10)
  const currentPage = ref(1)
  const total = ref(0)
  // 分页处理
  const handleSizeChange = async (val) => {
    search()
  }
  const handleCurrentChange = async (val) => {
    search()
  }
  // 学员列表数据
  const stuList = ref([
    {
      id: 1,
      name: '段誉',
      no: '2022000001',
      gender: 1,
      phone: '18800000001',
      idCard: '110120000300200001',
      isCollege: 1,
      address: '北京市昌平区建材城西路1号',
      degree: 1,
      graduationDate: '2021-07-01',
      clazzId: 2,
      violationCount: 1,
      violationScore: 10,
      createTime: '2024-11-14T21:22:19',
      updateTime: '2025-07-03T11:23:41',
      clazzName: null
    }
  ])

  onMounted(() => {
    search()
  })

  const search = async () => {
    const result = await queryPageApi(searchFrom.value.name, currentPage.value, pageSize.value, searchFrom.value.degree, searchFrom.value.classId)
    if (result.code){
      stuList.value = result.data.rows
      total.value = result.data.total
    }else{
      ElMessage.error(result.msg)
    }
  }
  // 清空查询条件
  const clear = () => {
    searchFrom.value.name = ''
    searchFrom.value.degree = ''
    searchFrom.value.classId = ''
    search()
  }

</script>

<template>
  <h1>学员管理</h1> <br>
  <!-- 学员查询条件 -->
  <el-form :inline="true" :model="searchFrom">
    <el-form-item label="姓名">
      <el-input v-model="searchFrom.name" placeholder="请输入学生姓名"></el-input>
    </el-form-item>

    <el-form-item label="最高学历">
      <el-select v-model="searchFrom.degree" placeholder="请选择" style="width: 200px;">
        <el-option label="博士" value="6"></el-option>
        <el-option label="硕士" value="5"></el-option>
        <el-option label="本科" value="4"></el-option>
        <el-option label="大专" value="3"></el-option>
        <el-option label="高中" value="2"></el-option>
        <el-option label="初中" value="1"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="班级" width="120px" style="width: 200px;">
      <el-select v-model="searchFrom.classId" placeholder="请选择">
        <el-option label="计算机科学与技术" value="1"></el-option>
        <el-option label="软件工程" value="2"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="addEmp"> + 新增学员</el-button>
  <el-button type="danger" @click="deleteByIds"> - 批量删除</el-button>
  <br><br>

  <!-- 学员列表 -->
  <el-table :data="stuList" border style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"></el-table-column>
    <el-table-column label="姓名" prop="name" width="120" align="center"></el-table-column>
    <el-table-column label="学号" prop="no"  width="120" align="center"></el-table-column>
    <el-table-column label="班级" prop="clazzName"  width="170" align="center"></el-table-column>
    <el-table-column label="性别" width="170" align="center">
      <template #default="scope">
        {{ scope.row.gender == 1 ? '男' : '女' }}
      </template>
    </el-table-column>
    <el-table-column label="手机号" prop="phone" width="120" align="center"></el-table-column>
    <el-table-column label="最高学历" prop="degree" width="120" align="center">
      <template #default="scope">
    <!-- //最高学历, 1: 初中, 2: 高中 , 3: 大专 , 4: 本科 , 5: 硕士 , 6: 博士 -->
        <span v-if="scope.row.degree == 1">初中</span>
        <span v-else-if="scope.row.degree == 2">高中</span>
        <span v-else-if="scope.row.degree == 3">大专</span>
        <span v-else-if="scope.row.degree == 4">本科</span>
        <span v-else-if="scope.row.degree == 5">硕士</span>
        <span v-else>博士</span>
      </template>
    </el-table-column>
    <el-table-column label="违纪次数" prop="violationCount" width="200" align="center"></el-table-column>
    <el-table-column label="违纪扣分" prop="violationScore" width="170" align="center"></el-table-column>
    <el-table-column label="最后操作时间" prop="updateTime"  width="210" align="center"></el-table-column>
    <el-table-column label="操作" fixed="right" align="center">
      <template #default="scope">
        <el-button size="small" type="primary" @click="edit(scope.row.id)">编辑</el-button>
        <el-button size="small" type="danger" @click="deleteById(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!-- 分页组件 -->
  <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
    v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 30, 40]"
    layout="total, sizes, prev, pager, next, jumper" :total="total">
  </el-pagination>

</template>

<style scoped>

</style>