import request from '@/utils/request'

export function together() {
    return request({
        url:"/lzp/together",
        method:'get'
    })
}

export function regNums() {
    return request({
        url: "/lzp/reg-nums",
        method: 'get'
    })
}

export function tfNums() {
    return request({
        url: "/lzp/tf-nums",
        method: 'get'
    })
}

export function fmProportion() {

    return request({
        url:"/lzp/fm-proportion",
        method: 'get'
    })
}

export function getPeriod() {
    return request({
        url: '/lzp/getPeriod',
        method: 'get'
    })
}