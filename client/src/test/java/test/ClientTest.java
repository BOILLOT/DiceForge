package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import client.Client;

public class ClientTest {

	Client c = Mockito.mock(Client.class);
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		c = new Client();
	}

	@Test
	public void runTest() {
		
	}

}
