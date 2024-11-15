package sistema.colegio.eduxsystem.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportGeneratorService {

    public byte[] generarReport(String reportName) throws JRException {
        // Cargar el reporte
        InputStream reportStream = this.getClass().getResourceAsStream("/reports/" + reportName + ".jasper");

        Map<String, Object> parms = null;
        // Llenado
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parms, new JREmptyDataSource());

        // exporta a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
