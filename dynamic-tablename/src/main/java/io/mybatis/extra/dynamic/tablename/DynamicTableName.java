package io.mybatis.extra.dynamic.tablename;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 动态表名
 */
public class DynamicTableName {
  public static ThreadLocal<String> TABLE = new ThreadLocal<>();

  /**
   * 获取动态表名
   */
  public static String table() {
    return TABLE.get();
  }

  /**
   * 获取动态表名
   *
   * @param defaultTable 默认表名
   */
  public static String table(String defaultTable) {
    String table = TABLE.get();
    return table != null ? table : defaultTable;
  }

  /**
   * 设置表名执行方法，返回执行方法返回值
   *
   * @param table    动态表名
   * @param supplier 执行方法
   * @param <T>      结果类型
   * @return 执行结果
   */
  public static <T> T hint(String table, Supplier<T> supplier) {
    Objects.requireNonNull(table);
    Objects.requireNonNull(supplier);
    TABLE.set(table);
    try {
      return supplier.get();
    } finally {
      TABLE.remove();
    }
  }

  /**
   * 设置表名执行方法，无须返回值
   *
   * @param table    动态表名
   * @param runnable 执行方法
   */
  public static void hint(String table, Runnable runnable) {
    Objects.requireNonNull(table);
    Objects.requireNonNull(runnable);
    TABLE.set(table);
    try {
      runnable.run();
    } finally {
      TABLE.remove();
    }
  }

}
