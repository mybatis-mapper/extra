# MyBatis 扩展功能

当前仓库用于实现附加功能，收集 mybatis-mapper 基础方法外的通用方法，欢迎PR。

如果想要增加新的子模块，可以先提 issues，确定功能后在PR。

## dynamic-tablename 动态表名

通过 `<T> T DynamicHint.table(String name, Supplier<T> supplier)` 设置动态表名后执行。

## mapper-common 通用方法

## mapper-mysql MySQL 通用方法

## mapper-oracle Oracle 通用方法

## mapper-sqlserver SQLServer 通用方法
