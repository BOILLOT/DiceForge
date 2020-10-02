package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import event.ChoiceFace;
import face.Face;
import face.HybridFace;
import resources.TypeResource;

class HandlerChoiceFaceTest {
		
	/** Create the hybride face */
	HybridFace hf;
	
	/** Create the ChoiceFace */
	ChoiceFace chF;
	
	/** Create the list of the face of the hybride face */
	List<Face> listF;
	
	/**
	 * Set up the variables
	 */
	@BeforeEach
	public void setUp() {
		listF = new ArrayList<>();
		listF.add(new Face(5, TypeResource.GOLD,2));
		listF.add(new Face(5, TypeResource.GLORY,2));
		this.chF = new ChoiceFace();
		this.hf = new HybridFace(5, true, listF);
		this.chF.setFace(this.hf);
	}
	
	/**
	 * Test if the choice face contains the good hybrid face
	 */
	@Test
	void test() {
		assertEquals(this.chF.getFace(), this.hf);
	}

}
