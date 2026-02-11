-- FamilyEats数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS family_eats CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE family_eats;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    role ENUM('admin', 'elder', 'child', 'teenager') NOT NULL,
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    preferences JSON,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜品表
CREATE TABLE IF NOT EXISTS dish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    video_url VARCHAR(255),
    type ENUM('meat', 'vegetable', 'soup', 'staple', 'dessert') NOT NULL,
    difficulty ENUM('easy', 'medium', 'hard') NOT NULL,
    cook_time INT NOT NULL,
    health_score INT DEFAULT 0,
    spiciness INT DEFAULT 0,
    sweetness INT DEFAULT 0,
    creator_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (creator_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 食材表
CREATE TABLE IF NOT EXISTS ingredient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category ENUM('vegetable', 'meat', 'seafood', 'grain', 'dairy', 'seasoning', 'other') NOT NULL,
    unit VARCHAR(20) NOT NULL,
    stock_quantity DECIMAL(10,2) DEFAULT 0,
    min_stock DECIMAL(10,2) DEFAULT 0,
    expiry_date DATE,
    purchase_date DATE,
    price DECIMAL(10,2),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜品食材关联表
CREATE TABLE IF NOT EXISTS dish_ingredient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dish_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dish_id) REFERENCES dish(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 每日菜单表
CREATE TABLE IF NOT EXISTS daily_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_date DATE NOT NULL UNIQUE,
    status ENUM('draft', 'voting', 'confirmed', 'completed') NOT NULL DEFAULT 'draft',
    creator_id BIGINT,
    notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (creator_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜单菜品关联表
CREATE TABLE IF NOT EXISTS menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    votes INT DEFAULT 0,
    is_confirmed BOOLEAN DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES daily_menu(id),
    FOREIGN KEY (dish_id) REFERENCES dish(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 投票记录表
CREATE TABLE IF NOT EXISTS vote_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES daily_menu(id),
    FOREIGN KEY (dish_id) REFERENCES dish(id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    UNIQUE KEY uk_menu_dish_user (menu_id, dish_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 许愿池表
CREATE TABLE IF NOT EXISTS wish_pool (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content VARCHAR(255) NOT NULL,
    status ENUM('pending', 'approved', 'rejected', 'completed') NOT NULL DEFAULT 'pending',
    image_url VARCHAR(255),
    notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评价表
CREATE TABLE IF NOT EXISTS evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    image_url VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (menu_id) REFERENCES daily_menu(id),
    FOREIGN KEY (dish_id) REFERENCES dish(id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 采购清单表
CREATE TABLE IF NOT EXISTS shopping_list (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT,
    name VARCHAR(100) NOT NULL,
    status ENUM('pending', 'purchasing', 'completed') NOT NULL DEFAULT 'pending',
    creator_id BIGINT,
    total_amount DECIMAL(10,2) DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (menu_id) REFERENCES daily_menu(id),
    FOREIGN KEY (creator_id) REFERENCES sys_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 采购清单项目表
CREATE TABLE IF NOT EXISTS shopping_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    list_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    purchased BOOLEAN DEFAULT FALSE,
    notes VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (list_id) REFERENCES shopping_list(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 营养数据表
CREATE TABLE IF NOT EXISTS nutrition_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dish_id BIGINT NOT NULL,
    calories DECIMAL(10,2),
    protein DECIMAL(10,2),
    carbs DECIMAL(10,2),
    fat DECIMAL(10,2),
    fiber DECIMAL(10,2),
    vitamins JSON,
    minerals JSON,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (dish_id) REFERENCES dish(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入基础数据
-- 插入管理员用户
INSERT INTO sys_user (username, password, nickname, role, created_at) 
VALUES ('admin', '$2a$10$e8wY8r23T5u6i7o8p9a0s1d2f3g4h5j6k7l8m9n0b1c2d3e4f5g6h7i8j9k0l', '管理员', 'admin', NOW())
ON DUPLICATE KEY UPDATE username=username;

-- 插入一些基础食材
INSERT INTO ingredient (name, category, unit, created_at) VALUES
('西红柿', 'vegetable', '个', NOW()),
('鸡蛋', 'other', '个', NOW()),
('猪肉', 'meat', '斤', NOW()),
('大米', 'grain', '斤', NOW()),
('白菜', 'vegetable', '斤', NOW()),
('萝卜', 'vegetable', '斤', NOW()),
('鱼', 'seafood', '条', NOW()),
('牛奶', 'dairy', '盒', NOW()),
('盐', 'seasoning', '袋', NOW()),
('糖', 'seasoning', '袋', NOW())
ON DUPLICATE KEY UPDATE name=name;

-- 插入一些基础菜品
INSERT INTO dish (name, description, type, difficulty, cook_time, health_score, created_at) VALUES
('西红柿炒蛋', '经典家常菜，酸甜可口', 'vegetable', 'easy', 15, 80, NOW()),
('红烧肉', '传统名菜，肥而不腻', 'meat', 'medium', 60, 60, NOW()),
('清炒白菜', '清爽可口，营养健康', 'vegetable', 'easy', 10, 90, NOW()),
('米饭', '主食', 'staple', 'easy', 30, 70, NOW()),
('鱼汤', '鲜美可口，营养丰富', 'soup', 'medium', 40, 85, NOW())
ON DUPLICATE KEY UPDATE name=name;

-- 插入菜品食材关联
INSERT INTO dish_ingredient (dish_id, ingredient_id, quantity) VALUES
(1, 1, 2),  -- 西红柿炒蛋：2个西红柿
(1, 2, 3),  -- 西红柿炒蛋：3个鸡蛋
(2, 3, 1),  -- 红烧肉：1斤猪肉
(3, 5, 1),  -- 清炒白菜：1斤白菜
(4, 4, 2),  -- 米饭：2斤大米
(5, 7, 1)   -- 鱼汤：1条鱼
ON DUPLICATE KEY UPDATE dish_id=dish_id;

-- 插入今日菜单
INSERT INTO daily_menu (menu_date, status, created_at) 
VALUES (CURDATE(), 'voting', NOW())
ON DUPLICATE KEY UPDATE menu_date=menu_date;

-- 插入菜单菜品
INSERT INTO menu_item (menu_id, dish_id, created_at) VALUES
(1, 1, NOW()),
(1, 2, NOW()),
(1, 3, NOW())
ON DUPLICATE KEY UPDATE menu_id=menu_id;

-- 插入评价
INSERT INTO evaluation (menu_id, dish_id, user_id, rating, content, created_at) 
VALUES (1, 1, 1, 5, '非常好吃！', NOW())
ON DUPLICATE KEY UPDATE menu_id=menu_id;

-- 插入许愿
INSERT INTO wish_pool (user_id, content, status, created_at) 
VALUES (1, '想吃火锅', 'pending', NOW())
ON DUPLICATE KEY UPDATE user_id=user_id;

-- 插入采购清单
INSERT INTO shopping_list (name, status, creator_id, created_at) 
VALUES ('今日采购', 'pending', 1, NOW())
ON DUPLICATE KEY UPDATE name=name;

-- 插入采购清单项目
INSERT INTO shopping_item (list_id, ingredient_id, quantity, created_at) VALUES
(1, 1, 5, NOW()),
(1, 2, 10, NOW()),
(1, 3, 2, NOW())
ON DUPLICATE KEY UPDATE list_id=list_id;

-- 插入营养数据
INSERT INTO nutrition_data (dish_id, calories, protein, carbs, fat, created_at) VALUES
(1, 250, 15, 30, 10, NOW()),
(2, 450, 20, 20, 35, NOW()),
(3, 100, 5, 15, 3, NOW()),
(4, 300, 5, 60, 1, NOW()),
(5, 200, 18, 10, 10, NOW())
ON DUPLICATE KEY UPDATE dish_id=dish_id;
