package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class ValidatorThread extends Thread {

	private int a;
	private int b;
	private int o;
	private String ip;
	private int checkedListsCount;
	private LinkedList<Integer> blackListOccurrences;
	private static final int BLACK_LIST_ALARM_COUNT=5;
	private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

	public ValidatorThread(int a, int b, String ip) {
		this.a = a;
		this.b = b;
		this.ip = ip;
	}

	@Override
	public void run() {
		blackListOccurrences = new LinkedList<>();
		o = 0;
		HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
		checkedListsCount = 0;
		for (int i = a; i < b && o < BLACK_LIST_ALARM_COUNT; i++) {
			checkedListsCount++;
			if (skds.isInBlackListServer(i, ip)) {
				blackListOccurrences.add(i);
				o++;
			}
		}
//		if (o >= BLACK_LIST_ALARM_COUNT) {
//			skds.reportAsNotTrustworthy(ip);
//		} else {
//			skds.reportAsTrustworthy(ip);
//		}
//		LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", 
//				new Object[] { checkedListsCount, b });
	}

	public int quantityOcurrences() {
		return o;
	}
	
	public List<Integer> listOccurrences() {
		return blackListOccurrences;
	}
	
	public int checkedLists() {
		return checkedListsCount;
	}

}
