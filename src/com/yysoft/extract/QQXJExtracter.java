package com.yysoft.extract;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yysoft.entity.Finance;
import com.yysoft.util.StringUtils;

/**
 * 功能描述：腾讯财经现金流量表页面数据提取
 * @author Huanyan.Lu
 * @date:2016年5月11日
 * @time:下午3:08:02
 * @version:1.0
 */
public class QQXJExtracter {
	private static Logger logger = LogManager.getLogger(QQXJExtracter.class); //

	
	/**
	 * Description:现金流量表页面数据提取
	 * @param finance 提取信息实例
	 * @param data 读取的页面元信息
	 * @param code 股票代码
	 * @param name 股票简称
	 * @param reportDate 要提取信息的报告期
	 * @return
	 */
	public static  Finance fetch(Finance finance,String data[][],String code,String name,String reportDate,boolean isBank){
		logger.info("提取的报告期："+ reportDate + "===腾讯财经现金流量表页面数据项部分赋值===");
		String dateString = "";
		for(int i=1;i<data[0].length;i++){
			if(data[0][i]==null || !data[0][i].contains(reportDate)){
				continue;
			}
			//有些页面的年度财报里季报重复，导致表格多了一列：http://stock.jrj.com.cn/share,002071,lrfpb_2014.shtml
			//在此做些过滤
			if(dateString.contains(data[0][i])){
				continue;
			}
			dateString +=data[0][i] +",";
			
			
			finance.setCode(code);
			finance.setName(name);
			finance.setReportDate(data[0][i]);//报告期
			finance.setQuarter(StringUtils.getQuarterFromReport(data[0][i]));
			finance.setYear(finance.getReportDate().substring(0,4));
			
			if(isBank==false){//非银行类页面（银行类页面比较特殊）
				//判断此页关键值是否存在，不存在则不提取并返回null
				if(StringUtils.isEmpty(data[11][i]) ||data[11][i].trim().equals("0")){//经营活动产生的现金流量净额为null
					return null;
				}
				finance.setCRFSOGAROS(data[2][i]);// 销售商品、提供劳务收到的现金
				finance.setTRRD(data[3][i]);// 收到的税费返还
				finance.setOCRCOA(data[4][i]);// 收到的其他与经营活动有关的现金
				finance.setCPFGPASR(data[6][i]);// 购买商品、接受劳务支付的现金
				finance.setCPTAFE(data[7][i]);// 支付给职工以及为职工支付的现金
				finance.setCPFTAS(data[8][i]);// 支付的各项税费
				finance.setCPFOOA(data[9][i]);// 支付其他与经营活动有关的现金
				finance.setNCFFOA(data[11][i]);// 经营活动产生的现金流量净额

				finance.setCRFDOI(data[13][i]);// 收回投资收到的现金
				finance.setIIR(data[14][i]);// 取得投资收益收到的现金
				finance.setNCFDOFAIAAOLTA(data[15][i]);// 处置固定资产、无形资产和其他长期资产收回的现金净额
				finance.setNCRFDOSAOBU(data[16][i]);// 处置子公司及其他营业单位收到的现金净额
				finance.setOCRCIA(data[17][i]);// 收到其他与投资活动有关的现金
				finance.setCPFPACOFAIAAOLTA(data[19][i]);// 购建固定资产、无形资产和其他长期资产支付的现金
				finance.setCPFAOI(data[20][i]);// 投资支付的现金/投资所支付的现金
				finance.setNCPFAOSAOBU(data[21][i]);// 取得子公司及其他营业单位支付的现金净额
				finance.setCPFOIA(data[22][i]);// 支付其他与投资活动有关的现金
				finance.setNCFFIA(data[24][i]);// 投资活动产生的现金流量净额

				finance.setCRFCC(data[26][i]);// 吸收投资收到的现金
				// finance.setCRFMSIBS(data[24][i]);//子公司吸收少数股东投资收到的现金
				finance.setBorrowingsReceived(data[27][i]);// 取得借款收到的现金
				finance.setOCRCFA(data[28][i]);// 收到其他与筹资活动有关的现金
				// finance.setCRFBI(data[27][i]);//发行债券收到的现金
				finance.setCROAB(data[30][i]);// 偿还债务支付的现金
				finance.setCPFDAPDOIP(data[31][i]);// 分配股利、利润或偿付利息支付的现金
				// finance.setDAPPTMSBS(data[31][i]);//子公司支付给少数股东的股利
				finance.setCPFOFA(data[32][i]);// 支付其他与筹资活动有关的现金
				finance.setNCFFFA(data[34][i]);// 筹资活动产生的现金流量净额
				finance.setFERFCOCACE(data[35][i]);// 汇率变动对现金的影响
				finance.setNIICACE(data[36][i]);// 现金及现金等价物净增加额
				finance.setCEATBOTP(data[37][i]);// 期初现金及现金等价物余额
				finance.setCEATEOTP(data[38][i]);// 期末现金及现金等价物余额
			}else{//银行类页面财报提取流程
				//判断此页关键值是否存在，不存在则不提取并返回null
				//因为腾讯是最后一道采集源，如果某字体空值其它字段有值则也要采，所以以下不用做处理。
//				if(StringUtils.isEmpty(data[61][i]) || data[11][i].trim().equals("0")){//经营活动产生的现金流量净额为null
//					return null;
//				}
				finance.setCRFSOGAROS(data[24][i]);// 销售商品、提供劳务收到的现金
				finance.setTRRD(data[25][i]);// 收到的税费返还
				finance.setOCRCOA(data[30][i]);// 收到的其他与经营活动有关的现金/收到其他与经营活动有关的现金
				finance.setCPFGPASR(data[55][i]);// 购买商品、接受劳务支付的现金
				finance.setCPTAFE(data[47][i]);// 支付给职工以及为职工支付的现金
				finance.setCPFTAS(data[48][i]);// 支付的各项税费
				finance.setCPFOOA(data[59][i]);// 支付其他与经营活动有关的现金
				finance.setNCFFOA(data[61][i]);// 经营活动产生的现金流量净额

				finance.setCRFDOI(data[63][i]);// 收回投资收到的现金
				finance.setIIR(data[64][i]);// 取得投资收益收到的现金
				finance.setNCFDOFAIAAOLTA(data[67][i]);// 处置固定资产、无形资产和其他长期资产收回的现金净额
				finance.setNCRFDOSAOBU(data[69][i]);// 处置子公司及其他营业单位收到的现金净额
				finance.setOCRCIA(data[70][i]);// 收到其他与投资活动有关的现金
				finance.setCPFPACOFAIAAOLTA(data[77][i]);// 购建固定资产、无形资产和其他长期资产支付的现金
				finance.setCPFAOI(data[72][i]);// 投资支付的现金
				finance.setNCPFAOSAOBU(data[78][i]);// 取得子公司及其他营业单位支付的现金净额
				finance.setCPFOIA(data[79][i]);// 支付其他与投资活动有关的现金
				finance.setNCFFIA(data[81][i]);// 投资活动产生的现金流量净额

//				finance.setCRFCC(data[23][i]);// 吸收投资收到的现金
				// finance.setCRFMSIBS(data[24][i]);//子公司吸收少数股东投资收到的现金
//				finance.setBorrowingsReceived(data[24][i]);// 取得借款收到的现金
				finance.setOCRCFA(data[88][i]);// 收到其他与筹资活动有关的现金
				 finance.setCRFBI(data[85][i]);//发行债券收到的现金
//				finance.setCROAB(data[27][i]);// 偿还债务支付的现金
				finance.setCPFDAPDOIP(data[91][i]);// 分配股利、利润或偿付利息支付的现金
				// finance.setDAPPTMSBS(data[31][i]);//子公司支付给少数股东的股利
				finance.setCPFOFA(data[94][i]);// 支付其他与筹资活动有关的现金
				finance.setNCFFFA(data[96][i]);// 筹资活动产生的现金流量净额
				finance.setFERFCOCACE(data[97][i]);// 汇率变动对现金的影响
				finance.setNIICACE(data[98][i]);// 现金及现金等价物净增加额
				finance.setCEATBOTP(data[99][i]);// 期初现金及现金等价物余额
				finance.setCEATEOTP(data[100][i]);// 期末现金及现金等价物余额
			}
			finance.setXJFlag(2);//标记采集来源为腾讯财经
			return finance;
			
		}
		return null;
	}
	
}
