package edu.yu.intro;

import java.lang.*;
import java.util.*;

public enum Patrons {
	
	Singleton; 
    private Set<Patron> patronList;
  
    private Patrons() {
    	patronList = new HashSet<>();
    }
  
	public void add(Patron patron) {
		patronList.add(patron);
	}

	public void clear() {
		patronList.clear();
	}
	
	public Set<Patron> getList() {
		return patronList;
	}
	
	public Patron get(String uuid) {
		Patron patron1 = null;
		if (uuid == null || uuid.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		for (Patron patron : patronList) {
			if (patron.getId().equals(uuid)) {
				patron1 = patron;
			}
			else {
				patron1 = null;
			}
		}
		return patron1;
	}

	public int nPatrons() {
		return patronList.size();
	}
	
	public Set<Patron> byLastNamePrefix(final String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException();
		}
		if (prefix.replaceAll("\\s", "").toString().length() == 0) {
			return patronList;
		}
		Set<Patron> patronNewList = new HashSet<>();
		List<Patron> patronArrayList = new ArrayList<>(patronList);
		for (Patron patron : patronArrayList) {
			if (patron.getLastName().startsWith(prefix)) {
				patronNewList.add(patron);
			}
		}
		System.out.println(patronNewList);
		return patronNewList;
	}
	
}