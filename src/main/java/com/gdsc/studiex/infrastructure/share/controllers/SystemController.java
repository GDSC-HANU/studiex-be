package com.gdsc.studiex.infrastructure.share.controllers;


import com.gdsc.studiex.StudiexApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            watch(process);
            process.waitFor();
            log.info("Finish running command: " + command);
        }
    }

    private static void watch(final Process process) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
