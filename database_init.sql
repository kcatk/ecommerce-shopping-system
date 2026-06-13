-- 创建数据库
CREATE DATABASE IF NOT EXISTS ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ecommerce_db;

-- 商品表
CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(20) PRIMARY KEY COMMENT '商品ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    price DECIMAL(10, 2) NOT NULL COMMENT '商品单价',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    id VARCHAR(20) PRIMARY KEY COMMENT '订单ID',
    total_price DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    status VARCHAR(20) NOT NULL DEFAULT '待支付' COMMENT '订单状态',
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '订单项ID',
    order_id VARCHAR(20) NOT NULL COMMENT '订单ID',
    product_id VARCHAR(20) NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    quantity INT NOT NULL COMMENT '购买数量',
    unit_price DECIMAL(10, 2) NOT NULL COMMENT '单价',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- 初始化商品数据
INSERT INTO products (id, name, price, stock) VALUES
('P001', '笔记本电脑', 5999.00, 10),
('P002', '无线鼠标', 99.00, 50),
('P003', '机械键盘', 299.00, 30),
('P004', '24英寸显示器', 1299.00, 15),
('P005', 'USB-C 转接器', 49.99, 100);
