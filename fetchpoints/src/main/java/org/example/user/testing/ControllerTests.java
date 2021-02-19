package org.example.user.testing;

import static org.junit.Assert.assertEquals;

import org.example.user.controller.TransactionController;
import org.junit.jupiter.api.Test;

public class ControllerTests {
	
	@Test
    public void controllerTestExample() {
        TransactionController transactionController = new TransactionController();
        String outcome = transactionController.basicTest();
        assertEquals(outcome, "For Controller Testing Exampl");
    }
}