package com.yysoft.mgr;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yysoft.dao.StockDAO;
import com.yysoft.data.handle.DividendComReponse;
import com.yysoft.data.handle.GeneralCrawler;
import com.yysoft.entity.FinancialMetaData;
import com.yysoft.util.ConfigParser;
import com.yysoft.util.Constant;

/**
 * @author 作者 E-mail: sunbaixiang
 * @date 创建时间：2016年5月3日 下午12:18:49
 * @version 1.0
 * @parameter 金融界,核心数据对照采集:接口
 *            http://stock.jrj.com.cn/report/js/gscb/2015-12-31.js?ts=
 *            1460102840965
 * @return
 */
public class DividendComparisonJRJ2 {
	private static Logger logger = LogManager.getLogger("fcProcessLoger"); //执行流程日志

	public static void gather() {
		new ConfigParser();
		int year = 2016;
		for (; year >= 2016; year--) {
			BufferedReader[] details = new BufferedReader[4];
			String url1 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-12-31.js?ts=1460102840965";// 年报入口
			String url2 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-09-30.js?ts=1460102840965";// 三季度报入口
			String url3 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-06-30.js?ts=1460102840965";// 半年报入口
			String url4 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-03-31.js?ts=1460102840965";// 一季度报入口

			ArrayList<FinancialMetaData> dividends1 = new ArrayList<FinancialMetaData>();
			ArrayList<FinancialMetaData> dividends2 = new ArrayList<FinancialMetaData>();
			ArrayList<FinancialMetaData> dividends3 = new ArrayList<FinancialMetaData>();
			ArrayList<FinancialMetaData> dividends4 = new ArrayList<FinancialMetaData>();

			details[0] = GeneralCrawler.getPageBufferedReader(url1, Constant.ENCODEGBK);
			details[1] = GeneralCrawler.getPageBufferedReader(url2, Constant.ENCODEGBK);
			details[2] = GeneralCrawler.getPageBufferedReader(url3, Constant.ENCODEGBK);
			details[3] = GeneralCrawler.getPageBufferedReader(url4, Constant.ENCODEGBK);

			dividends1 = DividendComReponse.ParseSheetJRJ2(details[0], year + "-12-31");// 年报
			dividends2 = DividendComReponse.ParseSheetJRJ2(details[1], year + "-09-30");// 三季度包
			dividends3 = DividendComReponse.ParseSheetJRJ2(details[2], year + "-06-30");// 半年报
			dividends4 = DividendComReponse.ParseSheetJRJ2(details[3], year + "-03-31");// 一季度报

			for (FinancialMetaData div : dividends1) {
				boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
				if (flag) {
					//股本数.对应报告期
				    double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
				    if (stockTotal==0) {
						stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
					}
				    String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";
				    StockDAO.runSql(sql);
				}
			}
			for (FinancialMetaData div : dividends2) {
				boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
				if (flag) {
					//股本数.对应报告期
					  double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
					  if (stockTotal==0) {
							stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
						}
					  String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";	
					  StockDAO.runSql(sql);
				}
			}
			for (FinancialMetaData div : dividends3) {
				boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
				if (flag) {
					//股本数.对应报告期
					  double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
					  if (stockTotal==0) {
							stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
						}
					  String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";			
					  StockDAO.runSql(sql);
				}
			}
			for (FinancialMetaData div : dividends4) {
				boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
				if (flag) {
					//股本数.对应报告期
					 double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
					 if (stockTotal==0) {
							stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
						}
					 String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";
					 StockDAO.runSql(sql);
				}
			}
			}
	}

	public static void gather2() {
		new ConfigParser();
		int year = 2017;
		//未搞明白该方法啥意思,调整为最近二个季度更新总收入
		BufferedReader[] details = new BufferedReader[2];
		String url3 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-06-30.js?ts=1460102840965";// 中报入口
		String url4 = "http://stock.jrj.com.cn/report/js/gscb/" + year + "-09-30.js?ts=1460102840965";// 一季度报入口

		ArrayList<FinancialMetaData> dividends3 = new ArrayList<FinancialMetaData>();
		ArrayList<FinancialMetaData> dividends4 = new ArrayList<FinancialMetaData>();

		details[0] = GeneralCrawler.getPageBufferedReader(url4, Constant.ENCODEGBK);
		details[1] = GeneralCrawler.getPageBufferedReader(url3, Constant.ENCODEGBK);

		dividends3 = DividendComReponse.ParseSheetJRJ2(details[1], year + "-06-30");// 中报
		dividends4 = DividendComReponse.ParseSheetJRJ2(details[0], year + "-09-30");// 一季度报

    	logger.info("test2");

		for (FinancialMetaData div : dividends4) {
			boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
			if (flag) {
				//股本数.对应报告期
		    	logger.info(div.getCode());

			    double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
			    if (stockTotal==0) {
					stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
				}
			    String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";
			    StockDAO.runSql(sql);
			}
		}
		for (FinancialMetaData div : dividends3) {
			boolean flag = StockDAO.getFinancial(div.getCode(), div.getReportDate());
			if (flag) {
				//股本数.对应报告期
			    double stockTotal=StockDAO.getStockTotal(div.getCode(),div.getReportDate());
			    if (stockTotal==0) {
					stockTotal=StockDAO.getStockTotalT(div.getCode(),div.getReportDate());
				}
			    String sql = "update stock_financial set OperatingIncomeJRJ="+div.getOperatingIncomeJRJ()+",NCFFOAJRJ="+div.getNCFFOAJRJ()*stockTotal+",ONPATPJRJ="+div.getOnpatpJRJ()+",TEATTSOPCJRJ="+div.gettEATTSOPCJRJ()*stockTotal+" where Code='"+div.getCode()+"' and ReportDate='"+div.getReportDate()+"'";
			    StockDAO.runSql(sql);
			    
			    sql ="update stock_financial set OperatingIncome=OperatingIncomeJRJ where OperatingIncome=0 and OperatingIncomeJRJ !=0";
			    StockDAO.runSql(sql);
			    
			    sql ="update financial a  inner join stock_financial  b  on a.code=b.code  and a.reportdate=b.reportdate and  a.OperatingIncome!=b.OperatingIncome and a.reportdate='2017-09-30'  set a.OperatingIncome=b.OperatingIncome";
			    StockDAO.runSql(sql);
			    
			    
			}
		}
	}

	// main（）测试
	public static void main(String[] args) {
		DividendComparisonJRJ2.gather2();
		//DividendComparisonJRJ2.gather();
	}
}
