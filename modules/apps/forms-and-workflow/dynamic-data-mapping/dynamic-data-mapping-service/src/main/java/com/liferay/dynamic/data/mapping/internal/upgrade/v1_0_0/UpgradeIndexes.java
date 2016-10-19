package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * Created by Gregory Bretall on 10/18/2016.
 */
public class UpgradeIndexes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateIndexesSQL(_createDDMDataProviderInstanceIndexes());
		updateIndexesSQL(_createDDMDataProviderInstanceLinkIndexes());
		updateIndexesSQL(_createDDMStorageLinkIndexes());
		updateIndexesSQL(_createDDMStructureLayoutIndexes());
		updateIndexesSQL(_createDDMStructureLinkIndexes());
		updateIndexesSQL(_createDDMStructureVersionIndexes());
		updateIndexesSQL(_createDDMTemplateLinkIndexes());
		updateIndexesSQL(_createDDMTemplateVersionIndexes());
	}

	protected void updateIndexesSQL(String[] indexes) throws Exception {
		for(String template: indexes) {
			runSQLTemplateString(template, false, false);
		}
	}

	private String[] _createDDMDataProviderInstanceIndexes() {
		String[] indexes = {"create index IX_DB54A6E5 on DDMDataProviderInstance (companyId);",
							"create index IX_1333A2A7 on DDMDataProviderInstance (groupId);",
							"create index IX_C903C097 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], companyId);",
							"create unique index IX_B4E180D9 on DDMDataProviderInstance (uuid_[$COLUMN_LENGTH:75$], groupId);"};
		return indexes;
	}

	private String[] _createDDMDataProviderInstanceLinkIndexes() {
		String[] indexes = {"create unique index IX_8C878342 on DDMDataProviderInstanceLink (dataProviderInstanceId, structureId);",
							"create index IX_CB823541 on DDMDataProviderInstanceLink (structureId);"};
		return indexes;
	}

	private String[] _createDDMStorageLinkIndexes() {
		String[] indexes = {"create index IX_DB81EB42 on DDMStorageLink (uuid_[$COLUMN_LENGTH:75$], companyId);"};
		return indexes;
	}

	private String[] _createDDMStructureLayoutIndexes() {
		String[] indexes = {"create unique index IX_B7158C0A on DDMStructureLayout (structureVersionId);",
							"create index IX_A90FF72A on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], companyId);",
							"create unique index IX_C9A0402C on DDMStructureLayout (uuid_[$COLUMN_LENGTH:75$], groupId);"};
		return indexes;
	}

	private String[] _createDDMStructureLinkIndexes() {
		String[] indexes = {"create unique index IX_E43143A3 on DDMStructureLink (classNameId, classPK, structureId);"};
		return indexes;
	}

	private String[] _createDDMStructureVersionIndexes() {
		String[] indexes = {"create index IX_17B3C96C on DDMStructureVersion (structureId, status);",
							"create unique index IX_64C3C42 on DDMStructureVersion (structureId, version[$COLUMN_LENGTH:75$]);"};
		return indexes;
	}

	private String[] _createDDMTemplateLinkIndexes() {
		String[] indexes = {"create unique index IX_6F3B3E9C on DDMTemplateLink (classNameId, classPK);",
							"create index IX_85278170 on DDMTemplateLink (templateId);"};
		return indexes;
	}

	private String[] _createDDMTemplateVersionIndexes() {
		String[] indexes = {"create index IX_66382FC6 on DDMTemplateVersion (templateId, status);",
							"create unique index IX_8854A128 on DDMTemplateVersion (templateId, version[$COLUMN_LENGTH:75$]);"};
		return indexes;
	}

}
