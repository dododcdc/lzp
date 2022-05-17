import request from '@/utils/request'

export function get() {

    return request({
        url:"/get",
        method:'get'
    })
}


export function demo1() {
    return request({
        url: "/demo1",
        method:'get'
    })
}