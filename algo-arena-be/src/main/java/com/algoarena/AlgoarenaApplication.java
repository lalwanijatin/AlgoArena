package com.algoarena;

import com.algoarena.api.Problem;
import com.algoarena.boilerplate.CodeGenerator;
import com.algoarena.problems.ProblemWithStatus;
import com.algoarena.recon.SubmissionRecon;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AlgoarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgoarenaApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(SubmissionRecon submissionRecon, Problem problem, CodeGenerator codeGenerator){
		return (args) -> {
			// Start Submission Recon in a separate Thread
			submissionRecon.initiateReconProcess();

			// Generate Boilerplate code for all the problems present in problem table
			List<ProblemWithStatus> problemList = problem.getProblemWithStatus();
			for(ProblemWithStatus problemWithStatus : problemList)
				codeGenerator.generate(problemWithStatus.getName());
		};
	}

}
