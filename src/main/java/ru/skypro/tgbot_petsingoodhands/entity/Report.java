package ru.skypro.tgbot_petsingoodhands.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "report_id")
    private Long reportId;
    @ManyToOne
    @JoinColumn (name = "clientId")
    private Client client;
    private LocalDateTime dataTimeReport;
    @Lob
    private byte[] photo;
    @Lob
    private byte[] report;

    public Long getReportId() {
        return reportId;
    }
    private boolean notificationAboutQualityReport;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDataTimeReport() {
        return dataTimeReport;
    }

    public void setDataTimeReport(LocalDateTime dataTimeReport) {
        this.dataTimeReport = dataTimeReport;
    }
}
