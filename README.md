# 电商购物系统 GUI 版本

基于命令行版本的扩展，实现完整的图形用户界面、数据持久化与设计模式应用。

## 项目特点

✅ **MVC 架构** - Model/View/Controller 分离  
✅ **DAO 模式** - 统一数据访问接口  
✅ **文件存储** - 购物车数据持久化到文件  
✅ **数据库存储** - 订单数据存储到 MySQL  
✅ **Swing GUI** - 完整的图形用户界面  

## 项目结构

```
src/
├── model/                # MVC Model 层
├── dao/                  # DAO 数据访问层
├── controller/           # MVC Controller 层
├── view/                 # MVC View 层
└── util/                 # 工具类
```

## 核心功能

### 购物车管理
- 浏览购物车
- 添加商品到购物车
- 从购物车移除商品
- 购物车数据文件持久化

### 订单管理
- 生成订单
- 浏览订单
- 删除订单
- 订单数据库持久化

## 使用技术

- Java Swing
- JDBC
- MySQL
- Gson (JSON 处理)
- File I/O

## 快速开始

### 1. 创建数据库

```sql
CREATE DATABASE ecommerce_db;

USE ecommerce_db;

-- 商品表
CREATE TABLE products (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

-- 订单表
CREATE TABLE orders (
    id VARCHAR(20) PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL,
    create_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL
);

-- 订单明细表
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(20),
    product_id VARCHAR(20),
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

### 2. 配置数据库连接

编辑 `src/util/Constants.java`，配置数据库连接参数：

```java
public static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_db";
public static final String DB_USER = "root";
public static final String DB_PASSWORD = "your_password";
```

### 3. 运行程序

```bash
java -cp .:lib/* util.Main
```

## 设计模式应用

### MVC 模式

- **Model** - `model/` 目录下的数据类
- **View** - `view/` 目录下的 GUI 类
- **Controller** - `controller/` 目录下的控制器类

Controller 协调 Model 和 View，处理用户交互。

### DAO 模式

- **DAO 接口** - 定义数据访问规范
- **DAO 实现** - 具体的数据访问实现
  - `CartFileDAO` - 文件存储实现
  - `OrderDBDAO` - 数据库存储实现

## 项目成员分工

| 成员 | 负责模块 |
|------|--------|
| 尹晓琳 | Model 层基础类 |
| 关梦瑜 | 购物车 Controller 和 View |
| 郭泽盼 | 订单 DAO 和数据库操作 |
| 李子涵 | 订单 Controller 和 View |

## 后续优化

- [ ] 用户登录认证
- [ ] 支付功能集成
- [ ] 订单状态流程管理
- [ ] 并发控制与库存管理
- [ ] 数据备份与恢复
