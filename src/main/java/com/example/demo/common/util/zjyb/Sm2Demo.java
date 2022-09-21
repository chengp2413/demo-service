package com.example.demo.common.util.zjyb;

/**
 * @ClassName Sm2Demo
 * @Description:
 * @Author xudan
 * @Date 2021/6/1 
 * @Version V1.0
 **/
public class Sm2Demo {
    public static void main(String[] args) throws Exception{
        String str = "MDQwN0Y4NTUxM0Y4NDI1NTU2QkJGMEY2NTg3NkZCMTA5RkY0MDNFODg1Mzg3RTdCNTcxMTU4OTE2MEJBMDZEQjlFN0NGMTU5N0M0NkZCNjA4QjY1OTQyMjY5OUZBNzQ3OEE1NUFDNEVBQkNCRjRFMkQwNzc2NDBFOEFBRkZDQkU2OEU2MEJCRDc4QUU3NTlDMTlGRjBCMkZGQzYwNjAwODkwQjA1QkVDNkI0OEZGNjA1N0UzMDgxQzU2ODhBNUMzQUQ3NENBNDFFMkY3REY0QTU5NDVENDAxREVCNTEwMEQxRTk5RDQ2NjY5RkQ1MjE3OEM3N0Q1RUFBOTJGMERDMkU3QkQzQUI1QURFQ0FCMTlBNzc5QkYyNjgxODdFNEI1NDdGMEFBOTUwNEY4NzYzODUwQzkzODU3OUI4OUU3OEE2NTg4RDZGNzVCQkQ0NkMwNjdEMTFFQ0NGM0UxNkFGNEFENEFEOEI0RTZGMDBGMkEwODE0Q0NDOTVCNzc5NUEyRjA5RjE4NTdBMDIzM0Q0NkU3MTRDRkMyOEYwNjExQjQyRjJGMjUwRjY2RkMzNjBGQzg4NDA3MjdCRUI3MkE5NzEwODgyMzQ1Q0I0ODM4NzZBQUYzNERDQjM4NkNERUM4OTUzMjFGRjI0NDE5OTYxRDY0RDcyRjI3QTUwOTVGOEIzREI4MDEwNTg3NUZCMEIwMkJDRjY4NENGMEM1NDFDQ0I4NDYzRDI3QTFFRjc3NzhCOTI3OUM2MEFEODlCMzIzODJCNTE2MzBGQTk3RDc3QUNBM0U0RTQyMEVCNkU0M0RBQzJGMUU0RTcxRTc2QzA5ODM5OTk4ODBDODc3QzBDODA3NUE2MDYwMUNCNUYyRDQ2NjlDNjNDNTUwQ0JBODRFNEFFMDQzQzI0OUVCOEQ1MDdFRTQwNEEyNzI2RjAyNUVDMUQ0N0E2NTc1MTYwRjNBMjgwRDRFNTg2RkE1OEE1RkJBMUNBNDc0Rjc1MjE5OEMzQTJBNUI0RjgyRTk4RUI3RDMzODY3NUEwMkVEMDQ0RjdFMjg0NUQzRDU4NDY1NjMwODNENDA5OUQ4MTk1ODZGNTBBNUM4QTRCNjc3MEM1OEY3Nzk3MTMwNkNDNDlFMzA4ODg1RkYzNkFGREFBMjlGNzFCMzM5QkQwNDMyRUY3QTlCQ0FCMUU5NEJGQUVDQTZDQTg1RDBBNzE2OTQ1OEVGNkY2REMxM0VDMTUwNzQwM0M4QTdGNEQ2N0U5NUFDN0Y3NjRDQTcyMzdDNTYxOURGNEYxQkNEODIxRkQ4QTNENEEyMTFCNDI1NkRGMjUzM0JGNDE2NDIyQzRFQUM1MUQyREFGRjEyNzUxMEZBQzgxNDhBNEZFQ0U3OEYzNzJDRTUzQzk2RDY2QzVBMEI1MzFCNkJFRjJCNTYzRjc0NzlCQzNEODQzMTk1NDgxREQxNzY1QUE4MjBGMTE2N0RFRUEwRTYwRDVFNDc2RjIyOEVERDhDMzQxM0I1QzgyRUJEM0Q1RjcyMDNBRkYzRjEzQ0Q2RUQxQjYyRUNGN0M2NkQ2QjVEMUUxOUJFRkZFMEM3NTczMTAzNTA3QkVDMjEzOTI3QTE5ODVCQjMzRDBBRkUyOUMzNjI4MTFDRkU1MzNFNDQyNUM1NTAzNDc3QzcyQjQ0QzU4QkQ3NzdCOUVDNzZFMzdBRjJENEJDMDJERjU1RjVBODhEQ0NDODI2Njc1RTBCQkZEMzNEMjZERUNGNDZENThGN0U1MThBRTQ0NUU3M0I0NTZCNDc1MTQ4QjkwQzlEQzBDRUIzMDFDODVERDMxODEyNDkzQzQ3MjExNjUzNzIyNzZFMEJENDE5MkQ0QTA0Q0ZDNDZDNTNFOEMzMkREMDc0MDA3OUJFMjkwQjg2NUQ5RTEwMEIxMjREODlEQjQ4OTE2MUVGRjFBQkFFNTQ1MTZFMTEzNDY4ODc1OUY0MDZCNUE3QkQwQzA3NzYxRTVGRkUxQjc1NEJFRjdBMEQyOTFGQkE2NDk1MjMyN0I1NzQzODI0ODNFNzJFOUQzMzA2NEM1RkRCRjkwRjk0NEEwNzFBQUU0RThBNzAzNTc5OEUxNjVCMUNENUIwOEZDQjBFODE5M0Q4QTI0RjA4OTA2OUU1QjhGNEMzODlGMzJFMDExNzQzODQzNzdGQ0UwMTcxM0EwNzJFQzNGQUEyQUM0NzdBMEU3QzQ0OEVEMjZERjVGN0Q5";
        String privateKey = "";
        String publicKey = "";
        //加密decryptMessage = SM2Util.encrypt(publicKey, new String(str.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
        String encryptionResponseXml = com.example.demo.common.util.zjyb.SafeUtil.decode(str,"UTF-8");
        String responseMessage = SM2Util.decrypt(privateKey, encryptionResponseXml);
        System.out.println(responseMessage);
    }
}
