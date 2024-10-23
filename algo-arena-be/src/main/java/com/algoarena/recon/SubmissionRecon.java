package com.algoarena.recon;

import com.algoarena.dao.repo.SubmissionRepo;
import com.algoarena.dao.repo.SubmissionsRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SubmissionRecon {

    private SubmissionRepo submissionRepo;
    private SubmissionsRepo submissionsRepo;

    public SubmissionRecon(SubmissionRepo submissionRepo, SubmissionsRepo submissionsRepo){
        this.submissionRepo = submissionRepo;
        this.submissionsRepo = submissionsRepo;
    }

    public void initiateReconProcess(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                try{
                    Pageable pageable = PageRequest.of(0, 20);
                    List<UUID> submissionIdsWithPendingStatus = submissionRepo.findIdByStatus("PENDING",pageable); // Get first 20 PENDING submissions

                    for(UUID submissionId : submissionIdsWithPendingStatus){
                        List<Integer> statusIdList = submissionsRepo.findStatusIdBySubmissionId(submissionId); // Get the list of status_ids for each test case

                        boolean isAcceptable = true;
                        boolean isRejectable = false;
                        for(int statusId : statusIdList){
                            if(statusId != 3){
                                isAcceptable = false;
                                if(statusId > 3)
                                    isRejectable = true;
                                break;
                            }
                        }

                        if(isAcceptable)
                            submissionRepo.updateStatus(submissionId,"ACCEPTED");
                        else if(isRejectable)
                            submissionRepo.updateStatus(submissionId,"REJECTED");
                    }

                    Thread.sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                    break;
                }

            }

        });
        executor.shutdown();  // Clean up resources

    }
}
