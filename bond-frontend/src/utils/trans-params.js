/**
 * 将给定的参数对象转换为 URL 编码的字符串
 * 这个函数主要用于将一个对象的键值对转换成查询字符串格式，可以处理嵌套对象
 * @param {Object} params - 包含需要转换的键值对的对象
 * @returns {string} URL编码后的字符串
 */
export function tansParams(params) {
  let result = ''
  // 遍历参数对象的每个属性
  for (const propName of Object.keys(params)) {
    const value = params[propName]
    const part = encodeURIComponent(propName) + '='
    // 检查值是否是非空、非undefined
    if (value !== null && value !== '' && typeof value !== 'undefined') {
      // 如果值是对象，进行嵌套处理
      if (typeof value === 'object') {
        for (const key of Object.keys(value)) {
          // 检查嵌套对象的值是否是非空、非undefined
          if (
            value[key] !== null &&
            value[key] !== '' &&
            typeof value[key] !== 'undefined'
          ) {
            let params = propName + '[' + key + ']'
            const subPart = encodeURIComponent(params) + '='
            // 将嵌套对象的键值对添加到结果字符串中
            result += subPart + encodeURIComponent(value[key]) + '&'
          }
        }
      } else {
        // 将非对象的键值对添加到结果字符串中
        result += part + encodeURIComponent(value) + '&'
      }
    }
  }
  // 返回构建的查询字符串
  return result
}
