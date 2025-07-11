import request from '@/utils/request'

//查询班级列表数据
export const queryPageApi = (name, page, pageSize, begin, end) => 
  request.get(`/clazzs?name=${name}&page=${page}&pageSize=${pageSize}&begin=${begin}&end=${end}`)

// 查询所有班级
export const queryAllApi = () => request.get('/clazzs/list');

// 新增班级
export const addApi = (clazz) =>  request.post('/clazzs', clazz);

// 根据ID查询班级信息
export const queryInfoApi = (id) =>  request.get(`/clazzs/${id}`);

// 修改班级信息
export const updateApi = (clazz) =>  request.put('/clazzs', clazz);

// 删除班级
export const deleteApi = (id) =>  request.delete(`/clazzs/${id}`);