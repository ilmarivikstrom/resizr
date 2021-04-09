package org.tudelft.cloud.resizr.service.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tudelft.cloud.resizr.model.monitoring.StatusReport;
import org.tudelft.cloud.resizr.service.monitoring.StatusMonitor;

@Component
public class Brain {

    private final StatusMonitor statusMonitor;

    private final ReportProcessor reportProcessor;

    @Autowired
    public Brain(StatusMonitor statusMonitor, ReportProcessor reportProcessor) {
        this.statusMonitor = statusMonitor;
        this.reportProcessor = reportProcessor;
    }


    @Scheduled(cron = "${resizr.status.report.interval}")
    public void fetchStatusReport() {
        StatusReport statusReport = statusMonitor.generateStatusReport();
        reportProcessor.process(statusReport);
    }

}
