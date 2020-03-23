package com.ql.util.express.test;


import com.ql.util.express.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shadowyy
 * @version 2020/3/18 11:17
 */
public class Demo {

    private ExpressRunner runner = new ExpressRunner();

    // 如果性别为“男”，并且 分数 > 80分 那么此人授信500元  否则拒绝授信

    @Before
    public void init() throws Exception {
        //逻辑控制符号定义
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);
        runner.addOperatorWithAlias("并且", "and", null);
        runner.addOperatorWithAlias("或者", "or", null);

        //指标项定义
        runner.addFunctionOfClassMethod("getAge", Demo.class.getName(), "getAge", new String[]{User.class.getName()}, null);
        runner.addMacro("年龄", "getAge(user)");

        runner.addFunctionOfClassMethod("getJob", Demo.class.getName(), "getJob", new String[]{User.class.getName()}, null);
        runner.addMacro("工作", "getJob(user)");

        runner.addFunctionOfClassMethod("getDest", User.class, "getDest", new Class[]{}, null);
        runner.addMacro("目的地", "user.getDest()");

        //runner.addFunctionOfServiceMethod("包含", new ArrayList(), "contains", new Class[] { Object.class }, null);


        // 自定义操作符号   join
        class JoinOperator extends Operator {
            public Object executeInner(Object[] list) throws Exception {
                Object opdata1 = list[0];
                Object opdata2 = list[1];
                if (opdata1 instanceof java.util.List) {
                    ((java.util.List) opdata1).add(opdata2);
                    return opdata1;
                } else {
                    List result = new ArrayList();
                    result.add(opdata1);
                    result.add(opdata2);
                    return result;
                }
            }
        }
        runner.addOperator("join", new JoinOperator());

        // 自定义操作符  集合加
        class GroupOperator extends Operator {
            public GroupOperator(String aName) {
                this.name = aName;
            }

            public Object executeInner(Object[] list) throws Exception {
                Object result = 0;
                for (Object o : list) {
                    result = OperatorOfNumber.add(result, o, false);//根据list[i]类型（string,number等）做加法
                }
                return result;
            }
        }
        runner.addFunction("group", new GroupOperator("group"));

    }

    @Test
    public void testDemo5() throws Exception {
        String express = "group(1,2,3)";
        System.out.println("表达式计算：" + express + " 处理结果： " + runner.execute(express, null, null, false, false));
    }

    public int getAge(User user) {
        return user.getAge();
    }

    public String getJob(User user) {
        return user.getJob();
    }

    // +,-,*,/,<,>,<=,>=,==,!=,<>【等同于!=】,%,mod【取模等同于%】,
    @Test
    public void testDemo1() throws Exception {
        ExpressRunner runner = new ExpressRunner();

        String express1 = "((1+2) >= 3) == false";
        System.out.println("表达式计算：" + express1 + " 处理结果： " + runner.execute(express1, null, null, false, false));

        String express2 = "(5 mod 2) != 7";
        System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false));
    }

    //  and(&&) or(||)  !(非)  in【类似sql】  like【类似sql】
    @Test
    public void testDemo2() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        String express1 = "!(false and true)";
        System.out.println("表达式计算：" + express1 + " 处理结果： " + runner.execute(express1, null, null, false, false));

        String express2 = "5 in(1,2,3)";
        System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false));

        String express3 = "'abc' like 'ab%'";
        System.out.println("表达式计算：" + express3 + " 处理结果： " + runner.execute(express3, null, null, false, false));

    }

    public void permission(User user, String express) throws Exception {
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        expressContext.put("user", user);
        int result = (Integer) runner.execute(express, expressContext, null, false, false);
        System.out.println("用户" + user.getName() + "授信：" + result);
    }

    // if then else
    @Test
    public void testDemo3() throws Exception {
        Demo demo = new Demo();
        demo.init();

        //String express1 = "如果 年龄< 18 则 0 否则 { 如果 年龄 < 30 则 500 否则 {如果 年龄 < 50 则 1000 否则 100}}";
        //User user = new User("张三", 35, "农民");
        //demo.permission(user, express1);

        String express3 = "如果 list.contains(目的地) 则 0 否则 外地金额 ";
        IExpressContext<String, Object> expressContext = new DefaultContext<String, Object>();
        User user = new User();
        user.setDest("西安");
        expressContext.put("user", user);
        List<String> list = new ArrayList<String>();
        list.add("南京");
        list.add("北京");
        expressContext.put("list", list);
        expressContext.put("外地金额", 90);
        int result = (Integer) runner.execute(express3, expressContext, null, false, true);
        System.out.println("金额：" + result);
        //System.out.println("用户" + user.getName() + "授信：" + result);

        //String express2 = "如果 (如果 1==2 则 false 否则 true) 则 {2+2} 否则 {20 + 20}";
        //System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false));
    }

    @Test
    public void testDemo4() throws Exception {
        String express = "1 join 2 join 3";
        Object r = runner.execute(express, null, null, false, false);
        System.out.println("表达式计算：" + express + " 处理结果： " + runner.execute(express, null, null, false, false));
    }
}

class User {
    private String name;
    private int age;
    private String job;
    private String dest;//目的地

    public User() {
    }

    public User(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}

class UserInfo {
    long id;
    long tag;
    String name;


    public UserInfo(long aId, String aName, long aUserTag) {
        this.id = aId;
        this.tag = aUserTag;
        this.name = aName;
    }

    public String getName() {
        return name;
    }

    public long getUserId() {
        return id;
    }

    public long getUserTag() {
        return tag;
    }
}