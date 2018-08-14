/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress,int N){
    	HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
    	ArrayList<ValidatorThread> lvt = new ArrayList<ValidatorThread>();    	
    	for(int i = 0;i < N-1;i++) {
			lvt.add(new ValidatorThread((skds.getRegisteredServersCount() / N) * i,(skds.getRegisteredServersCount() / N) * (i+1) - 1,ipaddress));
		}
    	lvt.add(new ValidatorThread((skds.getRegisteredServersCount() / N) * (N-1),((skds.getRegisteredServersCount() / N) * (N - 1) + (skds.getRegisteredServersCount() / N) + (skds.getRegisteredServersCount() % N)) - 1,ipaddress));
    	for(int i = 0;i < lvt.size();i++) {
    		lvt.get(i).start();
    	}
    	for(int i = 0;i < lvt.size();i++) {
    		try {
				lvt.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	int clc = 0;
    	for(int i = 0;i < lvt.size();i++) {
//    		System.out.println("El hilo " + i + " revisó " + lvt.get(i).checkedLists() + " listas");
    		clc += lvt.get(i).checkedLists();
    	}
    	int oc = 0;
    	for(int i = 0;i < lvt.size();i++) {
    		oc += lvt.get(i).quantityOcurrences();
    	}
    	if (oc >= BLACK_LIST_ALARM_COUNT) {
			skds.reportAsNotTrustworthy(ipaddress);
		} else {
			skds.reportAsTrustworthy(ipaddress);
		}
    	for(int i = 1;i < lvt.size();i++) {
    		lvt.get(0).listOccurrences().addAll(lvt.get(i).listOccurrences());
    	}
    	LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{clc, skds.getRegisteredServersCount()});
    	return lvt.get(0).listOccurrences();
        
//        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
//        
//        int ocurrencesCount=0;
//        
//        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
//        
//        int checkedListsCount=0;
//        
//        for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
//            checkedListsCount++;
//            
//            if (skds.isInBlackListServer(i, ipaddress)){
//                
//                blackListOcurrences.add(i);
//                
//                ocurrencesCount++;
//            }
//        }
//        
//        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
//            skds.reportAsNotTrustworthy(ipaddress);
//        }
//        else{
//            skds.reportAsTrustworthy(ipaddress);
//        }                
//        
//        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
//        
//        return blackListOcurrences;
    }
    
    
//    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
