package com.guo.ds.aspect;

import com.guo.ds.annotatino.DataScope;
import com.guo.ds.entity.BaseEntity;
import com.guo.ds.entity.Role;
import com.guo.ds.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DataScopeAspect {
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_CUSTOM = "2";
    public static final String DATA_SCOPE_DEPT = "3";
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";
    public static final String DATA_SCOPE_SELF = "5";
    public static final String DATA_SCOPE = "data_scope";

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint jp, DataScope dataScope){
        clearDataScope(jp);
        // 拿到存在SpringSecurity中的用户信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 超级用户不用添加过滤语句
        if (user.getUserId() == 1L) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            String ds = role.getDataScope();
            if(DATA_SCOPE_ALL.equals(ds)){
                return;
            } else if (DATA_SCOPE_CUSTOM.equals(ds)) {
                // 自定义的数据权限, 筛选条件为去sys_role_dept表中查看该角色可以查看的部门
                sql.append(String.format(" OR %s.dept_id in(select rd.dept_id from sys_role_dept rd where rd.role_id=%d)",
                        dataScope.deptAlias(), role.getRoleId()));
            } else if (DATA_SCOPE_DEPT.equals(ds)) {
                // 只能查看自己部门, 则筛选条件为自己的部门号
                sql.append(String.format(" OR %s.dept_id=%d",
                        dataScope.deptAlias(), user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(ds)) {
                // 可以查看子部门, 则筛选条件除了自己部门, 还有ancestors字段中包含自己的部门
                sql.append(String.format(" OR %s.dept_id in(select dept_id from sys_dept where dept_id=%d or find_in_set(%d,ancestors))",
                        dataScope.deptAlias(), user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(ds)) {
                String s = dataScope.userAlias();
                if ("".equals(s)) {
                    // 如果没有使用别名, 则说明没有使用user表, 就直接加个1=0, 别名还得和sql语句中相同(此处体现了强耦合, 可优化)
                    sql.append(" OR 1=0");
                }else {
                    // 否则说明要使用用户表查询相应的用户或者要通过关联用户表查询角色等
                    sql.append(String.format(" OR %s.user_id=%d", dataScope.userAlias(), user.getUserId()));
                }
            }
        }
        // 通过连接点获取方法执行的参数
        Object arg = jp.getArgs()[0];
        if (arg != null && arg instanceof BaseEntity) {
            // 将过滤条件的sql语句放入传进的实体类参数中用于mapper.xml文件进行拼接
            BaseEntity baseEntity = (BaseEntity) arg;
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sql.substring(4) + ")");
        }
    }

    // 恶意用户可能传入并调用setParams()修改params, 所以提前清除
    private void clearDataScope(JoinPoint jp) {
        Object arg = jp.getArgs()[0];
        if (arg != null && arg instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) arg;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }

}
