/*
 * SPDX-FileCopyrightText: 2026 jvondermarck
 * SPDX-License-Identifier: MIT
 */

package com.dinosaur.dinosaurexploder.components;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.utils.MockGameTimer;
import com.dinosaur.dinosaurexploder.view.DinosaurGUI;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class PlayerComponentTest {

  private PlayerComponent player;
  private Entity playerEntity;
  private final int PLAYER_STARTING_COORDS = 100;

  @BeforeEach
  void setUp() {
    player = new PlayerComponent(new MockGameTimer());

    playerEntity = new Entity();
    playerEntity.setPosition(PLAYER_STARTING_COORDS, PLAYER_STARTING_COORDS);
    playerEntity.addComponent(player);
  }

  private void stubMovementAnimation(MockedStatic<FXGL> fxgl) {
    EntityBuilder animationBuilder = mock(EntityBuilder.class);
    when(animationBuilder.at(any(Point2D.class))).thenReturn(animationBuilder);
    when(animationBuilder.view(any(Node.class))).thenReturn(animationBuilder);
    when(animationBuilder.with(any(ExpireCleanComponent.class))).thenReturn(animationBuilder);

    fxgl.when(FXGL::entityBuilder).thenReturn(animationBuilder);
  }

  private AllyComponent attachMockAlly() {
    AllyComponent ally = mock(AllyComponent.class);
    player.setAlly(ally);
    return ally;
  }

  /** Verifies that moving up decreases Y by PlayerComponent.movementSpeed without changing X. */
  @Test
  @DisplayName("Moving up should decrease Y by PlayerComponent.movementSpeed and leave X unchanged")
  void shouldMoveUpByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      player.moveUp();

      assertEquals(PLAYER_STARTING_COORDS, playerEntity.getX(), 0.001);
      assertEquals(PLAYER_STARTING_COORDS - player.movementSpeed, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that moving down increases Y by PlayerComponent.movementSpeed without changing X. */
  @Test
  @DisplayName(
      "Moving down should increase Y by PlayerComponent.movementSpeed and leave X unchanged")
  void shouldMoveDownByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      player.moveDown();

      assertEquals(PLAYER_STARTING_COORDS, playerEntity.getX(), 0.001);
      assertEquals(PLAYER_STARTING_COORDS + player.movementSpeed, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that moving right increases X by PlayerComponent.movementSpeed without changing Y. */
  @Test
  @DisplayName(
      "Moving right should increase X by PlayerComponent.movementSpeed and leave Y unchanged")
  void shouldMoveRightByMovementSpeed() {

    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      player.moveRight();

      assertEquals(playerEntity.getY(), PLAYER_STARTING_COORDS, 0.001);
      assertEquals(PLAYER_STARTING_COORDS + player.movementSpeed, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving left decreases X by PlayerComponent.movementSpeed without changing Y. */
  @Test
  @DisplayName(
      "Moving left should decrease X by PlayerComponent.movementSpeed and leave Y unchanged")
  void shouldMoveLeftByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      player.moveLeft();

      assertEquals(playerEntity.getY(), PLAYER_STARTING_COORDS, 0.001);
      assertEquals(PLAYER_STARTING_COORDS - player.movementSpeed, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving up also affects an ally. */
  @Test
  @DisplayName("Moving up should instruct allies to move as well")
  void shouldMoveAllyUpByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();
      player.moveUp();

      verify(ally).moveUp(player.movementSpeed);
    }
  }

  /** Verifies that moving down also affects allies. */
  @Test
  @DisplayName("Moving down should instruct allies to move as well")
  void shouldMoveAllyDownByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();
      player.moveDown();

      verify(ally).moveDown(player.movementSpeed);
    }
  }

  /** Verifies that moving right also affects allies. */
  @Test
  @DisplayName("Moving right should instruct allies to move as well")
  void shouldMoveAllyRightByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();
      player.moveRight();

      verify(ally).moveRight(player.movementSpeed);
    }
  }

  /** Verifies that moving left also affects allies. */
  @Test
  @DisplayName("Moving left should instruct allies to move as well")
  void shouldMoveAllyLeftByMovementSpeed() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();
      player.moveLeft();

      verify(ally).moveLeft(player.movementSpeed);
    }
  }

  /** Verifies that moving left to 0 X coordinate is possible. */
  @Test
  @DisplayName("Moving left to 0 X should work")
  void shouldBeAbleToMoveToZeroX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setX(player.movementSpeed);
      player.moveLeft();

      assertEquals(0, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving up to 0 Y coordinate is possible. */
  @Test
  @DisplayName("Moving up to 0 Y should work")
  void shouldBeAbleToMoveToZeroY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setY(player.movementSpeed);
      player.moveUp();

      assertEquals(0, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that moving down to max Y coordinate is possible. */
  @Test
  @DisplayName("Moving down to max Y should work")
  void shouldBeAbleToMoveToMaxY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setY(DinosaurGUI.HEIGHT - player.movementSpeed);
      player.moveDown();

      assertEquals(DinosaurGUI.HEIGHT, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that moving right to max X coordinate is possible. */
  @Test
  @DisplayName("Moving right to max X should work")
  void shouldBeAbleToMoveToMaxX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setX(DinosaurGUI.WIDTH - player.movementSpeed);
      player.moveRight();

      assertEquals(DinosaurGUI.WIDTH, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving past max X coordinate is not possible. */
  @Test
  @DisplayName("Moving past max X should not work")
  void shouldNotBeAbleToMovePastMaxX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setX(DinosaurGUI.WIDTH - player.movementSpeed + 1);
      player.moveRight();

      assertEquals(DinosaurGUI.WIDTH, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving past max Y coordinate is not possible. */
  @Test
  @DisplayName("Moving past max Y should not work")
  void shouldNotBeAbleToMovePastMaxY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setY(DinosaurGUI.HEIGHT - player.movementSpeed + 1);
      player.moveDown();

      assertEquals(DinosaurGUI.HEIGHT, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that moving below 0 X coordinate is not possible. */
  @Test
  @DisplayName("Moving below zero X should not work")
  void shouldNotBeAbleToMoveBelowZeroX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setX(player.movementSpeed - 1);
      player.moveLeft();

      assertEquals(0, playerEntity.getX(), 0.001);
    }
  }

  /** Verifies that moving below 0 Y coordinate is not possible. */
  @Test
  @DisplayName("Moving below zero Y should not work")
  void shouldNotBeAbleToMoveBelowZeroY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      playerEntity.setY(player.movementSpeed - 1);
      player.moveUp();

      assertEquals(0, playerEntity.getY(), 0.001);
    }
  }

  /** Verifies that attempting to move past max X coordinates does not move the ally. */
  @Test
  @DisplayName("Trying to move past max X should not affect ally")
  void allyShouldNotBeMovedPastMaxX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();

      playerEntity.setX(DinosaurGUI.WIDTH - player.movementSpeed + 1);
      player.moveRight();

      verify(ally, never()).moveRight(player.movementSpeed);
    }
  }

  /** Verifies that attempting to move past max Y coordinates does not move the ally. */
  @Test
  @DisplayName("Trying to move past max Y should not affect ally")
  void allyShouldNotBeMovedPastMaxY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();

      playerEntity.setY(DinosaurGUI.HEIGHT - player.movementSpeed + 1);
      player.moveDown();

      verify(ally, never()).moveDown(player.movementSpeed);
    }
  }

  /** Verifies that attempting to move below 0 Y does not move the ally. */
  @Test
  @DisplayName("Trying to move below 0 Y should not affect ally")
  void allyShouldNotBeMovedBelowZeroY() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();

      playerEntity.setY(player.movementSpeed - 1);
      player.moveUp();

      verify(ally, never()).moveUp(player.movementSpeed);
    }
  }

  /** Verifies that attempting to move below 0 X does not move the ally. */
  @Test
  @DisplayName("Trying to move below 0 X should not affect ally")
  void allyShouldNotBeMovedBelowZeroX() {
    try (MockedStatic<FXGL> fxgl = mockStatic(FXGL.class)) {
      stubMovementAnimation(fxgl);

      AllyComponent ally = attachMockAlly();

      playerEntity.setX(player.movementSpeed - 1);
      player.moveLeft();

      verify(ally, never()).moveLeft(player.movementSpeed);
    }
  }
}
