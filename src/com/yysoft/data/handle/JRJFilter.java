package com.yysoft.data.handle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yysoft.entity.ReportNotice;
import com.yysoft.entity.Stock;

public class JRJFilter {
	private static Logger logger = LogManager.getLogger(JRJFilter.class); //
	/**
	 * Description:
	 * @param rns
	 * @return
	 */
	public static HashSet<ReportNotice> removeDuplicate(HashSet<ReportNotice>  jrjRN,ArrayList<ReportNotice> jrjRNTab){

		//stock_financial_gather_basic对象
		String codesJRJTabStr ="";
		for(ReportNotice rn:jrjRNTab){
			if(rn.getNum()!=1){
				codesJRJTabStr +=rn.getCode().toLowerCase() + rn.getYear() + rn.getQuarter()+ rn.getPublishDate() +",";
			}
		}
		
		Iterator<ReportNotice> it = jrjRN.iterator(); 
		//基于stock_company,stock_financial_gather_basic表
		while(it.hasNext()){  
			ReportNotice rn = it.next(); 

            if(codesJRJTabStr.contains(rn.getCode().toLowerCase() + rn.getYear()  + rn.getQuarter()+ rn.getPublishDate())){  
            	it.remove(); 
                continue;
            } 
        }
		return jrjRN;
		
	}
	
	
	/**
	 * Description:过滤退市的、非沪深A股、非正式财报
	 * @param rns
	 * @return
	 */
	public static HashSet<ReportNotice> selectAJRJData(HashSet<ReportNotice>  jrjRN,ArrayList<Stock> stocksTab){
		String stocksCodeStr="";
		//company对象
		for(Stock s:stocksTab){
			stocksCodeStr +=s.getCode()+",";
		}
		Iterator<ReportNotice> it = jrjRN.iterator(); 
		//基于stock_company
		while(it.hasNext()){  
			ReportNotice rn = it.next(); 
            if(!stocksCodeStr.contains(rn.getCode())  ){  
            	it.remove(); 
                continue;
            } 
        }
		it = jrjRN.iterator(); 
		//过滤非正式财报类型
		while(it.hasNext()){  
			ReportNotice rn = it.next(); 
            if(!rn.getReType().equals("年度报告") && !rn.getReType().contains("季度季度报告") && !rn.getReType().contains("中期报告")){  
            	it.remove(); 
                continue;
            } 
        }
		int c =0;
		while(it.hasNext()){ 
			c++;
			ReportNotice rn = it.next(); 
        }
		return jrjRN;
		}
}
