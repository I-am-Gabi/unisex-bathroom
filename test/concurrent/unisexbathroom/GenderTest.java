package concurrent.unisexbathroom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GenderTest {

	@Test
	void testGetGender() { 
		Gender g = Gender.getGender();
		assertTrue(g == Gender.Men || g == Gender.Woman); 
	} 
}
