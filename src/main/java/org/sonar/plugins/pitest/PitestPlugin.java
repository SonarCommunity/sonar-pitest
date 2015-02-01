/*
 * Sonar Pitest Plugin
 * Copyright (C) 2009 Alexandre Victoor,
 * dev@sonar.codehaus.org
 * Copyright (C) 2015 Gerald Muecke,
 * gerald@moskito.li
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
package org.sonar.plugins.pitest;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

/**
 * This class is the entry point for all PIT extensions
 */
@Properties({
        @Property(key = PitestPlugin.MODE_KEY,
                defaultValue = PitestPlugin.MODE_SKIP,
                name = "PIT activation mode",
                description = "Possible values:  empty (means skip) and 'reuseReport'",
                global = true,
                project = true),
        @Property(key = PitestPlugin.REPORT_DIRECTORY_KEY,
                defaultValue = PitestPlugin.REPORT_DIRECTORY_DEF,
                name = "Output directory for the PIT reports",
                description = "This property is needed when the 'reuseReport' mode is activated and the reports are not located in the default directory (i.e. target/pit-reports)",
                global = true,
                project = true)
})
public final class PitestPlugin extends SonarPlugin {

    public static final String MODE_KEY = "sonar.pitest.mode";

    public static final String MODE_SKIP = "skip";

    public static final String MODE_REUSE_REPORT = "reuseReport";

    public static final String REPORT_DIRECTORY_KEY = "sonar.pitest.reportsDirectory";

    public static final String REPORT_DIRECTORY_DEF = "target/pit-reports";

    // modification: changed signature and creation of List
    @SuppressWarnings("rawtypes")
    @Override
    public List getExtensions() {

        return Arrays.asList(ResultParser.class, ReportFinder.class, PitestRulesDefinition.class, PitestSensor.class,
                PitestMetrics.class, PitestDecorator.class, PitestCoverageDecorator.class, PitestDashboardWidget.class,
                PitSourceTab.class);
    }

}
