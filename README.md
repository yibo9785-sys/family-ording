# FamilyEats 家庭点餐系统

## 项目简介
家庭点餐系统是一个基于 Spring Boot 的家庭智能点餐应用，支持菜品管理、食材管理、每日菜单规划、购物清单管理等功能。

## 技术栈
- Spring Boot 3.5.10
- Spring Security 6.5.7
- MyBatis-Plus 3.5.5
- Redis
- MySQL
- JWT

## 项目结构
```
src/main/java/com/example/family_ording/
├── config/          # 配置类
├── controller/      # 控制器层
├── service/         # 服务层
├── mapper/          # 数据访问层
├── model/           # 数据模型
│   ├── entity/      # 实体类
│   ├── dto/         # 数据传输对象
│   └── vo/          # 视图对象
├── exception/       # 异常处理
└── utils/           # 工具类
```

## 功能模块
- 认证授权（注册、登录、JWT）
- 用户管理（个人信息、密码修改）
- 菜品管理（CRUD、分页查询）
- 食材管理（CRUD、库存预警）
- 每日菜单（CRUD、日期范围查询）
- 购物清单（CRUD、状态管理）
- 许愿池（CRUD、状态管理）

## 快速开始
1. 克隆项目
2. 配置数据库连接
3. 启动 Redis 服务
4. 运行 `mvn spring-boot:run`
