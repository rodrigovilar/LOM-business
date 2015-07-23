package com.nanuvem.lom.business.entitytype;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.entitytype.CreateEntityTypeTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MySqlDaoFactory;

public class BusinessCreateEntityTypeTest extends CreateEntityTypeTest {

	@Override
	public Facade createFacade() {
		return new BusinessFacade(new MySqlDaoFactory());
	}

}
