package com.jinrongtong5.cmdline;

import java.io.IOException;

import com.beust.jcommander.Parameter;
import com.jinrongtong5.checker.SingleFileChecker;

public class FileCommand extends BaseCommand {

    @Parameter(names = {"--file", "-f"}, description = "File of inspection", required = true)
    public String file;

    @Override
    public void doCommand() {
        try {
            SingleFileChecker.singleFileCheck(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
