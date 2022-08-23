package io.mybatis.extra.dynamic.tablename;

import io.mybatis.provider.EntityTable;
import io.mybatis.provider.EntityTableFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DynamicEntityTableFactory implements EntityTableFactory {

  @Override
  public int getOrder() {
    return EntityTableFactory.super.getOrder() + 100;
  }

  @Override
  public EntityTable createEntityTable(Class<?> entityClass, Chain chain) {
    EntityTable entityTable = chain.createEntityTable(entityClass);
    if (entityTable != null) {
      Boolean enabled = entityTable.getPropBoolean("dynamic.table.enabled", true);
      if (enabled) {
        // cglib 动态代理，支持 tableName 重写
        entityTable = proxy(entityTable);
      }
    }
    return entityTable;
  }

  /**
   * 动态代理 entityTable 方法
   *
   * @param entityTable 被代理对象
   */
  public EntityTable proxy(EntityTable entityTable) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(EntityTable.class);
    enhancer.setCallback(new EntityTableMethodInterceptor(entityTable));
    return (EntityTable) enhancer.create(new Class[]{Class.class}, new Object[]{entityTable.entityClass()});
  }

  public static class EntityTableMethodInterceptor implements MethodInterceptor {
    private final EntityTable target;

    public EntityTableMethodInterceptor(EntityTable target) {
      this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
      if ("tableName".equals(method.getName())) {
        return "${@" + DynamicTableName.class.getName() + "@table('" + target.tableName() + "')}";
      }
      return method.invoke(target, objects);
    }
  }

}
