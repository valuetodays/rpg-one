package billy.rpg.game.core.character;

import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;

/**
 * 敌人角色
 *
 * @author lei.liu@datatist.com
 * @since 2018-10-08 14:50:56
 */
public class EnemyCharacter extends Fightable {

    public EnemyCharacter(GameContainer gameContainer) {
        super(gameContainer);
    }


    @Override
    public void setRoleMetaData(RoleMetaData roleMetaData) {
        this.roleMetaData = roleMetaData.clone();
    }

}
