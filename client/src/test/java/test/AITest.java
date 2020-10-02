package test;



import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import client.AI;
import event.ChoiceExploit;
import event.ChoiceFace;
import event.ChoiceForge;
import face.CompoundFace;
import face.Face;
import face.HybridFace;
import face.SimpleFace;
import resources.TypeResource;
import card.*;

public class AITest {

	AI aiManager = Mockito.mock(AI.class, Mockito.CALLS_REAL_METHODS);
	Card aCard = Mockito.mock(Card.class);
	ChoiceForge chF = Mockito.mock(ChoiceForge.class);
	ChoiceExploit chE = Mockito.mock(ChoiceExploit.class);
	Face sFace = Mockito.mock(Face.class);
	HybridFace hFace = Mockito.mock(HybridFace.class);
	ChoiceFace chFa = Mockito.mock(ChoiceFace.class);
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aCard = new Card(Deck.BEAR,Deck.BEAR.getName(), Deck.BEAR.getGlory(),Deck.BEAR.getiE(),Deck.BEAR.getCosts());
		chE = new ChoiceExploit();
		chF = new ChoiceForge();
		chFa = new ChoiceFace();
		hFace = new HybridFace(CompoundFace.G1_OR_S1_OR_L1.getPrice(), CompoundFace.G1_OR_S1_OR_L1.isSelectable(), CompoundFace.G1_OR_S1_OR_L1.getListFace());

	}
	
	@Test
	public void cantBuyCardTest() {
		EnumMap<TypeResource, Integer> listResourcesPlayer = new EnumMap<TypeResource, Integer>(TypeResource.class);
		listResourcesPlayer.put(TypeResource.LUNAR, 3);
		chE.setListResourcesPlayer(listResourcesPlayer);
		assertFalse(aiManager.cantBuyCard(aCard, chE));
		listResourcesPlayer.put(TypeResource.LUNAR, 1);
		chE.setListResourcesPlayer(listResourcesPlayer);
		assertEquals(true, aiManager.cantBuyCard(aCard, chE));
	}
	@Test
	public void cantBuyFaceTest() {
		// Simple Face
		sFace = new Face(SimpleFace.GLORY1.getValue(), SimpleFace.GLORY1.getType(), SimpleFace.GLORY1.getPrice());
		chF.setGoldAmount(5);
		assertFalse(aiManager.cantBuyFace(sFace, chF));
		chF.setGoldAmount(1);
		assertTrue(aiManager.cantBuyFace(sFace, chF));
		
		// Hybrid Face
		chF.setGoldAmount(10);
		assertFalse(aiManager.cantBuyFace(hFace, chF));

		}
	@Test
	public void getRandomBetweenMinMaxTest() {
		System.out.println(aiManager.getRandomBetweenMinMax(2,10));
		when(aiManager.getRandomBetweenMinMax(2,10)).thenReturn(7);
		
		assertEquals(7, aiManager.getRandomBetweenMinMax(2,10));
	}
}
