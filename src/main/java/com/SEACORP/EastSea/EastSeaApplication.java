package com.SEACORP.EastSea;

import com.SEACORP.EastSea.Auth.ERole;
import com.SEACORP.EastSea.Repositories.RoleRepository;
import com.SEACORP.EastSea.Repositories.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

@SpringBootApplication
@EnableBatchProcessing
public class EastSeaApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${spring.datasource.driver-class-name}")
	public String myDriver;

	@Value("${spring.datasource.url}")
	public String myUrl;

	@Value("${spring.datasource.username}")
	public String username;

	@Value("${spring.datasource.password}")
	public String password;

	public static void main(String[] args) {
		SpringApplication.run(EastSeaApplication.class, args);
	}

	public void run(String... args) throws Exception{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("date", UUID.randomUUID().toString())
				.addLong("JobId",System.currentTimeMillis())
				.addLong("time",System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);
		System.out.println("STATUS :: " +execution.getStatus());
	}

	@Bean
	public void loadENums() {
		int roleCheck = roleRepository.isRoleEmpty();

		if (roleCheck < ERole.values().length) {
			int id = 1;
			for (ERole role : ERole.values()) {
				if (roleRepository.findByName(role).isEmpty()) {
					try {
						Connection conn = DriverManager.getConnection(myUrl, username, password);
						Class.forName(myDriver);
						String query = "Insert into role (id, name) values (?,?)";
						PreparedStatement statement = conn.prepareStatement(query);

						statement.setString(1, Integer.toString(id));
						statement.setString(2, role.toString());

						statement.executeUpdate();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				id++;
			}
		}
	}

}
