package com.dreamer.domain.user;

import com.dreamer.domain.authorization.Authorization;
import com.dreamer.domain.authorization.AuthorizationType;
import com.dreamer.domain.system.Module;
import com.dreamer.domain.system.ModuleComparator;
import com.dreamer.domain.system.Role;
import com.dreamer.domain.user.enums.AgentStatus;

import java.util.*;

public class Admin extends User {

	private String loginName;

	private static final long serialVersionUID = -8566187723208697571L;
	private Set<Role> roles = new HashSet<Role>();

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

	public List<Module> getLeafModules() {
		List<Module> modules = new ArrayList<Module>();
		roles.forEach(role -> {
			role.getModules().stream().filter(m -> m.getLeaf()).forEach(m -> {
				modules.add(m);
			});
		});
		return modules;
	}
	
	public HashMap<Integer,Module> getMyModules() {
		HashMap<Integer,Module> modules = new HashMap<Integer,Module>();
		roles.forEach(role -> {
			role.getModules().stream().filter(m -> m.getLeaf()).forEach(m -> {
				modules.put(m.getId(), m);
			});
		});
		return modules;
	}

	public Set<Module> getTopModules() {
		SortedSet<Module> modules = new TreeSet<Module>(new ModuleComparator());
		roles.forEach(role -> {
			role.getModules().stream().forEach(m -> {
				Module temp = m;
				while (Objects.nonNull(temp) && !temp.isTop()) {
					temp = temp.getParent();
				}
				modules.add(temp);
			});
		});
		return modules;
	}

//	@Override
//	public void addAdvanceRecord(Integer type, String more, Double advance) {
//
//	}
//
//	@Override
//	public void addPurchaseRecord(Integer type, String more, Double purchase) {
//
//	}





	@Override
	public boolean isAdmin() {
		return true;
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
		types.forEach(t -> {
			Authorization auth = agent.buildAuthorization(t);
			auth.setStatus(AgentStatus.ACTIVE);
			agent.addAuthorization(auth);
		});
		agent.active();
	}

	@Override
	public boolean isMutedUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void clearRoles() {
		this.roles.clear();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
