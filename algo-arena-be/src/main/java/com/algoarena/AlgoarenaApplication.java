package com.algoarena;

import com.algoarena.boilerplate.CodeGenerator;
import com.algoarena.boilerplate.JavaCodeGenerator;
import com.algoarena.boilerplate.JavaScriptCodeGenerator;
import com.algoarena.boilerplate.ProblemStructureReader;
import com.algoarena.recon.SubmissionRecon;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlgoarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgoarenaApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(JavaCodeGenerator javaCodeGenerator, CodeGenerator codeGenerator, SubmissionRecon submissionRecon, JavaScriptCodeGenerator javaScriptCodeGenerator, ProblemStructureReader problemStructureReader){
		return (args) -> {
			submissionRecon.initiateReconProcess();
		};
	}

}
