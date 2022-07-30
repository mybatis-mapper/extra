# MyBatis 扩展功能

当前仓库用于实现附加功能，收集 mybatis-mapper 基础方法外的通用方法，欢迎PR。

如果想要增加新的子模块，可以先提 issues，确定功能后在PR。

## dynamic-tablename 动态表名

添加依赖：
```xml
<dependency>
  <groupId>io.mybatis.extra</groupId>
  <artifactId>dynamic-tablename</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

提供如下两个方法：

- `<T> T DynamicTableName.hint(String table, Supplier<T> supplier)`
- `<T> T DynamicTableName.hint(String table, Runnable runnable)`

需要返回值时使用第一个，不需要返回值使用第二个。

使用示例：

```java
Optional<User> user = DynamicTableName.hint("user_2", () -> entityMapper.selectByPrimaryKey(1L));
```

`selectByPrimaryKey` 对应的 xml 方法：
```xml
<script>
SELECT id,name AS userName,sex FROM ${@io.mybatis.extra.dynamic.tablename.DynamicTableName@table('user')}
<where>
id = #{id}
</where> 
</script>
```

实际执行的 SQL：
```sql
SELECT id,name AS userName,sex FROM user_2 WHERE id = ?
```

## mapper-common 通用方法

## mapper-mysql MySQL 通用方法

## mapper-oracle Oracle 通用方法

## mapper-sqlserver SQLServer 通用方法
