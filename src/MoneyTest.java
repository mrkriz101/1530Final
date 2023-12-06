package src;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;

public class MoneyTest {

    @Test
    public void testSavingsButtonPressed() {
        Money money = new Money(); // Instantiate your Money class
        money.frame = Mockito.mock(JFrame.class); // Mock the JFrame

        // Simulate a button click on the savings goals button
        money.savingsButton.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Verify that the cardLayout's show method was called with the expected arguments
        Mockito.verify(money.cardLayout).show(money.contentPanel, "Savings");

        // Verify that the content displayed is "Savings Content!"
        assertEquals("Savings Content!", ((JLabel) ((JPanel) money.contentPanel.getComponent(2)).getComponent(0)).getText());
    }
}
