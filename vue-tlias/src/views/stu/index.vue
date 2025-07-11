<script setup>
  import { ref, watch, onMounted } from 'vue'
  import { ElMessageBox, ElMessage } from 'element-plus'
  import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi, violationApi } from '@/api/student'
  import { queryAllApi as queryClazzList } from '@/api/clazz'


  /** 列表查询展示 ************************************************************/
  // 搜索数据
  const searchFrom = ref({
    name: '',
    degree: '',
    classId: ''
  })
  const clazzList = ref([{ name: '计算机科学与技术', id: 1 }, { name: '软件工程', id: 2 }])
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

  onMounted( async () => {
    search()
    const result = await queryClazzList()
    if (result.code){
      clazzList.value = result.data
    }else{
      ElMessage.error(result.msg)
    }
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


  /** 删除学员 ************************************************************/
  const deleteIds = ref([])
  const handleSelectionChange = (selection) => {
    deleteIds.value = selection.map( item => item.id);
    console.log(deleteIds.value);
  }
  //删除学员
  const deleteById = (id) => {
    //弹出确认框
    ElMessageBox.confirm('您确认删除该学员吗?','提示',
      { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
    ).then(async () => { //确认
      const result = await deleteApi(id);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);
      }
    }).catch(() => { //取消
      ElMessage.info('您已取消删除');
    })
  }
  //批量删除学员
  const deleteByIds = () => {
    //弹出确认框
    ElMessageBox.confirm('您确认删除该学员吗?','提示',
      { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
    ).then(async () => { //确认
      if(deleteIds.value && deleteIds.value.length > 0){
        const result = await deleteApi(deleteIds.value);
        if(result.code){
          ElMessage.success('删除成功');
          search();
        }else{
          ElMessage.error(result.msg);
        }
      }else {
        ElMessage.info('您没有选择任何要删除的数据');
      }
    }).catch(() => { //取消
      ElMessage.info('您已取消删除');
    })
  }

  /** 新增/修改学员 ************************************************************/
  const studentForm = ref({
    name: '',
    no: '',
    gender: null,
    phone: '',
    idCard: '',
    isCollege: null,
    address: '',
    degree: null,
    graduationDate: '',
    clazzId: null,
  })

  const rules = ref({
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }
      ,{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
    ],
    no: [{ required: true, message: '请输入学号', trigger: 'blur' }
      ,{ min: 10, max: 10, message: '长度必须为 10 个字符', trigger: 'blur' }
    ],
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }
      ,{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
    ],
    idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }
      ,{ pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '身份证号格式不正确', trigger: 'blur' }
    ],
    isCollege: [{ required: true, message: '请选择是否是学院学员', trigger: 'change' }],
  })

  const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
  const isColleges = ref([{ name: '是', value: 1 }, { name: '否', value: 2 }])
  const degrees = ref([{ name: '博士', value: 6 }, { name: '硕士', value: 5 }, { name: '本科', value: 4 }, { name: '大专', value: 3 }, { name: '高中', value: 2 }, { name: '初中', value: 1 }])
  const formRef = ref(null)

  const dialogVisible = ref(false)

  const addStudent = () => {
    dialogVisible.value = true
  }

  const resetForm = () => {
    studentForm.value = {
      name: '',
      no: '',
      gender: null,
      phone: '',
      idCard: '',
      isCollege: 1,
      address: '',
      degree: 1,
      graduationDate: '',
      clazzId: null,
    }
    dialogVisible.value = false
  }
  const save = async () => {
    formRef.value.validate(async (valid) => {
      if (valid) {
        let result
        if (studentForm.value.id) {
          result = await updateApi(studentForm.value)
        } else {
          result = await addApi(studentForm.value)
        }
        if (result.code) {
          if (studentForm.value.id) {
            ElMessage.success('修改学员成功')
          } else {
            ElMessage.success('新增学员成功')
          }
          resetForm()
          search()
        } else {
          ElMessage.error(result.msg)
        }
      } else {
        ElMessage.error('请填写完整信息')
      }
    })
  }

  const edit = async (id) => {
    dialogVisible.value = true
    const result = await queryInfoApi(id)
    if (result.code){
      studentForm.value = result.data
    }else{
      ElMessage.error(result.msg)
    }
  }

  /** 违纪扣分 ************************************************************/
  const dialogViolation = ref(false)
  const dialogViolationTitle = ref('违纪扣分')
  const violationForm = ref({
    id: '',
    violationScore: '',
  })
  const violationFormRef = ref(null)
  const violationRules = ref({
    violationScore: [{ required: true, message: '请输入违纪扣分', trigger: 'blur' }
      ,{ pattern: /^[1-9]\d*$/, message: '违纪扣分必须为正整数', trigger: 'blur' }
    ],
  })
  
  const resetViolation = () => {
    violationForm.value = {
      id: '',
      violationScore: '',
    }
  }

  const violation = async (id) => {
    dialogViolation.value = true
    dialogViolationTitle.value = '违纪扣分'
    violationForm.value.id = id
  }
  const saveViolation = async () => {
   const result = await violationApi(violationForm.value.id,violationForm.value.violationScore)
    if (result.code){
      ElMessage.success('操作成功')
      search()
    }else{
      ElMessage.error(result.msg)
    }
    resetViolation()
    dialogViolation.value = false
  }
  const confirmViolation = () => {
    dialogViolation.value = false
    resetViolation()
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
        <el-option v-for="clazz in clazzList" :key="clazz.id" :label="clazz.name"
          :value="clazz.id"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="addStudent"> + 新增学员</el-button>
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
    <el-table-column label="违纪次数" prop="violationCount" width="100" align="center"></el-table-column>
    <el-table-column label="违纪扣分" prop="violationScore" width="100" align="center"></el-table-column>
    <el-table-column label="最后操作时间" prop="updateTime"  width="250" align="center"></el-table-column>
    <el-table-column label="操作" fixed="right" align="center" >
      <template #default="scope">
        <el-button size="small" type="primary" @click="edit(scope.row.id)">编辑</el-button>
        <el-button size="small" type="warning" @click="violation(scope.row.id)">违纪</el-button>
        <el-button size="small" type="danger" @click="deleteById(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!-- 分页组件 -->
  <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
    v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 30, 40]"
    layout="total, sizes, prev, pager, next, jumper" :total="total">
  </el-pagination>

  <!-- 新增学员弹窗 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form ref="formRef"  :rules="rules" :model="studentForm" label-width="80px">


      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="studentForm.name" placeholder="请输入姓名"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="学号" prop="no">
            <el-input v-model="studentForm.no" placeholder="请输入学号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="studentForm.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="gender in genders" :key="gender.name" :label="gender.name"
                :value="gender.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="studentForm.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="studentForm.idCard" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="是否院校" prop="isCollege">
            <el-select v-model="studentForm.isCollege" placeholder="请选择" style="width: 100%;">
              <el-option v-for="isCollege in isColleges" :key="isCollege.value" :label="isCollege.name"
                :value="isCollege.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="联系地址">
            <el-input v-model="studentForm.address" placeholder="请输入联系地址"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最高学历">
            <el-select v-model="studentForm.degree" placeholder="请选择学历" style="width: 100%;">
              <el-option v-for="degree in degrees" :key="degree.name" :label="degree.name"
                :value="degree.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="毕业时间">
            <el-date-picker v-model="studentForm.graduationDate" type="date" placeholder="选择日期"
              style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="班级">
            <el-select v-model="studentForm.clazzId" placeholder="请选择班级" style="width: 100%;">
              <el-option v-for="clazz in clazzList" :key="clazz.id" :label="clazz.name"
                :value="clazz.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetForm">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 违纪扣分弹窗 -->
  <el-dialog v-model="dialogViolation" :title="dialogViolationTitle">
    <el-form ref="violationFormRef"  :rules="violationRules" :model="violationForm" label-width="80px">
      <el-form-item label="违纪扣分" prop="violationScore">
        <el-input v-model="violationForm.violationScore" placeholder="请输入违纪扣分"></el-input>
      </el-form-item>
    </el-form>
    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="confirmViolation">取消</el-button>
        <el-button type="primary" @click="saveViolation">确认</el-button>
      </span>
    </template>

  </el-dialog>


</template>

<style scoped>

</style>