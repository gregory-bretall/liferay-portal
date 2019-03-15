/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v7_0_5;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void deleteOrphanExpandoRow() throws Exception {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if (dbType == DBType.POSTGRESQL) {
			runSQL(
				"delete from ExpandoRow er where not exists (select null " +
					"from ExpandoValue ev where ev.rowId_ = er.rowId_)");
		}
		else {
			runSQL(
				"delete from ExpandoRow where rowId_ not in (select rowId_ " +
					"from ExpandoValue)");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanExpandoRow();
	}

}