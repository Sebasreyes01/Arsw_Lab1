package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.List;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/**
 * @author Sebastián Reyes
 */
public class ValidatorThread extends Thread {

	private int a;
	private int b;
	private int o;
	private String ip;
	private int checkedListsCount;
	private LinkedList<Integer> blackListOccurrences;
	private static final int BLACK_LIST_ALARM_COUNT=5;
	
	/**
	 * Constructor of the class.
	 * @param a It is the beginning of the range of servers that is going to be checked.
	 * @param b It is the end of the range of servers that is going to be checked.
	 * @param ip It is the ip address that is going to be checked.
	 */
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
		for (int i = a; i <= b && o < BLACK_LIST_ALARM_COUNT; i++) {
			checkedListsCount++;
			if (skds.isInBlackListServer(i, ip)) {
				blackListOccurrences.add(i);
				o++;
			}
		}
	}
	
	/**
	 * @return The quantity of occurrences of the ip in the servers.
	 */
	public int quantityOccurrences() {
		return o;
	}
	
	/**
	 * @return The number of the lists where there was an occurrence.
	 */
	public List<Integer> listOccurrences() {
		return blackListOccurrences;
	}
	
	/**
	 * @return The number of lists checked.
	 */
	public int checkedLists() {
		return checkedListsCount;
	}

}
