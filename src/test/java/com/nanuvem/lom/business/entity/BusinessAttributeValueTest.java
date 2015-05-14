package com.nanuvem.lom.business.entity;

import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.tests.property.AttributeValueTest;
import com.nanuvem.lom.business.BusinessFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class BusinessAttributeValueTest extends AttributeValueTest {

	@Override
	public Facade createFacade() {
//		return new BusinessFacade(new MySqlDaoFactory());
		return new BusinessFacade(new MemoryDaoFactory());
	}

}
