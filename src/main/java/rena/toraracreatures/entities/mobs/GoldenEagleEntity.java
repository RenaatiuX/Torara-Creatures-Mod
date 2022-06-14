package rena.toraracreatures.entities.mobs;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class GoldenEagleEntity extends TameableEntity {

    private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(GoldenEagleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TACKLING = EntityDataManager.defineId(GoldenEagleEntity.class, DataSerializers.BOOLEAN);

    private boolean isLandNavigator;
    private int timeFlying;

    protected GoldenEagleEntity(EntityType<? extends GoldenEagleEntity> eagle, World world) {
        super(eagle, world);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }


}
