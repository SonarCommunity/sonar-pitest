/*
 * Sonar Pitest Plugin
 * Copyright (C) 2015 SonarCommunity
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.pitest.ui;

import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_COVERAGE_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_DETECTED_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_KILLED_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_MEMORY_ERROR_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_NO_COVERAGE_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_SURVIVED_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_TIMED_OUT_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_TOTAL_KEY;
import static org.sonar.plugins.pitest.metrics.PitestMetricsKeys.MUTATIONS_UNKNOWN_KEY;

import org.sonar.api.resources.Qualifiers;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.DefaultTab;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.ResourceQualifier;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

@NavigationSection(NavigationSection.RESOURCE)
@ResourceQualifier({
    Qualifiers.FILE
})
@DefaultTab(metrics = {
        MUTATIONS_TOTAL_KEY,
        MUTATIONS_COVERAGE_KEY,
        MUTATIONS_DETECTED_KEY,
        MUTATIONS_KILLED_KEY,
        MUTATIONS_MEMORY_ERROR_KEY,
        MUTATIONS_NO_COVERAGE_KEY,
        MUTATIONS_SURVIVED_KEY,
        MUTATIONS_TIMED_OUT_KEY,
        MUTATIONS_UNKNOWN_KEY
})
@UserRole(UserRole.CODEVIEWER)
public class PitSourceTab extends AbstractRubyTemplate implements RubyRailsPage {

    @Override
    protected String getTemplatePath() {

        return "/org/sonar/plugins/pitest/pitest_source_tab.rb";
    }

    @Override
    public String getId() {

        return "Mutations Coverage";
    }

    @Override
    public String getTitle() {

        return "Mutations Coverage";
    }

}
