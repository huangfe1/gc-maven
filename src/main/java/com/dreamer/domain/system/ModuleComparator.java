package com.dreamer.domain.system;

import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
public class ModuleComparator implements Comparator<Module> ,java.io.Serializable{
	@Override
	public int compare(Module o1, Module o2) {
		// TODO Auto-generated method stub
		return o1.getSequence().compareTo(o2.getSequence());
	}

}
