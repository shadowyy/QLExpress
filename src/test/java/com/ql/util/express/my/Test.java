package com.ql.util.express.my;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

/**
 * @author shadowyy
 * @version 2020/3/18 15:29
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //test12();
        //test27();
        //test42();

        test90();


    }

    private static void test12() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    /**
     * 集合新建
     */
    private static void test27() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "abc = NewMap(1:1,2:2); return abc.get(1) + abc.get(2);";
        Object r = runner.execute(express, context, null, false, false);
        System.out.println(r);
        express = "abc = NewList(1,2,3); return abc.get(1)+abc.get(2)";
        r = runner.execute(express, context, null, false, false);
        System.out.println(r);
        express = "abc = [1,2,3]; return abc[1]+abc[2];";
        r = runner.execute(express, context, null, false, false);
        System.out.println(r);
    }

    /**
     * 出差补贴天数
     */
    private static void test() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "if (start_hour<=12 and end_hour<=12)\n" +
                "0.5\n" +
                "else if (start_hour<=12 and end_hour>12)\n" +
                "1\n" +
                "else if (start_hour>12 and end_hour<=12)\n" +
                "0\n" +
                "else if (start_hour>12 and end_hour>12)\n" +
                "0.5\n";
        context.put("start_hour", 13);
        context.put("end_hour", 13);
        Object r = runner.execute(express, context, null, false, true);
        System.out.println(r);
    }

    /**
     * 出差天数
     */
    private static void test71() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        String express = "";
        context.put("start_hour", 13);
        context.put("end_hour", 13);

        Object r = runner.execute(express, context, null, false, true);
        System.out.println(r);

    }

    /**
     * 发票监制章检查
     */
    private static void test90() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        String express = "String year;\n" +
                "if (vatInvoice.invoiceCode.length()==10){\n" +
                "    year=vatInvoice.invoiceCode.substring(4,6);\n" +
                "}else if (vatInvoice.invoiceCode.length()==12){\n" +
                "    year=vatInvoice.invoiceCode.substring(5,7);\n" +
                "}else{\n" +
                "    year=\"0\";\n" +
                "}\n" +
                "if(Integer.parseInt(year)>=19){\n" +
                "   return \"合规\";\n" +
                "}else{\n" +
                "   return \"不合规\";\n" +
                "}\n";

        VatInvoice vatInvoice = new VatInvoice();
        //vatInvoice.setInvoiceCode("1234567890");
        vatInvoice.setInvoiceCode("123456789012");
        context.put("vatInvoice", vatInvoice);

        Object r = runner.execute(express, context, null, false, true);
        System.out.println(r);
    }

    /**
     * 增值税发票购买方-XXX公司校验
     */
    private static void test119() throws Exception {
        ExpressRunner runner = new ExpressRunner(false, false);
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        String express = "";

        VatInvoice vatInvoice = new VatInvoice();
        vatInvoice.setBuyerId("91320104302767442H");
        context.put("vatInvoice", vatInvoice);

        Object r = runner.execute(express, context, null, false, true);
        System.out.println(r);
    }


}
