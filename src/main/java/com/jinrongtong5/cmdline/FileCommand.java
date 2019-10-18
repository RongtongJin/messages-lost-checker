package com.jinrongtong5.cmdline;

import java.io.IOException;
import java.util.Objects;

import com.beust.jcommander.Parameter;
import com.jinrongtong5.checker.FileChecker;

public class FileCommand extends BaseCommand {

    @Parameter(names = {"--file", "-f"}, description = "File of inspection", required = true)
    public String file;

    @Override
    public void doCommand() {
        Objects.requireNonNull(file);
        try {
            FileChecker.singleFileCheck(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
