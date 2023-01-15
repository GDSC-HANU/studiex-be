package com.gdsc.studiex.infrastructure.share.controllers;


import com.gdsc.studiex.StudiexApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class SystemController {
    @PostMapping("/system/update")
    public void updateSystem() throws IOException, InterruptedException {
        run("git pull", true);
        StudiexApplication.restart();
    }

    public void run(String command) throws IOException, InterruptedException {
        run(command, true);
    }

    public void run(String command, boolean wait) throws IOException, InterruptedException {
        final Process process = Runtime.getRuntime().exec(command);
        log.info("Start running command: " + command);
        if (wait) {
            process.waitFor();
            log.info("Finish running command: " + command);
        }
    }
}
