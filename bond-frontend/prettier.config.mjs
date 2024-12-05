/** @type {import("prettier").Config} */
const config = {
  // 当箭头函数只有一个参数时，省略括号
  arrowParens: 'avoid',

  // 在多行元素的情况下，将 ">" 放在最后一行的末尾，而不是新的一行
  bracketSameLine: true,

  // 在对象字面量之间打印空格
  bracketSpacing: true,

  // 对嵌入的语言进行自动格式化
  embeddedLanguageFormatting: 'auto',

  // 指定 HTML 文件的空白敏感度
  htmlWhitespaceSensitivity: 'ignore',

  // 在文件顶部插入一个特殊的 @format 标记，指定文件已被 Prettier 格式化
  insertPragma: false,

  // 在 JSX 中使用单引号
  jsxSingleQuote: true,

  // 指定打印宽度
  printWidth: 80,

  // 指定如何换行
  proseWrap: 'preserve',

  // 对象的 key 仅在必要时用引号
  quoteProps: 'as-needed',

  // 只有在文件顶部有特殊的 @format 标记时才格式化代码
  requirePragma: false,

  // 语句末尾不使用分号
  semi: false,

  // 每个属性单独一行
  singleAttributePerLine: false,

  // 使用单引号
  singleQuote: true,

  // 指定制表符的宽度
  tabWidth: 2,

  // 指定尾随逗号的方式
  trailingComma: 'es5',

  // 使用空格缩进
  useTabs: false,

  // 不缩进 Vue 文件中的 script 和 style 标签
  vueIndentScriptAndStyle: false,

  // 针对不同文件的覆盖配置
  overrides: [
    {
      // 针对 .wxml 文件
      files: '*.wxml',
      options: {
        // 使用 HTML 解析器
        parser: 'html',
      },
    },
  ],
}

export default config
