package com.example.demo.common.util.dzhd;

import cn.hutool.core.convert.NumberChineseFormatter;
import com.example.demo.domain.vo.BatchReceiptData;
import com.example.demo.domain.vo.BatchDetailData;
import com.example.demo.domain.vo.SingleReceiptData;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 浙江医保电子回单工具类
 *
 * @author chengp
 * @date 2021/11/17
 */
@Slf4j
public class ZjybReceiptUtil {

    /**
     * 构造私有化
     */
    private ZjybReceiptUtil() {
        //do something
    }

    /**
     * 银行Logo路径
     */
    private static final String bankLogo = "D:\\chengp\\IDEA_TEST_PATH\\nbcb.jpg";

    /**
     * 回单签章路径
     */
    private static final String signLogo = "D:\\chengp\\IDEA_TEST_PATH\\sign.jpg";

    /**
     * wkhtmltopdf工具路径
     */
    private static final String toPdfTool = "D:\\chengp\\tools\\wkhtmltopdf\\bin\\wkhtmltopdf";

    /**
     * 浙江医保生成电子回单（批量交易）
     *
     * @param batchReceiptData 批量数据
     * @param localPath        本地路径
     * @param fileName         文件名
     * @return 生成结果
     */
    public static boolean createBatchReceiptFile(BatchReceiptData batchReceiptData, String localPath, String fileName) {
        StringBuffer sb = new StringBuffer();
        //批量汇总信息录入
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        sb.append(" <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">\n");
        sb.append(" <head>\n");
        sb.append("     <style type=\"text/css\">\n");
        sb.append("         body{font-family:SimSun}\n");
        sb.append("			.logo-bank{position:absolute;top:-60px;left:-7px;z-index:-1}\n");
        sb.append("			.logo-sign{position:absolute;top:160px;right:0;z-index:-1}\n");
        sb.append("			.body-title{line-height:74px;color:#000;font-weight:500;height:74px;font-size:32px;border-style:none}\n");
        sb.append("			.body-header{margin-bottom:20px}\n");
        sb.append("			.body-header-row{width:100%;display:inline-block}\n");
        sb.append("			.body-header-row div{width:45%;display:inline-block}\n");
        sb.append("			.body-header-row div span{display:inline-block;height:34px;font-size:14px;text-align:left}\n");
        sb.append("			.body-header-row div span:nth-child(1n){width:47%}\n");
        sb.append("			.body-centes{font-size:12px;display:inline}\n");
        sb.append("			.body-centes div{display:inline-block;width:20%}\n");
        sb.append("			.table-detail{margin-top:5px;border-color:#ffffff}\n");
        sb.append("			.tds{font-size:14px;padding:10px}\n");
        sb.append("			.centess{text-align:center}\n");
        sb.append("			.table-detail tr td:nth-child(1){width:3%}\n");
        sb.append("			.table-detail tr td:nth-child(2){width:13%}\n");
        sb.append("			.table-detail tr td:nth-child(3){width:14%}\n");
        sb.append("			.table-detail tr td:nth-child(4){width:14%}\n");
        sb.append("			.table-detail tr td:nth-child(5){width:8%}\n");
        sb.append("			.table-detail tr td:nth-child(6){width:13%}\n");
        sb.append("			.table-detail tr td:nth-child(7){width:6%}\n");
        sb.append("			.table-detail tr td:nth-child(8){width:11%}\n");
        sb.append("		</style>\n");
        sb.append("	</head>\n");
        sb.append("	<body style=\"text-align:center\">\n");
        sb.append("		<div class=\"body-title\">\n");
        sb.append("			<span><b>批量明细打印回单</b></span>\n");
        sb.append("			<img class=\"logo logo-bank\" src=\"" + bankLogo + "\" alt=\"宁波银行\" width=\"200\"\n");
        sb.append("				height=\"160\" />\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"body-header\">\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">银行批次号：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getYhpch() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">企业备注/批次信息：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getPcbz() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">转出账号：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getZczh() + " </span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">转出户名：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getZchm() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right\">总笔数：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getZje() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right\">总金额：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getZbs() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">此交易状态：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getJyzt() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right;vertical-align:text-top\">预约转账时间：</span>\n");
        sb.append("					<span style=\"text-align:left;vertical-align:text-top\">" + batchReceiptData.getYyzzsj() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right\">成功笔数：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getCgbs() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right\">成功金额：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getCgje() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right\">失败笔数：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getSbbs() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right\">失败金额：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getSbje() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"body-header-row\">\n");
        sb.append("				<div class=\"body-header-row-left\">\n");
        sb.append("					<span style=\"text-align:right\">其他状态笔数：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getQtztbs() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("				<div class=\"body-header-row-right\">\n");
        sb.append("					<span style=\"text-align:right\">其他状态金额：</span>\n");
        sb.append("					<span style=\"text-align:left\">" + batchReceiptData.getQtztje() + "</span>\n");
        sb.append("				</div>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<img class=\"logo logo-sign\" src=\"" + signLogo + "\" alt=\"回单专用章\" width=\"200\"\n");
        sb.append("			height=\"100\" />\n");
        sb.append("		<div class=\"body-centes\">\n");
        sb.append("			<div style=\"margin-left:100px\">录入员：" + batchReceiptData.getLry() + "</div>\n");
        sb.append("			<div>录入时间：" + batchReceiptData.getLrsj() + "</div>\n");
        sb.append("			<div style=\"margin-left:20px\">复核员：" + batchReceiptData.getFhy() + "</div>\n");
        sb.append("			<div>复核时间：" + batchReceiptData.getFhsj() + "</div>\n");
        sb.append("		</div>\n");
        //批量明细信息录入
        sb.append("		<table class=\"table-detail\" width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\">\n");
        sb.append("			<thead>\n");
        sb.append("				<tr>\n");
        sb.append("					<td class=\"tds tdWhdth3 centess\"><b>编号</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>网银流水号</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>收款方账号</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>户名</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth4 centess\"><b>金额</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>开户行信息</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth3 centess\"><b>交易状态</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>备注</b></td>\n");
        sb.append("					<td class=\"tds tdWhdth1 centess\"><b>失败原因</b></td>\n");
        sb.append("				</tr>\n");
        sb.append("			</thead>\n");
        sb.append("			<tbody>\n");
        for (BatchDetailData batchDetailData : batchReceiptData.getBatchDetailDataList()) {
            sb.append("             <tr>\n");
            sb.append("                 <td class=\"tds tdWhdth3 centess\">" + batchDetailData.getBh() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getWylsh() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getSkfzh() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getSkfhm() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth4 centess\">" + batchDetailData.getJyje() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getKhhxx() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth3 centess\">" + batchDetailData.getJyzt() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getBz() + "</td>\n");
            sb.append("                 <td class=\"tds tdWhdth1 centess\">" + batchDetailData.getSbyy() + "</td>\n");
            sb.append("             </tr>\n");
        }
        sb.append("			</tbody>\n");
        sb.append("		</table>\n");
        sb.append("	</body>\n");
        sb.append("</html>\n");

        //判断本地路径是否存在，不存在则创建
        File fileDirectory = new File(localPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }
        //生成回单html文件
        String htmlFilePath = localPath + File.separator + fileName.replace(".pdf", ".html");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlFilePath)), "UTF-8"))) {
            bw.write(sb.toString());
        } catch (Exception e) {
            log.error("批量电子回单html文件生成失败：{}", e.getLocalizedMessage());
            return false;
        }
        log.info("批量电子回单html文件生成成功");
        //html文件转pdf文件
        String pdfFilePath = localPath + File.separator + fileName;
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append(" --enable-local-file-access ");
        //设置页面上边距
        cmd.append(" --margin-top 10mm ");
        //设置页面底边距 (default 10mm)
        cmd.append(" --margin-bottom 10mm ");
        //设置页眉字体大小
        cmd.append(" --header-font-size 12");
        //设置页眉内容
        cmd.append(" --header-right 批交易回单打印");
        //设置页眉和内容的距离
        cmd.append(" --header-spacing 5");
        //设置页脚字体大小
        cmd.append(" --footer-font-size 12");
        //设置页脚内容
        cmd.append(" --footer-center 第[page]页/共[topage]页");
        cmd.append(" --footer-right [date]");
        //设置页脚和内容的距离
        cmd.append(" --footer-spacing 5 ");
        cmd.append(htmlFilePath);
        cmd.append(" ");
        cmd.append(pdfFilePath);
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            log.error("批量电子回单html文件转pdf文件失败：{}", e.getLocalizedMessage());
            return false;
        }
        if (!new File(pdfFilePath).exists()) {
            log.info("批量电子回单html文件转pdf文件失败：执行转换命令异常");
            return false;
        }
        log.info("批量电子回单html文件转pdf文件成功");
        return true;
    }


    /**
     * 浙江医保生成电子回单（单笔交易）
     *
     * @param singleReceiptData 单笔数据
     * @param localPath         本地路径
     * @param fileName          文件名
     * @return 生成结果
     */
    public static boolean createSingleReceiptFile(SingleReceiptData singleReceiptData, String localPath, String fileName) {
        //数字金额转大写
        String dxje = NumberChineseFormatter.format(singleReceiptData.getJyje().doubleValue(), true, true);
        singleReceiptData.setDxje(dxje);
        //单笔信息录入
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        sb.append("	<head>\n");
        sb.append("		<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />\n");
        sb.append("		<style type=\"text/css\">\n");
        sb.append("		body{font-family:simsun}\n");
        sb.append("		.title{width:100%;text-align:center;font-size:25px;margin-bottom:3mm}\n");
        sb.append("		.content-item{display:inline-block;margin-right:10vw;padding:3px;font-size:15px;margin:2px}\n");
        sb.append("		.col-3{width:25%}\n");
        sb.append("		.col-4{width:25%}\n");
        sb.append("		.col-5{width:18%}\n");
        sb.append("		.content-item-special{display:inline-block;padding:2px;font-size:13px;margin:2px}\n");
        sb.append("		.content-item span,.content-item-special span{display:inline-block;vertical-align:top}\n");
        sb.append("		.content-item span:nth-child(1){word-wrap:normal}\n");
        sb.append("		.content-item span:nth-child(1){margin-left:5px}\n");
        sb.append("		.logo-sign{position:absolute;top:25%;right:60px;z-index:-1}\n");
        sb.append("		.clearFloat::after {clear:both}\n");
        sb.append("		</style>\n");
        sb.append("	</head>\n");
        sb.append("	<body>\n");
        sb.append("		<img class=\"logo-sign\" src=\"" + signLogo + "\" alt=\"回单专用章\" width=\"220\" height=\"110\" />\n");
        sb.append("		<div class=\"title\">\n");
        sb.append("			<div><b>业务回单（收款）</b></div>\n");
        sb.append("		</div>");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>入账日期：</span>\n");
        sb.append("				<span>" + singleReceiptData.getRzrq() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span style=\"margin-left:35px\">回单编号：</span>\n");
        sb.append("				<span>" + singleReceiptData.getHdbh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>付款方账号：</span>\n");
        sb.append("				<span>" + singleReceiptData.getFkfzh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item-special\" style=\"float: right;margin-top:5px\">\n");
        sb.append("				<span>我行吸收的本外币存款依照《存款保险条例》受到保护。</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div>\n");
        sb.append("		    <div class=\"content-item\">\n");
        sb.append("		        <span>多级账簿号：</span>\n");
        sb.append("		        <span>" + singleReceiptData.getFkdjzbh() + "</span>\n");
        sb.append("		    </div>\n");
        sb.append("		<div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>付款方户名：</span>\n");
        sb.append("				<span>" + singleReceiptData.getFkfhm() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>多级账簿名：</span>\n");
        sb.append("				<span>" + singleReceiptData.getFkdjzbm() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>付款方开户行：</span>\n");
        sb.append("				<span>" + singleReceiptData.getFkfkhh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>收款方账号：</span>\n");
        sb.append("				<span>" + singleReceiptData.getSkfzh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div>\n");
        sb.append("		    <div class=\"content-item\">\n");
        sb.append("		        <span>多级账簿号：</span>\n");
        sb.append("		        <span>" + singleReceiptData.getSkdjzbh() + "</span>\n");
        sb.append("		    </div>\n");
        sb.append("		<div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>收款方户名：</span>\n");
        sb.append("				<span>" + singleReceiptData.getSkfhm() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>多级账簿名：</span>\n");
        sb.append("				<span>" + singleReceiptData.getSkdjzbm() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>收款方开户行：</span>\n");
        sb.append("				<span>" + singleReceiptData.getSkfkhh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item col-3\">\n");
        sb.append("				<span style=\"vertical-align:middle\">币种：</span>\n");
        sb.append("				<span style=\"font-size:23px;vertical-align:middle\">" + singleReceiptData.getBz() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item col-3\">\n");
        sb.append("				<span style=\"vertical-align:middle\">金额：</span>\n");
        sb.append("				<span style=\"font-size:23px;vertical-align:middle\">" + singleReceiptData.getJyje() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>金额（大写）：</span>\n");
        sb.append("				<span>" + singleReceiptData.getDxje() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row clearFloat\" style=\"position:relative\">\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>交易时间：</span>\n");
        sb.append("				<span>" + singleReceiptData.getJysj() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>日志号：</span>\n");
        sb.append("				<span>" + singleReceiptData.getRzh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>状态：</span>\n");
        sb.append("				<span>" + singleReceiptData.getZt() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>摘要：</span>\n");
        sb.append("				<span>" + singleReceiptData.getZy() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item\" style=\"position:absolute\">\n");
        sb.append("				<span>渠道：</span>\n");
        sb.append("				<span style=\"width:130px;vertical-align:text-top\">" + singleReceiptData.getQd() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row\">\n");
        sb.append("			<div class=\"content-item col-5\">\n");
        sb.append("				<span>打印日期：</span>\n");
        sb.append("				<span>" + singleReceiptData.getDyrq() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span>用户：</span>\n");
        sb.append("				<span style=\"width:120px;vertical-align:text-top\">" + singleReceiptData.getYh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("			<div class=\"content-item col-5\"></div>\n");
        sb.append("			<div class=\"content-item\">\n");
        sb.append("				<span style=\"margin-left:-11px\">打印行号：</span>\n");
        sb.append("				<span>" + singleReceiptData.getDyhh() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("		<div class=\"content-row clearFloat\" style=\"position:relative\">\n");
        sb.append("			<div class=\"content-item\" style=\"position:absolute\">\n");
        sb.append("				<span>附言：</span>\n");
        sb.append("				<span style=\"width:800px;vertical-align:text-top;white-space:pre-line\">" + singleReceiptData.getFy() + "</span>\n");
        sb.append("			</div>\n");
        sb.append("		</div>\n");
        sb.append("	</body>\n");
        sb.append("</html>\n");

        //判断本地路径是否存在，不存在则创建
        File fileDirectory = new File(localPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }

        //生成回单html文件
        String htmlFilePath = localPath + File.separator + fileName.replace(".pdf", ".html");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlFilePath)), "UTF-8"))) {
            bw.write(sb.toString());
        } catch (Exception e) {
            log.error("单笔电子回单html文件生成失败：{}", e.getLocalizedMessage());
            return false;
        }
        log.info("单笔电子回单html文件生成成功");

        //html文件转pdf文件
        String pdfFilePath = localPath + File.separator + fileName;
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append(" --enable-local-file-access ");
        //设置页面上边距
        cmd.append(" --margin-top 10mm ");
        //设置页面底边距 (default 10mm)
        cmd.append(" --margin-bottom 10mm ");
        cmd.append(htmlFilePath);
        cmd.append(" ");
        cmd.append(pdfFilePath);
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            log.error("单笔电子回单html文件转pdf文件失败：{}", e.getLocalizedMessage());
            return false;
        }
        if (!new File(pdfFilePath).exists()) {
            log.info("单笔电子回单html文件转pdf文件失败：执行转换命令异常");
            return false;
        }
        log.info("单笔电子回单html文件转pdf文件成功");
        return true;
    }

}
