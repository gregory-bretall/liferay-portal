package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * Created by Admin on 10/18/2016.
 */
public class UpgradeIndexes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateIndexesSQL(_createWikipageResourceIndexes());
	}

	protected void updateIndexesSQL(String[] indexes) throws Exception {
		for(String template: indexes) {
			runSQLTemplateString(template, false, false);
		}
	}

	private String[] _createWikipageResourceIndexes() {
		String[] indexes = {"create index IX_13319367 on WikiPageResource (uuid_[$COLUMN_LENGTH:75$], companyId);",
							"create unique index IX_F705C7A9 on WikiPageResource (uuid_[$COLUMN_LENGTH:75$], groupId);"};
		return indexes;
	}
}
