# Course Project - Dinosaur Exploder
[Original README](README-original.md)

## Group Members
* Gargaar Abdulle Ahmed 
* Anna Forslund 
* Nathalie Fredriksson 
* William Olin 
* Andreas Karl Jonas Wallgren 

## How to reproduce tests
* `mvn clean -Dmaven.test.failure.ignore=true jacoco:report`
* `mvn test-compile org.pittest:pittest-maven:mutationCoverage` - <skipFailingTest>true</skipFailingTests>


### Test code
**Added**
- [AchievementPersistenceTest.java](src/test/java/com/dinosaur/dinosaurexploder/achievements/AchievementPersistenceTest.java)
- [AchievementRecoveryTest.java](src/test/java/com/dinosaur/dinosaurexploder/achievements/AchievementRecoveryTest.java)
- [PlayerComponentTest.java](src/test/java/com/dinosaur/dinosaurexploder/components/PlayerComponentTest.java)
- [PlayerRotationComponentTest.java](src/test/java/com/dinosaur/dinosaurexploder/components/PlayerRotationComponentTest.java) (moved from `model/`)
- [WeaponHeatComponentTest.java](src/test/java/com/dinosaur/dinosaurexploder/components/WeaponHeatComponentTest.java)
- [GameActionsDamagePlayerTest.java](src/test/java/com/dinosaur/dinosaurexploder/controller/core/GameActionsDamagePlayerTest.java)
- [GameDataTest.java](src/test/java/com/dinosaur/dinosaurexploder/model/GameDataTest.java)

**Modified**
- [CollisionHandlerTest.java](src/test/java/com/dinosaur/dinosaurexploder/model/CollisionHandlerTest.java)
- [GameOverStatsTest.java](src/test/java/com/dinosaur/dinosaurexploder/model/GameOverStatsTest.java)
- [SelectionTest.java](src/test/java/com/dinosaur/dinosaurexploder/model/SelectionTest.java)
- [SettingsProviderTest.java](src/test/java/com/dinosaur/dinosaurexploder/utils/SettingsProviderTest.java)
