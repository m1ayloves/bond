import PinyinMatch from 'pinyin-match'

/**
 * 根据指定属性和关键词搜索对象数组
 *
 * @param {Array} objs - 待搜索的对象数组
 * @param {Array} propertyNames - 包含要搜索的属性名称的数组
 * @param {string} keyword - 搜索关键词
 * @returns {Array} - 返回匹配的对象数组
 */
export function searchObjects(objs, propertyNames, keyword) {
  // 检查输入参数的类型，如果不符合预期，则返回空数组
  if (
    !Array.isArray(objs) ||
    !Array.isArray(propertyNames) ||
    typeof keyword !== 'string' ||
    keyword === ''
  ) {
    return objs
  }

  // 过滤对象数组，只保留那些在指定属性中包含关键词的对象
  return objs.filter(obj => {
    // 检查对象的每个指定属性，如果该属性的值是字符串且包含关键词，则返回 true
    return propertyNames.some(prop => {
      if (typeof obj[prop] === 'string') {
        return PinyinMatch.match(obj[prop], keyword)
      }
      return false
    })
  })
}
