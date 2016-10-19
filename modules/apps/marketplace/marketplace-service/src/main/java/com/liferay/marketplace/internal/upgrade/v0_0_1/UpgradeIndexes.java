package com.liferay.marketplace.internal.upgrade.v0_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * Created by Admin on 10/18/2016.
 */
public class UpgradeIndexes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateIndexesSQL(_createMarketplaceModuleIndexes());
	}

	protected void updateIndexesSQL(String[] indexes) throws Exception {
		for(String template: indexes) {
			runSQLTemplateString(template, false, false);
		}
	}

	private String[] _createMarketplaceModuleIndexes() {
		String[] indexes = {"create index IX_5848F52D on Marketplace_Module (appId, bundleSymbolicName[$COLUMN_LENGTH:500$], bundleVersion[$COLUMN_LENGTH:75$]);",
							"create index IX_DD03D499 on Marketplace_Module (bundleSymbolicName[$COLUMN_LENGTH:500$]);"};
		return indexes;
	}
}
