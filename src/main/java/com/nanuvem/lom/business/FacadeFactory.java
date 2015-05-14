package com.nanuvem.lom.business;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.kernel.dao.MySqlDaoFactory;

public class FacadeFactory {

	private Facade facade;

	public Facade createFacade() {
		if (this.facade == null) {
			this.facade = new BusinessFacade(new MySqlDaoFactory());
		}
		return this.facade;
	}
}
