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
package org.sonar.plugins.pitest.report;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.test.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ReportFinderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @InjectMocks
    private ReportFinder subject;

    @Test
    @Ignore
    public void testFindReport_existingReport() throws IOException {

        // prepare
        final Path xmlFile = TestUtils.getResource("mutations.xml").toPath();
        final Path directory = xmlFile.getParent();

        // act
        final Path report = subject.findReport(directory);

        // assert
        assertEquals(xmlFile, report);
    }

    @Test
    public void testFindReport_noReportInDirectory() throws IOException {

        final Path directory = TestUtils.getResource("fake_libs").toPath();

        // act
        final Path report = subject.findReport(directory);

        // assert
        assertThat(report).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindReport_nullPath_exception() throws IOException {

        subject.findReport(null);

    }

    @Test
    public void testIsNewer_newer_true() throws Exception {

        // prepare

        final Path older = folder.newFile().toPath();
        final Path newer = folder.newFile().toPath();
        Files.setLastModifiedTime(older, FileTime.fromMillis(1000L));
        Files.setLastModifiedTime(newer, FileTime.fromMillis(2000L));
        // act
        final boolean result = subject.isNewer(older, newer);

        // assert

        assertTrue(result);
    }

    @Test
    public void testIsNewer_older_false() throws Exception {

        // prepare

        final Path older = folder.newFile().toPath();
        final Path newer = folder.newFile().toPath();
        Files.setLastModifiedTime(older, FileTime.fromMillis(1000L));
        Files.setLastModifiedTime(newer, FileTime.fromMillis(2000L));
        // act
        final boolean result = subject.isNewer(newer, older);

        // assert

        assertFalse(result);
    }

    @Test
    public void testIsNewer_equals_false() throws Exception {

        // prepare
        final Path older = folder.newFile().toPath();
        Files.setLastModifiedTime(older, FileTime.fromMillis(1000L));

        // act
        final boolean result = subject.isNewer(older, older);

        // assert

        assertFalse(result);
    }

    @Test
    public void testFindMostRecentReport_notMatchingPattern() throws Exception {

        folder.newFile("someFile.txt").toPath();
        // act
        final Path report = subject.findMostRecentReport(folder.getRoot().toPath(), "*.xml");

        // assert
        assertNull(report);
    }

    @Test
    public void testFindMostRecentReport_matchingPatternOnce() throws Exception {

        // prepare
        final Path newFile = folder.newFile("someFile.xml").toPath();
        // act
        final Path report = subject.findMostRecentReport(folder.getRoot().toPath(), "*.xml");

        // assert
        assertNotNull(report);
        assertEquals(newFile, report);
    }

    @Test
    public void testFindMostRecentReport_matchingPatternNewerFile() throws Exception {

        // prepare
        final Path newFile1 = folder.newFile("someFile1.xml").toPath();
        final Path newFile2 = folder.newFile("someFile2.xml").toPath();
        Files.setLastModifiedTime(newFile1, FileTime.fromMillis(1000L));
        Files.setLastModifiedTime(newFile2, FileTime.fromMillis(2000L));

        // act
        final Path report = subject.findMostRecentReport(folder.getRoot().toPath(), "*.xml");

        // assert
        assertNotNull(report);
        assertEquals(newFile2, report);
    }

    @Test
    public void testFindMostRecentReport_matchingPatternNewerFile_reverseOrder() throws Exception {

        // prepare
        final Path newFile1 = folder.newFile("someFile2.xml").toPath();
        final Path newFile2 = folder.newFile("someFile1.xml").toPath();
        Files.setLastModifiedTime(newFile1, FileTime.fromMillis(1000L));
        Files.setLastModifiedTime(newFile2, FileTime.fromMillis(2000L));

        // act
        final Path report = subject.findMostRecentReport(folder.getRoot().toPath(), "*.xml");

        // assert
        assertNotNull(report);
        assertEquals(newFile2, report);
    }
}
