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

package com.liferay.social.activities.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.social.activities.taglib.internal.servlet.ServletContextUtil;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.util.SocialActivityDescriptor;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Raymond Augé
 */
public class SocialActivitiesTag extends IncludeTag {

	public static String replaceUserTags(
			String content, ServiceContext serviceContext)
		throws PortalException {

		Matcher matcher = _userTagPattern.matcher(content);

		while (matcher.find()) {
			String result = matcher.group();

			try {
				StringBuilder sb = new StringBuilder(5);

				sb.append("<a href=\"");

				String assetTagScreenName = result.replace(
					"[@", StringPool.BLANK);

				assetTagScreenName = assetTagScreenName.replace(
					"]", StringPool.BLANK);

				ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

				User assetTagUser = UserLocalServiceUtil.getUserByScreenName(
					themeDisplay.getCompanyId(), assetTagScreenName);

				sb.append(assetTagUser.getDisplayURL(themeDisplay));

				sb.append("\">");

				String assetTagUserName = PortalUtil.getUserName(
					assetTagUser.getUserId(), assetTagScreenName);

				sb.append(assetTagUserName);

				sb.append("</a>");

				String userLink = sb.toString();

				content = StringUtil.replace(content, result, userLink);
			}
			catch (NoSuchUserException nsue) {
				if (_log.isDebugEnabled()) {
					_log.debug(nsue, nsue);
				}
			}
		}

		return content;
	}

	public void setActivities(List<SocialActivity> activities) {
		List<SocialActivityDescriptor> activityDescriptors = new ArrayList<>(
			activities.size());

		for (SocialActivity activity : activities) {
			activityDescriptors.add(new SocialActivityDescriptor(activity));
		}

		_activityDescriptors = activityDescriptors;
	}

	public void setActivitySets(List<SocialActivitySet> activitySets) {
		List<SocialActivityDescriptor> activityDescriptors = new ArrayList<>(
			activitySets.size());

		for (SocialActivitySet activitySet : activitySets) {
			activityDescriptors.add(new SocialActivityDescriptor(activitySet));
		}

		_activityDescriptors = activityDescriptors;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDisplayRSSFeed(boolean displayRSSFeed) {
		_displayRSSFeed = displayRSSFeed;
	}

	public void setFeedDelta(int feedDelta) {
		_feedDelta = feedDelta;
	}

	public void setFeedDisplayStyle(String feedDisplayStyle) {
		_feedDisplayStyle = feedDisplayStyle;
	}

	public void setFeedEnabled(boolean feedEnabled) {
		_feedEnabled = feedEnabled;
	}

	public void setFeedResourceURL(ResourceURL feedResourceURL) {
		_feedResourceURL = feedResourceURL;
	}

	public void setFeedTitle(String feedTitle) {
		_feedTitle = feedTitle;
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public void setFeedURL(String feedURL) {
		_feedURL = feedURL;
	}

	public void setFeedURLMessage(String feedURLMessage) {
		_feedURLMessage = feedURLMessage;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_activityDescriptors = null;
		_className = StringPool.BLANK;
		_classPK = 0;
		_displayRSSFeed = false;
		_feedDelta = 0;
		_feedDisplayStyle = null;
		_feedEnabled = false;
		_feedResourceURL = null;
		_feedTitle = null;
		_feedType = null;
		_feedURL = StringPool.BLANK;
		_feedURLMessage = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-social-activities:social-activities:activityDescriptors",
			_activityDescriptors);
		request.setAttribute(
			"liferay-social-activities:social-activities:className",
			_className);
		request.setAttribute(
			"liferay-social-activities:social-activities:classPK",
			String.valueOf(_classPK));
		request.setAttribute(
			"liferay-social-activities:social-activities:displayRSSFeed",
			String.valueOf(_displayRSSFeed));
		request.setAttribute(
			"liferay-social-activities:social-activities:feedDelta",
			String.valueOf(_feedDelta));
		request.setAttribute(
			"liferay-social-activities:social-activities:feedDisplayStyle",
			_feedDisplayStyle);
		request.setAttribute(
			"liferay-social-activities:social-activities:feedEnabled",
			String.valueOf(_feedEnabled));
		request.setAttribute(
			"liferay-social-activities:social-activities:feedResourceURL",
			_feedResourceURL);
		request.setAttribute(
			"liferay-social-activities:social-activities:feedTitle",
			_feedTitle);
		request.setAttribute(
			"liferay-social-activities:social-activities:feedType", _feedType);
		request.setAttribute(
			"liferay-social-activities:social-activities:feedURL", _feedURL);
		request.setAttribute(
			"liferay-social-activities:social-activities:feedURLMessage",
			_feedURLMessage);
	}

	private static final String _PAGE = "/social_activities/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivitiesTag.class);

	private static final Pattern _userTagPattern = Pattern.compile(
		"\\[\\@\\S*\\]");

	private List<SocialActivityDescriptor> _activityDescriptors;
	private String _className = StringPool.BLANK;
	private long _classPK;
	private boolean _displayRSSFeed;
	private int _feedDelta;
	private String _feedDisplayStyle;
	private boolean _feedEnabled;
	private ResourceURL _feedResourceURL;
	private String _feedTitle;
	private String _feedType;
	private String _feedURL = StringPool.BLANK;
	private String _feedURLMessage = StringPool.BLANK;

}