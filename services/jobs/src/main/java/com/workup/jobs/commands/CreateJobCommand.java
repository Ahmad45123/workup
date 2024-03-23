package com.workup.jobs.commands;

import com.workup.shared.commands.*;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;;

public class CreateJobCommand extends JobCommand<CreateJobRequest> {    

    @Override
    public void Run(CreateJobRequest request) {
        System.out.println(" [x] Received In Command: '" + request.getTitle() + "'");
    }
    
}
