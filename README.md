# 电商购物系统 GUI 版本

基于命令行版本的扩展，实现完整的图形用户界面、数据持久化与设计模式应用。

## 项目特点

✅ **MVC 架构** - Model/View/Controller 分离  
✅ **DAO 模式** - 统一数据访问接口  
✅ **文件存储** - 商品、购物车、订单均持久化到 JSON 文件  
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
- Gson (JSON 处理)
- File I/O

## 快速开始

### 1. 准备数据文件

项目启动时会读取：

- `data/products.json`（初始商品）
- `data/orders.json`（订单）
- `data/shopping_cart.json`（购物车）

### 2. 运行程序

```bash
mvn -q exec:java -Dexec.mainClass=com.ecommerce.Main
```

## 设计模式应用

### MVC 模式

- **Model** - `model/` 目录下的数据类
- **View** - `view/` 目录下的 GUI 类
- **Controller** - `service/` 目录下的业务控制层

Controller 协调 Model 和 View，处理用户交互。

### DAO 模式

- **DAO 接口** - 定义数据访问规范
- **DAO 实现** - 具体的数据访问实现
  - `ProductDAOImpl` - 商品文件存储实现
  - `OrderDAOFileImpl`/`OrderDAOMemoryImpl` - 订单文件/内存存储切换实现
  - `ShoppingCartDAOFileImpl` - 购物车文件存储实现

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
