package com.dreamer.domain.user;

import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.system.Module;

import java.util.List;
import java.util.Set;

public class Visitor extends User {

	private static final long serialVersionUID = 5760546307231369466L;





	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAgent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addAuthorizationToAgent(Agent agent,
			List<AuthorizationType> types) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	@Override
	public List<Module> getLeafModules() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void addAdvanceRecord(Integer type, String more, Double advance) {
//
//	}

	@Override
	public Set<Module> getTopModules() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void addPurchaseRecord(Integer type, String more, Double purchase) {
//
//	}

	@Override
	public boolean isMutedUser() {
		// TODO Auto-generated method stub
		return false;
	}



//	@Override
//	public void addVoucherRecord(Integer type, String more, Double voucher) {
//		// TODO Auto-generated method stub
//
//	}
	
}
