package com.noyss.printer.controller;

import com.noyss.printer.services.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/printerAll")
    public List<String> getNamePrinterAll(){
        printerService.printString("POS-80", "\ntest1\ntest2");
        return printerService.getPrinters();
    }

    @GetMapping("/cut")
    public String cutPaper(){
        // cut that paper!
        // Command	    ASCII	    Code (Hex)
        // Full Cut	    GS V 0	    0x1D 0x56 0x00
        // Partial Cut	GS V 1	    0x1D 0x56 0x01
        byte ESC = 0x1B;
        byte at = 0x40; // @
        byte GS = 0x1D;
        byte V = 0x56;
        byte n = 0x00 ; // Full Cut
        byte[] cutP = new byte[] { ESC, at, GS, V, n };
        printerService.printBytes("POS-80", cutP);
        return "CUT PAPER";
    }

    @GetMapping("/feed")
    public String feedPaper(){
        // ASCII: ESC d n
        // Code (Hex): 0x1B 0x64 n
        byte ESC = 0x1B;
        byte at = 0x40; // @
        byte d = 0x64;
        byte n = 0x05; // Print and Feed 5 Lines
        byte[] cutP = new byte[] { ESC, at, ESC, d, n };
        printerService.printBytes("POS-80", cutP);
        return "FEED PAPER";
    }
}
