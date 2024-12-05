# 📇 Bond 通讯录管理系统



**Bond 通讯录管理系统** 是一款功能强大且用户友好的通讯录管理工具，帮助用户高效地管理联系人信息。支持增删改查、多条件查询、标签管理以及数据导出等功能，适合个人和团队使用。

------

## 🌟 项目简介



Bond 通讯录管理系统采用 **前后端分离架构**，前端基于 Vue 3 开发，后端使用 Spring Boot 3 构建。通过简洁直观的界面和高性能的后端服务，提供灵活可靠的通讯录管理解决方案。

------

## 🌐 在线体验



项目已部署，点击以下链接体验： 👉 [Bond 通讯录管理系统 Demo](http://mayloves.cyou/)

------

## 🛠 技术栈



### 后端



- **Spring Boot 3**：构建 RESTful API 服务。
- **MyBatis-Plus**：简化数据库操作。
- **MySQL**：关系型数据库，用于存储用户和联系人信息。
- **JWT（JSON Web Token）**：用户认证和权限管理。
- **EasyExcel**：数据导出为 Excel 文件。
- **Hutool**：工具类操作，简化开发流程。

### 前端



- **Vue 3**：构建现代化的用户界面。
- **Ant Design Vue**：UI 组件库。
- **Axios**：HTTP 请求库。
- **Vite**：快速开发与构建工具。

------

## ✨ 功能特性



- **用户认证**：支持注册、登录及会话管理。

- **联系人管理**：

    - 增删改查操作。
    - 多条件查询与分页展示。

- **标签管理**：灵活添加标签，为联系人分类。

- **数据导出**：联系人数据可导出为 Excel 和 JSON 文件。

- **直观界面**：基于 Ant Design Vue 的用户友好界面。

- **高性能**：后端优化支持大数据量操作。

------

## 🚀 快速开始



### 环境准备



确保你已安装以下工具：

- MySQL 9.0.0
- JDK 17
- Maven
- Node.js 18.0.0
- Git

### 克隆项目



```
git clone https://github.com/m1ayloves/bond.git
cd bond
```



#### 后端服务



1. 初始化数据库：

    - 在 MySQL 中执行 `bond-backend/init.sql` 脚本。

2. 修改配置：

    - 配置 `bond-backend/src/main/resources/application-dev.yml` 中的数据库连接。

3. 启动服务：

   ```
   cd bond-backend
   mvn spring-boot:run
   ```



后端默认运行在 [http://localhost:18080](http://localhost:18080/)

接口详见 👉 [api-doc.md](https://github.com/m1ayloves/bond/blob/main/api-doc.md)

#### 前端服务



1. 安装依赖：

   ```
   cd bond-frontend
   npm install
   ```



2. 启动开发服务器：

   ```
   npm run dev
   ```



前端默认运行在



http://localhost:5173

------

## 📖 项目结构



```
Bond/
├── bond-backend/     # 后端服务
│   ├── src/          # 源代码
│   ├── pom.xml       # Maven 配置文件
│   └── init.sql      # 数据库初始化脚本
├── bond-frontend/    # 前端服务
│   ├── src/          # 源代码
│   ├── package.json  # Node 项目配置
│   └── vite.config.js# 前端构建工具配置
└── README.md         # 项目说明文档
```



------

## 🤝 贡献



欢迎贡献代码或提出建议！请先 Fork 项目并提交 PR。

## 📜 许可证



本项目基于 MIT 许可证开源，详情请参考 [LICENSE](https://github.com/m1ayloves/bond/blob/main/LICENSE)。