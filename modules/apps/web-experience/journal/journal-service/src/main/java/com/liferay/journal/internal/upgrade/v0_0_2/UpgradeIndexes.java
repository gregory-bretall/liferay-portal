package com.liferay.journal.internal.upgrade.v0_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * Created by Admin on 10/18/2016.
 */
public class UpgradeIndexes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateIndexesSQL(_createJournalArticleIndexes());
		updateIndexesSQL(_createJournalArticleResourceIndexes());
	}

	protected void updateIndexesSQL(String[] indexes) throws Exception{
		for(String template: indexes) {
			runSQLTemplateString(template, false, false);
		}
	}

	private String[] _createJournalArticleIndexes() {
		String[] indexes = {"create index IX_17806804 on JournalArticle (DDMStructureKey[$COLUMN_LENGTH:75$]);",
							"create index IX_75CCA4D1 on JournalArticle (DDMTemplateKey[$COLUMN_LENGTH:75$]);",
							"create index IX_C761B675 on JournalArticle (classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$]);",
							"create index IX_D8EB0D84 on JournalArticle (groupId, DDMStructureKey[$COLUMN_LENGTH:75$]);",
							"create index IX_31B74F51 on JournalArticle (groupId, DDMTemplateKey[$COLUMN_LENGTH:75$]);",
							"create index IX_353BD560 on JournalArticle (groupId, classNameId, DDMStructureKey[$COLUMN_LENGTH:75$]);",
							"create index IX_6E801BF5 on JournalArticle (groupId, classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$]);"};
		return indexes;
	}

	private String[] _createJournalArticleResourceIndexes() {
		String[] indexes = {"create index IX_CC7576C7 on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], companyId);"};
		return indexes;
	}

}
