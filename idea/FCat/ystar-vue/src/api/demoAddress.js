import axiosProvider from './axiosProvider'

const serviceName = '/api/v1/kline/'

// 获取K线数据
export function queryStrategy() {
    return axiosProvider.get(serviceName + 'queryStrategy')
}

// 两条均线策略的收益以及交易历史
export function twoMaStrategy(params) {
    return axiosProvider.get(serviceName + 'queryTwoMaStrategy?' + params)
}

// 获取哪两条均线策略是最佳的
export function twoMaStrategyBest(params) {
    return axiosProvider.get(serviceName + 'queryTwoMaStrategyBest?' + params)
}

// 两条均线策略的收益以及交易历史-做空
export function twoMaStrategySell(params) {
    return axiosProvider.get(serviceName + 'queryTwoMaStrategySell?' + params)
}

// 获取哪两条均线策略是最佳的-做空
export function twoMaStrategySellBest(params) {
    return axiosProvider.get(serviceName + 'queryTwoMaStrategySellBest?' + params)
}

