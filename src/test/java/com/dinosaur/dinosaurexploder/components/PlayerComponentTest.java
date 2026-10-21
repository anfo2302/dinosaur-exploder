/*
 * SPDX-FileCopyrightText: 2026 jvondermarck
 * SPDX-License-Identifier: MIT
 */

package com.dinosaur.dinosaurexploder.components;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.dinosaur.dinosaurexploder.utils.MockGameTimer;
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
}
