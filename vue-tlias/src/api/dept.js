import request from "@/utils/request"

//列表查询
export const queryAllApi = () => request.get('/depts')

//新增
export const addDeptApi = (data) => request.post('/depts', data)

//根据id返回部门信息
export const getDeptByIdApi = (id) => request.get(`/depts/${id}`)

//更新部门信息
export const updateDeptApi = (data) => request.put('/depts', data)

//删除部门信息
export const deleteDeptApi = (id) => request.delete(`/depts?id=${id}`)