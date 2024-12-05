# Bond 通讯录管理系统接口文档

## 1. 用户认证模块

### 1.1 用户注册

**URL:** `/auth/register`  
**请求方法:** POST  
**请求参数:**

| 参数名          | 类型   | 必填 | 描述       |
|-----------------|--------|------|------------|
| username        | String | 是   | 用户名     |
| password        | String | 是   | 密码       |
| confirmPassword | String | 是   | 确认密码   |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

### 1.2 用户登录

**URL:** `/auth/login`  
**请求方法:** POST  
**请求参数:**

| 参数名   | 类型   | 必填 | 描述   |
|----------|--------|------|--------|
| username | String | 是   | 用户名 |
| password | String | 是   | 密码   |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "token": "JWT_TOKEN_HERE"
  }
}
```

### 1.3 获取用户信息

**URL:** `/auth/info`  
**请求方法:** GET  

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 1,
    "username": "test_user",
    "tags": "tag1,tag2"
  }
}
```

### 1.4 验证 Token

**URL:** `/auth/validate`  
**请求方法:** GET  
**请求参数:**

| 参数名 | 类型   | 必填 | 描述         |
|--------|--------|------|--------------|
| token  | String | 是   | JWT 令牌     |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "valid": true,
    "username": "test_user"
  }
}
```

---

## 2. 联系人管理模块

### 2.1 获取联系人列表

**URL:** `/contacts/list`  
**请求方法:** GET  
**请求参数:**

| 参数名      | 类型    | 必填 | 默认值 | 描述         |
|-------------|---------|------|--------|--------------|
| current     | Integer | 否   | 1      | 当前页码     |
| size        | Integer | 否   | 10     | 每页记录数   |
| name        | String  | 否   | -      | 联系人姓名   |
| tag         | String  | 否   | -      | 标签         |
| sex         | String  | 否   | -      | 性别 (M/F)   |
| birthday    | Date    | 否   | -      | 生日         |
| phone       | String  | 否   | -      | 电话号码     |
| email       | String  | 否   | -      | 邮箱地址     |
| address     | String  | 否   | -      | 地址         |
| description | String  | 否   | -      | 描述         |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "records": [
      {
        "no": 1,
        "name": "John",
        "tag": "Friend",
        "sex": "M",
        "phone": "123456789",
        "email": "john@example.com",
        "address": "123 Street",
        "description": "Best friend"
      }
    ],
    "current": 1,
    "size": 10,
    "total": 1
  }
}
```

### 2.2 获取联系人详情

**URL:** `/contacts/{id}`  
**请求方法:** GET  

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "no": 1,
    "name": "John",
    "tag": "Friend",
    "sex": "M",
    "phone": "123456789",
    "email": "john@example.com",
    "address": "123 Street",
    "description": "Best friend"
  }
}
```

### 2.3 添加联系人

**URL:** `/contacts`  
**请求方法:** POST  
**请求参数:**

| 参数名       | 类型   | 必填 | 描述     |
|--------------|--------|------|----------|
| name         | String | 是   | 姓名     |
| tag          | String | 否   | 标签     |
| sex          | String | 否   | 性别     |
| phone        | String | 否   | 电话号码 |
| email        | String | 否   | 邮箱     |
| address      | String | 否   | 地址     |
| description  | String | 否   | 描述     |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

### 2.4 更新联系人

**URL:** `/contacts`  
**请求方法:** PUT  

**请求参数:**

| 参数名       | 类型   | 必填 | 描述     |
|--------------|--------|------|----------|
| no           | Integer| 是   | 联系人编号|
| name         | String | 否   | 姓名     |
| tag          | String | 否   | 标签     |
| sex          | String | 否   | 性别     |
| phone        | String | 否   | 电话号码 |
| email        | String | 否   | 邮箱     |
| address      | String | 否   | 地址     |
| description  | String | 否   | 描述     |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

### 2.5 删除联系人

**URL:** `/contacts/{id}`  
**请求方法:** DELETE  

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

### 2.6 导出联系人 Excel 文件

**URL:** `/contacts/export/excel`  
**请求方法:** GET  

**响应:** 下载一个 `.xlsx` 文件。

### 2.7 导出联系人 JSON 文件

**URL:** `/contacts/export/json`  
**请求方法:** GET  

**响应:** 下载一个 `.json` 文件。

---

## 3. 用户标签管理模块

### 3.1 获取标签列表

**URL:** `/user/tag`  
**请求方法:** GET  

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": ["tag1", "tag2"]
}
```

### 3.2 添加新标签

**URL:** `/user/tag`  
**请求方法:** POST  
**请求参数:**

| 参数名 | 类型   | 必填 | 描述 |
|--------|--------|------|------|
| tag    | String | 是   | 标签 |

**响应示例:**
```json
{
  "code": 1,
  "msg": null,
  "data": null
}
```

---

## 4. 首页 API

### 4.1 获取客户端信息

**URL:** `/`  
**请求方法:** GET  

**响应示例:**
```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "clientIP": "127.0.0.1",
    "headers": {
      "Host": "localhost:8080",
      "User-Agent": "PostmanRuntime/7.28.4",
      "Accept": "*/*"
    }
  }
}
```