package com.xm.recommendation;

import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Application context tests")
@Epic("Application context")
@Tag("context")
class ApplicationTests {

	@Test
	@SuppressWarnings("squid:S2699")
	void contextLoads() {
	}
}
