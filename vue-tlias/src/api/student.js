import request from '@/utils/request'

// 查询学员列表
export const queryPageApi = (name, page, pageSize, degree, clazzId) => 
  request.get(`/students?name=${name}&page=${page}&pageSize=${pageSize}&degree=${degree}&clazzId=${clazzId}`)

