package com.workup.controller;

import asg.cliche.CLIException;
import asg.cliche.Command;
import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.pojo.*;
import com.workup.shared.commands.controller.*;
import com.workup.shared.enums.ControllerQueueNames;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javassist.*;
import org.apache.logging.log4j.Level;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;
import org.springframework.core.env.Environment;
import com.myjeeva.digitalocean.pojo.Droplet;

public class CLIHandler {
    @Autowired
    AmqpTemplate rabbitTemplate;
    @Autowired
    Environment environment;
    private static final Map<String, String> appQueueMap = new HashMap<>();

    static {
        appQueueMap.put("jobs", ControllerQueueNames.JOBS);
        appQueueMap.put("users", ControllerQueueNames.USERS);
        appQueueMap.put("payments", ControllerQueueNames.PAYMENTS);
        appQueueMap.put("contracts", ControllerQueueNames.CONTRACTS);
    }

    @Command(description = "Set the maximum number of threads for a specific app")
    public String maxThreads(String app, int maxThreads) throws CLIException {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        rabbitTemplate.convertAndSend(
                appQueueMap.get(app),
                "",
                SetMaxThreadsRequest.builder().withMaxThreads(maxThreads).build());
        return "Command sent";
    }

    @Command(description = "Set the maximum number of DB connections for a specific app")
    public String maxdb(String app, int maxDBConn) {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        if (maxDBConn > 100 || maxDBConn < 1) {
            return "Error: Max threads must have a value between 1 and 100";
        }
        rabbitTemplate.convertAndSend(
                appQueueMap.get(app),
                "",
                SetMaxDBConnectionsRequest.builder().withMaxDBConnections(maxDBConn).build());
        return "Command Sent!";
    }

    @Command(description = "Adds a new command")
    public String addCommand(String app, String commandName, String className) throws Exception {
        return updateCommand(app, commandName, className);
    }

    @Command(description = "starts a specific app")
    public String start(String app) {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }

        rabbitTemplate.convertAndSend(appQueueMap.get(app), "", ContinueRequest.builder().build());
        return "Command sent";
    }

    @Command(description = "stops a specific app")
    public String freeze(String app) {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        rabbitTemplate.convertAndSend(appQueueMap.get(app), "", FreezeRequest.builder().build());
        return "Command sent";
    }

    @Command(description = "sets a logging level")
    public String setLoggingLevel(String app, String level) {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        // To throw an error in case an invalid level is provided :)
        Level.valueOf(level);
        rabbitTemplate.convertAndSend(
                appQueueMap.get(app), "", SetLoggingLevelRequest.builder().withLevel(level).build());
        return "Command sent!!";
    }

    @Command(description = "Updates an existing command")
    public String updateCommand(String app, String commandName, String className) throws Exception {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        try {
            byte[] byteArray = getByteCode(commandName, className);
            rabbitTemplate.convertAndSend(
                    appQueueMap.get(app),
                    "",
                    UpdateCommandRequest.builder()
                            .withCommandName(commandName)
                            .withClassName(className)
                            .withByteCode(byteArray)
                            .build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Command sent!!";
    }

    private byte[] getByteCode(String commandName, String className)
            throws InstantiationException,
            IllegalAccessException,
            NotFoundException,
            IOException,
            CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(ControllerApplication.class));
        CtClass ctClass = pool.get(className);
        // That's the compiled class byte code
        return ctClass.toBytecode();
    }

    @Command(description = "Deletes an existing command")
    public String deleteCommand(String app, String commandName) {
        app = app.toLowerCase();
        if (!appQueueMap.containsKey(app)) {
            return "Error: app can only be jobs, users, contracts or payments!";
        }
        rabbitTemplate.convertAndSend(
                appQueueMap.get(app), "", DeleteCommandRequest.builder().commandName(commandName).build());
        return "Command sent";
    }

    private DigitalOceanClient setupClient() {
        String apiToken = environment.getProperty("digitalocean.api.token");

        DigitalOceanClient digitalOceanClient = new DigitalOceanClient(apiToken);

        return digitalOceanClient;
    }

    @Command(description = "Spawns a new droplet instance, and sets it to join the swarm with the token and the ip")
    public String spawnMachine(String dropletName, String swarmToken, String swarmIP) {
        DigitalOceanClient client = setupClient();

        try {
            Droplet newDroplet = new Droplet();
            newDroplet.setName(dropletName);
            newDroplet.setSize("s-4vcpu-8gb-240gb-intel");
            newDroplet.setImage(new Image("ubuntu-24-04-x64")); // Replace with actual image ID
            newDroplet.setRegion(new Region("lon1")); // Replace with actual region slug
            newDroplet.setInstallMonitoring(true);

            String gistURL = "https://shorturl.at/ZEU34";

            newDroplet.setUserData(
                    "#!/bin/bash\nwget -O /tmp/setupmachine.sh " + gistURL + " && cd /tmp && chmod +x setupmachine.sh && ./setupmachine.sh " + swarmToken + " " + swarmIP + " && touch /finished.txt ");

            Droplet createdDroplet = client.createDroplet(newDroplet);

            return "Created Droplet" + createdDroplet;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: Failed to create droplet";
        }


    }

    @Command(description = "Lists existing droplet instances")
    public String listMachine() {

        String apiToken = environment.getProperty("digitalocean.api.token");

        DigitalOceanClient digitalOceanClient = new DigitalOceanClient(apiToken);


        try {
            Account account = digitalOceanClient.getAccountInfo();

            Droplets droplets = digitalOceanClient.getAvailableDroplets(1, 10);
            for (Droplet droplet : droplets.getDroplets()) {
                System.out.println("Droplet: " + droplet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Listed Successfully";
    }

}
