package io.jenkins.plugins.coverage.model;

import java.util.Collections;

import org.junit.Test;

import hudson.model.FreeStyleProject;
import hudson.model.Result;

import io.jenkins.plugins.coverage.CoveragePublisher;
import io.jenkins.plugins.coverage.adapter.JacocoReportAdapter;
import io.jenkins.plugins.util.IntegrationTestWithJenkinsPerSuite;

/**
 * Tests if 0 reports fail vs ok.
 */
public class CoveragePlugin0vsOkITest extends IntegrationTestWithJenkinsPerSuite {
    private static final String JACOCO_FILE_NAME = "jacoco-analysis-model.xml";

    /**
     * Adapter reads no file and failNoReports is set true.
     */
    @Test
    public void noFileAndFailNoReportsTrue() {
        FreeStyleProject project = createFreeStyleProject();

        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter("");
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(true);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.FAILURE);
    }

    /**
     * Adapter reads no file and failNoReports is set false.
     */
    @Test
    public void noFileAndFailNoReportsFalse() {
        FreeStyleProject project = createFreeStyleProject();
        copyFilesToWorkspace(project, JACOCO_FILE_NAME);
        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter("");
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(false);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.SUCCESS);
    }

    /**
     * Adapter reads one file and failNoReports is set true.
     */
    @Test
    public void withFileAndFailNoReportsTrue() {
        FreeStyleProject project = createFreeStyleProject();
        copyFilesToWorkspace(project, JACOCO_FILE_NAME);
        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter(JACOCO_FILE_NAME);
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(true);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.SUCCESS);
    }

    /**
     * Adapter reads one file and failNoReports is set false.
     */
    @Test
    public void withFileAndFailNoReportsFalse() {
        FreeStyleProject project = createFreeStyleProject();
        copyFilesToWorkspace(project, JACOCO_FILE_NAME);
        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter(JACOCO_FILE_NAME);
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(false);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.SUCCESS);
    }

    /**
     * Adapter reads one file and failNoReports is set false.
     */
    @Test
    public void withFileWildcardAndFailNoReportsTrue() {
        FreeStyleProject project = createFreeStyleProject();
        copyFilesToWorkspace(project, JACOCO_FILE_NAME);
        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter("**/*.xml");
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(true);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.SUCCESS);
    }    /**
     * Adapter reads one file and failNoReports is set false.
     */
    @Test
    public void withFileWildcardAndFailNoReportsFalse() {
        FreeStyleProject project = createFreeStyleProject();
        copyFilesToWorkspace(project, JACOCO_FILE_NAME);
        CoveragePublisher coveragePublisher = new CoveragePublisher();
        JacocoReportAdapter jacocoReportAdapter = new JacocoReportAdapter("**/*.xml");
        coveragePublisher.setAdapters(Collections.singletonList(jacocoReportAdapter));
        coveragePublisher.setFailNoReports(false);
        project.getPublishersList().add(coveragePublisher);

        buildWithResult(project, Result.SUCCESS);
    }
}
