package com.libcentro.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.libcentro.demo.controller.Main;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

		Main.main(null);
	}

}
