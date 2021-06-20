package rena.toraracreatures.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import rena.toraracreatures.core.init.EntityInit;
import rena.toraracreatures.core.init.ItemInit;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WallFossilEntity extends HangingEntity implements IEntityAdditionalSpawnData {

    public Fossil fossil;

    public WallFossilEntity(EntityType<? extends WallFossilEntity>type, World world){
        super(type, world);
    }

    public WallFossilEntity(World world, BlockPos pos, Direction facing) {
        super(EntityInit.WALL_FOSSIL_ENTITY, world, pos);
        List<Fossil> list = new ArrayList<>();
        int i = 0;

        for (Fossil fossil : Fossil.VALUES) {
            this.fossil = fossil;
            this.setDirection(facing);
            if (this.survives()) {
                list.add(fossil);
                int j = fossil.getWidth() * fossil.getHeight();
                if (j > i) {
                    i = j;
                }
            }
        }

        if (!list.isEmpty()) {
            Iterator<Fossil> iterator = list.iterator();

            while (iterator.hasNext()) {
                Fossil fossil1 = iterator.next();
                if (fossil1.getWidth() * fossil1.getHeight() < i) {
                    iterator.remove();
                }
            }

            this.fossil = list.get(this.random.nextInt(list.size()));
        }

        this.setDirection(facing);
    }

    @OnlyIn(Dist.CLIENT)
    public WallFossilEntity(World world, BlockPos pos, Direction facing, Fossil fossil) {
        this(world, pos, facing);
        this.fossil = fossil;
        this.setDirection(facing);
    }


    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        compound.putInt("Type", fossil.ordinal());
        compound.putByte("Facing", (byte)this.direction.get2DDataValue());
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        fossil = Fossil.VALUES[compound.getInt("Type")];
        this.direction = Direction.from2DDataValue(compound.getByte("Facing"));
        super.readAdditionalSaveData(compound);
        this.setDirection(this.direction);
    }

    @Override
    public int getWidth() {
        return this.fossil == null ? 1 : this.fossil.getWidth();
    }

    @Override
    public int getHeight() {
        return this.fossil == null ? 1 : this.fossil.getHeight();
    }

    @Override
    public void dropItem(@Nullable Entity brokenEntity) {
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (brokenEntity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity) brokenEntity;
                if (playerentity.abilities.instabuild) {
                    return;
                }
            }

            this.spawnAtLocation(ItemInit.TC_PAINTING.get());
        }
    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
    }

    @Override
    public void moveTo(double x, double y, double z, float yaw, float pitch) {
        this.setPos(x, y, z);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        BlockPos blockpos = this.pos;
        this.setPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeEnum(fossil);
        buffer.writeBlockPos(pos);
        if (direction == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeEnum(direction);
        }
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.fossil = additionalData.readEnum(Fossil.class);
        this.pos = additionalData.readBlockPos();
        if (additionalData.readBoolean()) {
            this.setDirection(additionalData.readEnum(Direction.class));
        }
    }

    public enum Fossil {
        THECTARDIS_WALL_FOSSIL(16, 16);


        public static final Fossil[] VALUES = values();

        private final int width;
        private final int height;

        Fossil(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
