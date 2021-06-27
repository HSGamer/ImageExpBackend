package edu.fpt.swp391.g2.imageexp.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VerifyEmailHandlerTest {
    @Test
    void testPropertiesFileNotNull() {
        Assertions.assertNotNull(VerifyEmailHandler.class.getResourceAsStream("/email-host.properties"));
    }
}