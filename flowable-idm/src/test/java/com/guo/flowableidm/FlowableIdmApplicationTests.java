package com.guo.flowableidm;

import org.flowable.common.engine.api.management.TableMetaData;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmManagementService;
import org.flowable.idm.api.NativeUserQuery;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.IdentityInfoEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class FlowableIdmApplicationTests {

    @Autowired
    IdentityService identityService;

    @Autowired
    IdmManagementService idmManagementService;

    private static final Logger logger= LoggerFactory.getLogger(FlowableIdmApplicationTests.class);

    /**
     * 查询系统信息，本质上是查询 ACT_ID_PROPERTY
     */
    @Test
    void test15() {
        Map<String, String> properties = idmManagementService.getProperties();
        Set<String> key = properties.keySet();
        for (String s : key) {
            logger.info("key:{};value:{}", s, properties.get(s));
        }
        // 查询实体类所对应的表名称
        String groupTableName = idmManagementService.getTableName(Group.class);
        logger.info("tableName:{}", groupTableName);
        // 获取表的相信信息
        TableMetaData tableMetaData = idmManagementService.getTableMetaData(groupTableName);
        logger.info("列名：{}",tableMetaData.getColumnNames());
        logger.info("列的类型：{}",tableMetaData.getColumnTypes());
        logger.info("表名：{}",tableMetaData.getTableName());
    }

    /**
     * 自定义 SQL 查询用户组
     */
    @Test
    void test14() {
        List<Group> list = identityService.createNativeGroupQuery()
                .sql("SELECT RES.* from ACT_ID_GROUP RES WHERE exists(select 1 from ACT_ID_MEMBERSHIP M where M.GROUP_ID_ = RES.ID_ and M.USER_ID_ = #{userId}) order by RES.ID_ asc")
                .parameter("userId", "guo").list();
        for (Group group : list) {
            logger.info("id:{};name:{}", group.getId(), group.getName());
        }
    }

    /**
     * 查询包含某个用户的用户组
     *
     * 对应 SQL
     * ==>  Preparing: SELECT RES.* from ACT_ID_GROUP RES WHERE exists(select 1 from ACT_ID_MEMBERSHIP M where M.GROUP_ID_ = RES.ID_ and M.USER_ID_ = ?) order by RES.ID_ asc
     * ==> Parameters: guo(String)
     * <==      Total: 1
     */
    @Test
    void test13() {
        List<Group> list = identityService.createGroupQuery().groupMember("guo").list();
        for (Group group : list) {
            logger.info("id:{};name:{}", group.getId(), group.getName());
        }
    }

    /**
     * 查询用户组. 注意用户组名称不唯一, 不适用 singleResult, 所以用 list
     *
     * 对应 SQL
     * ==>  Preparing: SELECT RES.* from ACT_ID_GROUP RES WHERE RES.NAME_ = ? order by RES.ID_ asc
     * ==> Parameters: 组长(String)
     * <==      Total: 1
     */
    @Test
    void test12() {
        List<Group> list = identityService.createGroupQuery().groupName("组长").list();
        for (Group group : list) {
           logger.info("id:{};name:{}", group.getId(), group.getName());
        }
    }

    /**
     * 将 managers 用户组的 name 字段改为 CEO
     * 同样也需要先查询(保证 revision 字段相同)
     *
     * 对应 SQL
     * ==>  Preparing: update ACT_ID_GROUP SET REV_ = ?, NAME_ = ?, TYPE_ = ? where ID_ = ? and REV_ = ?
     * ==> Parameters: 2(Integer), CEO(String), null, managers(String), 1(Integer)
     * <==    Updates: 1
     *
     * 可以看出, 乐观锁的查询方式为只有对应版本的数据才会被修改. 即需要先查出目标版本的数据才能修改, 否则不修改
     */
    @Test
    void test11() {
        Group group = identityService.createGroupQuery().groupId("managers").singleResult();
        group.setName("CEO");
        identityService.saveGroup(group);
    }

    /**
     * 用户组添加用户
     * 对应 SQL
     * ==>  Preparing: insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( ?, ? )
     * ==> Parameters: guo(String), managers(String)
     * <==    Updates: 1
     */
    @Test
    void test10() {
        String groupId = "managers";
        String userId = "guo";
        // 用户和用户组间的关联关系
        identityService.createMembership(userId, groupId);
    }

    /**
     * 删除用户组
     * 对应 SQL
     * ==>  Preparing: delete from ACT_ID_MEMBERSHIP where GROUP_ID_ = ?
     * ==> Parameters: leader(String)
     * <==    Updates: 0
     * ==>  Preparing: delete from ACT_ID_GROUP where ID_ = ? and REV_ = ?
     * ==> Parameters: leader(String), 1(Integer)
     * <==    Updates: 1
     *
     * 为什么有两个删除 SQL？
     * - ACT_ID_MEMBERSHIP 表中保存的是用户 ID 和 组 ID 之间的关联关系，所以，当删除一个用户组的时候，需要先删除组中的用户，
     *      第一个删除语句其实就是干这个事情。
     * - 第二个删除语句就是删除具体的用户组
     */
    @Test
    void test09() {
        identityService.deleteGroup("leader");
    }

    /**
     * 对应 ACT_ID_GROUP 表
     * 对应的查询语句
     * ==>  Preparing: insert into ACT_ID_GROUP (ID_, REV_, NAME_, TYPE_) values ( ?, 1, ?, ? )
     * ==> Parameters: managers(String), 经理(String), null
     * <==    Updates: 1
     */
    @Test
    void test08() {
        GroupEntityImpl group = new GroupEntityImpl();
        group.setName("经理");
        group.setId("managers");
        // 也得设置 revision
        group.setRevision(0);
        identityService.saveGroup(group);
    }

    @Test
    void test07() {
        List<User> list = identityService.createNativeUserQuery()
                .sql("select * from ACT_ID_USER where EMAIL_=#{email}")
                .parameter("email", "guo@qq.com").list();
        for (User user : list) {
            logger.info("id:{};displayName:{}", user.getId(), user.getDisplayName() );
        }
    }

    @Test
    void test06() {
        List<User> list = identityService.createUserQuery().orderByUserId().desc().list();
        for (User user : list) {
            logger.info("id:{};displayName:{}", user.getId(), user.getDisplayName() );
        }
    }

    @Test
    void test05() {
        List<User> list = identityService.createUserQuery().userDisplayNameLike("%zhang%").list();
        for (User user : list) {
            logger.info("id:{};displayName:{}", user.getId(), user.getDisplayName() );
        }
    }

    @Test
    void test04() {
        identityService.deleteUser("guo");
    }

    @Test
    void test03() {
        User user = identityService.createUserQuery().userId("guo").singleResult();
        user.setEmail("123@12.com");
        user.setPassword("321");
        // 用户密码修改了需调用该方法
        identityService.updateUserPassword(user);
    }

    @Test
    void test02() {
        User user = identityService.createUserQuery().userId("guo").singleResult();
        user.setEmail("123@123.com");
        identityService.saveUser(user);
    }

    /**
     * 更新用户信息
     * saveUser 方法可以用来更新用户信息，但是不能用来更新密码。
     * 每更新一次，数据库的 reversion 会自增 1
     */
    @Test
    void test01() {
        UserEntityImpl user = new UserEntityImpl();
        user.setId("guo");
        user.setEmail("666@123.com");
        //注意，修改的时候，需要确保 reversion 的版本号和数据库中的版本号保持一致
        user.setRevision(2);
        identityService.saveUser(user);
    }

    @Test
    void contextLoads() {
        // 创建一个用户
        UserEntityImpl user = new UserEntityImpl();
        user.setId("guo");
        user.setDisplayName("fengyin");
        user.setPassword("123");
        user.setFirstName("guo");
        user.setLastName("guo");
        user.setEmail("guo@qq.com");
        // 注意新用户需设置版本号为0
        user.setRevision(0);

        identityService.saveUser(user);
    }

}
