import request from '@/utils/request'

export const getLogListApi = (currentPage, pageSize) => request.get('/log/page?page=' + currentPage + '&pageSize=' + pageSize)
