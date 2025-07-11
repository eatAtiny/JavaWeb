import request from '@/utils/request'

// 查询学员列表
export const queryPageApi = (name, page, pageSize, degree, clazzId) => 
  request.get(`/students?name=${name}&page=${page}&pageSize=${pageSize}&degree=${degree}&clazzId=${clazzId}`)

// 新增学员
export const addApi = (student) => request.post('/students', student)

// 根据ID查询学员信息
export const queryInfoApi = (id) => request.get(`/students/${id}`)

// 修改学员信息
export const updateApi = (student) => request.put('/students', student)

// 删除学员
export const deleteApi = (id) => request.delete(`/students/${id}`)

// 违纪扣分
export const violationApi = (id,violationScore) => request.put(`/students/violation/${id}/${violationScore}`)