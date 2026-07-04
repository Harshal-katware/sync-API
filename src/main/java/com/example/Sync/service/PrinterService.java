package com.example.Sync.service;

import com.example.Sync.dto.PrintItemDto;
import com.example.Sync.dto.PrintRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class PrinterService {

    @Value("${printer.bill.name}")
    private String billPrinterName;

    @Value("${printer.kot.name}")
    private String kotPrinterName;

    // ── ESC/POS Commands ──────────────────────────────────────────────────────
    private static final byte[] INIT          = {0x1B, 0x40};
    private static final byte[] BOLD_ON       = {0x1B, 0x45, 0x01};
    private static final byte[] BOLD_OFF      = {0x1B, 0x45, 0x00};
    private static final byte[] ALIGN_LEFT    = {0x1B, 0x61, 0x00};
    private static final byte[] ALIGN_CENTER  = {0x1B, 0x61, 0x01};
    private static final byte[] FONT_LARGE    = {0x1D, 0x21, 0x11};
    private static final byte[] FONT_NORMAL   = {0x1D, 0x21, 0x00};
    private static final byte[] CUT_PAPER     = {0x1D, 0x56, 0x41, 0x03};

    // ── Find Printer by Name ──────────────────────────────────────────────────
    private PrintService findPrinter(String name) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(name)) {
                return service;
            }
        }
        return null;
    }

    // ── Send bytes to printer ─────────────────────────────────────────────────
    private void sendToPrinter(PrintService printer, byte[] data) throws Exception {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(data, flavor, null);
        PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
        DocPrintJob job = printer.createPrintJob();
        job.print(doc, attrs);
    }

    // ── Print Bill ────────────────────────────────────────────────────────────
    public void printBill(PrintRequest order) throws Exception {
        PrintService printer = findPrinter(billPrinterName);
        if (printer == null) {
            throw new Exception("Bill printer not found: " + billPrinterName);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(INIT);

        // Restaurant name
        bos.write(ALIGN_CENTER);
        bos.write(BOLD_ON);
        bos.write(FONT_LARGE);
        bos.write("PATIL DHABHA\n".getBytes("UTF-8"));
        bos.write(FONT_NORMAL);
        bos.write(BOLD_OFF);
        bos.write("Old PB Road NH-48, Opp. Aequs - Hattargi\n".getBytes("UTF-8"));
        bos.write("Ph: 9000000000\n".getBytes("UTF-8"));
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Table / Captain / Date
        bos.write(ALIGN_LEFT);
        bos.write(("Table   : " + (order.getTableName() != null ? order.getTableName() : "") + "\n").getBytes("UTF-8"));
        if (order.getCaptainName() != null && !order.getCaptainName().isEmpty()) {
            bos.write(("Captain : " + order.getCaptainName() + "\n").getBytes("UTF-8"));
        }
        bos.write(("Date    : " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n").getBytes("UTF-8"));
        bos.write(("Time    : " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n").getBytes("UTF-8"));
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Column header
        bos.write(BOLD_ON);
        bos.write(String.format("%-16s %4s %8s\n", "Item", "Qty", "Amount").getBytes("UTF-8"));
        bos.write(BOLD_OFF);
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Items
        for (PrintItemDto item : order.getItems()) {
            String name = item.getName() != null ? item.getName() : "";
            if (name.length() > 16) name = name.substring(0, 16);
            bos.write(String.format("%-16s %4d %8.2f\n",
                    name, item.getQty(), item.getPrice() * item.getQty()
            ).getBytes("UTF-8"));
        }

        // Totals
        bos.write("--------------------------------\n".getBytes("UTF-8"));
        bos.write(String.format("%-20s %10.2f\n", "Subtotal :", order.getSubtotal()).getBytes("UTF-8"));
        if (order.getDiscount() > 0) {
            bos.write(String.format("%-20s %10.2f\n", "Discount :", order.getDiscount()).getBytes("UTF-8"));
        }
        if (order.getGst() > 0) {
            bos.write(String.format("%-20s %10.2f\n", "GST (5%) :", order.getGst()).getBytes("UTF-8"));
        }
        bos.write("--------------------------------\n".getBytes("UTF-8"));
        bos.write(BOLD_ON);
        bos.write(String.format("%-20s %10.2f\n", "TOTAL    :", order.getTotal()).getBytes("UTF-8"));
        bos.write(BOLD_OFF);
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        if (order.getPaymentMode() != null && !order.getPaymentMode().isEmpty()) {
            bos.write(("Payment  : " + order.getPaymentMode() + "\n").getBytes("UTF-8"));
            bos.write("--------------------------------\n".getBytes("UTF-8"));
        }

        // Footer
        bos.write(ALIGN_CENTER);
        bos.write("Thank You! Visit Again!\n".getBytes("UTF-8"));
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Feed + Cut
        bos.write("\n\n\n".getBytes("UTF-8"));
        bos.write(CUT_PAPER);

        sendToPrinter(printer, bos.toByteArray());
    }

    // ── Print KOT ─────────────────────────────────────────────────────────────
    public void printKOT(PrintRequest order) throws Exception {
        PrintService printer = findPrinter(kotPrinterName);
        if (printer == null) {
            throw new Exception("KOT printer not found: " + kotPrinterName);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(INIT);

        // KOT Header
        bos.write(ALIGN_CENTER);
        bos.write(BOLD_ON);
        bos.write(FONT_LARGE);
        bos.write("PATIL DHABHA\n".getBytes("UTF-8"));
        bos.write(FONT_NORMAL);
        bos.write("*** KOT ***\n".getBytes("UTF-8"));
        bos.write(BOLD_OFF);
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Table / Captain / Time
        bos.write(ALIGN_LEFT);
        bos.write(BOLD_ON);
        bos.write(("Table   : " + (order.getTableName() != null ? order.getTableName() : "") + "\n").getBytes("UTF-8"));
        bos.write(BOLD_OFF);
        if (order.getCaptainName() != null && !order.getCaptainName().isEmpty()) {
            bos.write(("Captain : " + order.getCaptainName() + "\n").getBytes("UTF-8"));
        }
        bos.write(("Time    : " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n").getBytes("UTF-8"));
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Column header
        bos.write(BOLD_ON);
        bos.write(String.format("%-22s %6s\n", "Item", "Qty").getBytes("UTF-8"));
        bos.write(BOLD_OFF);
        bos.write("--------------------------------\n".getBytes("UTF-8"));

        // Items — NO prices on KOT
        for (PrintItemDto item : order.getItems()) {
            String name = item.getName() != null ? item.getName() : "";
            if (name.length() > 22) name = name.substring(0, 22);
            bos.write(String.format("%-22s %6d\n",
                    name, item.getQty()
            ).getBytes("UTF-8"));
        }

        bos.write("--------------------------------\n".getBytes("UTF-8"));
        bos.write(ALIGN_CENTER);
        bos.write("** KITCHEN COPY **\n".getBytes("UTF-8"));

        // Feed + Cut
        bos.write("\n\n\n".getBytes("UTF-8"));
        bos.write(CUT_PAPER);

        sendToPrinter(printer, bos.toByteArray());
    }
}