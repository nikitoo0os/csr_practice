package com.vyatsu.practiceCSR;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class PracticeCsrApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(PracticeCsrApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}
}
