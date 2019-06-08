## 数据库表结构

```
ads0
  ├── user_0
  └── user_1
ads1
  ├── user_0
  └── user_1
```

## 建表语句

```sql
CREATE TABLE `user_0` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

