/*
 * Sonar Pitest Plugin
 * Copyright (C) 2009 Alexandre Victoor
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
package org.sonar.plugins.pitest;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

/**
 * Computer for calculating the mutation coverage of a component based on the detected vs total mutations.
 *
 * @author <a href="mailto:gerald.muecke@devcon5.io">Gerald M&uuml;cke</a>
 */
public class PitestCoverageComputer implements MeasureComputer {

    public MeasureComputerDefinition define(final MeasureComputerDefinitionContext defContext) {

        return defContext.newDefinitionBuilder()
                         .setInputMetrics(PitestMetricsKeys.MUTATIONS_DETECTED_KEY, PitestMetricsKeys.MUTATIONS_TOTAL_KEY)
                         .setOutputMetrics(PitestMetricsKeys.MUTATIONS_COVERAGE_KEY)
                         .build();
    }

    public void compute(final MeasureComputerContext context) {
        final Measure mutationsTotal = context.getMeasure(PitestMetricsKeys.MUTATIONS_TOTAL_KEY);
        if(mutationsTotal != null)  {
            final Integer elements = mutationsTotal.getIntValue();
            if (elements > 0) {
                final Integer coveredElements = context.getMeasure(PitestMetricsKeys.MUTATIONS_DETECTED_KEY).getIntValue();
                final Double coverage = 100.0 * coveredElements / elements;
                context.addMeasure(PitestMetricsKeys.MUTATIONS_COVERAGE_KEY, coverage);
            }
        }
    }
}
