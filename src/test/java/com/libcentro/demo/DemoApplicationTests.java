package com.libcentro.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.libcentro.demo.controller.main;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

		main.main(null);
	}

}
