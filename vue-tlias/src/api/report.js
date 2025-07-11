import request from '@/utils/request'

// 员工人数统计
export const empJobDataApi = () => request.get('/report/empJobData')

//员工性别统计
export const empGenderApi = () => request.get('/report/empGenderData')

// 学员学历统计
export const studentDegreeApi = () => request.get('/report/studentDegreeData')

// 班级人数统计
export const clazzCountApi = () => request.get('/report/studentCountData')
