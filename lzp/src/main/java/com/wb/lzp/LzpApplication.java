package com.wb.lzp;

import com.wb.lzp.config.LzpHttpConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LzpApplication implements CommandLineRunner {

	@Autowired
	LzpHttpConfig lzpHttpConfig;

	public static void main(String[] args) {
		SpringApplication.run(LzpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		log.info(lzpHttpConfig.test());


	}
}
