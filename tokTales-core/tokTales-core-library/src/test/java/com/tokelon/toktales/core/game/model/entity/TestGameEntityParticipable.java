package com.tokelon.toktales.core.game.model.entity;

import org.junit.Test;
import org.mockito.Mockito;

import com.tokelon.toktales.core.game.model.entity.IGameEntity.IGameEntityObserver;
import com.tokelon.toktales.core.game.model.entity.IGameEntity.IGameEntityParticipant;

public class TestGameEntityParticipable {

	
	@Test
	public void GameEntity_IGameEntityParticipant_ShouldReceiveGenericChanges() {
		IGameEntityParticipant participantSpy = Mockito.spy(IGameEntityParticipant.class);
		Mockito.doReturn(true).when(participantSpy).hasInterest(Mockito.any(), Mockito.any());

		GameEntity gameEntity = new GameEntity();
		gameEntity.getParticipation().addParticipant(participantSpy);
		
		
		gameEntity.setActive(false);
		
		String testChange = "Test_Change_Participant";
		gameEntity.getParticipation().notifyOfChange(testChange);

		
		Mockito.verify(participantSpy).onEntityActiveStatusChange(gameEntity);
		Mockito.verify(participantSpy).onSubjectChange(gameEntity, testChange);
	}
	
	
	@Test
	public void GameEntity_IGameEntityObserver_ShouldReceiveGenericChanges() {
		IGameEntityObserver observerSpy = Mockito.spy(IGameEntityObserver.class);
		Mockito.doReturn(true).when(observerSpy).hasInterest(Mockito.any(), Mockito.any());

		GameEntity gameEntity = new GameEntity();
		gameEntity.getObservation().addObserver(observerSpy);
		
		
		gameEntity.setActive(false);
		
		String testChange = "Test_Change_Observer";
		gameEntity.getObservation().notifyOfChange(testChange);

		
		Mockito.verify(observerSpy).entityActiveStatusChanged(gameEntity);
		Mockito.verify(observerSpy).subjectChanged(gameEntity, testChange);
	}
	
	@Test
	public void GameEntity_IGameEntityObserver_ShouldReceiveGenericChanges_MockVersion() {
		/* This does the same as the above but uses a mock,
		 * which means the default implementations of the interface will not be called 
		 * and instead we have to manually override all relevant methods.
		 */
		
		IGameEntityObserver observerMock = Mockito.mock(IGameEntityObserver.class);
		Mockito.when(observerMock.hasInterest(Mockito.any(), Mockito.any())).thenReturn(true);
		Mockito.when(observerMock.getObservationInterest(Mockito.any(), Mockito.any())).thenReturn(observerMock);

		GameEntity gameEntity = new GameEntity();
		gameEntity.getObservation().addObserver(observerMock);
		
		
		gameEntity.setActive(false);
		
		String testChange = "Test_Change_Observer-Mock";
		gameEntity.getObservation().notifyOfChange(testChange);

		
		Mockito.verify(observerMock).entityActiveStatusChanged(gameEntity);
		Mockito.verify(observerMock).subjectChanged(gameEntity, testChange);
	}
	
}
