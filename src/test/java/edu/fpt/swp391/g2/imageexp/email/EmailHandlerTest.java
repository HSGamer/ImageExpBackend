package edu.fpt.swp391.g2.imageexp.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailHandlerTest {
    @Test
    void testPropertiesFileNotNull() {
        Assertions.assertNotNull(EmailHandler.class.getResourceAsStream("/email-host.properties"));
    }
}