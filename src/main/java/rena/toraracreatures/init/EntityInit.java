package rena.toraracreatures.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.WallFossilEntity;
import rena.toraracreatures.entities.mobs.DickinsoniaRexEntity;
import rena.toraracreatures.entities.mobs.GreenlandSharkEntity;

public class EntityInit {

    public static EntityType<GreenlandSharkEntity> GREENLAND_SHARK;
    public static EntityType<DickinsoniaRexEntity> DICKINSONIA_REX;
    public static EntityType<WallFossilEntity> WALL_FOSSIL_ENTITY;

    public static void register(){

        GREENLAND_SHARK = register("greenland_shark", EntityType.Builder.of(GreenlandSharkEntity::new, EntityClassification.WATER_CREATURE).sized(1.5f, 1.0f));
        DICKINSONIA_REX = register("dickinsonia_rex", EntityType.Builder.of(DickinsoniaRexEntity::new, EntityClassification.WATER_CREATURE).sized(0.5f, 0.5f));
        WALL_FOSSIL_ENTITY = register("wall_fossil_entity", EntityType.Builder.<WallFossilEntity>of(WallFossilEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE));

        ForgeRegistries.ENTITIES.registerAll(
                GREENLAND_SHARK,
                DICKINSONIA_REX,
                WALL_FOSSIL_ENTITY
        );

    }


    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceLocation regName = new ResourceLocation(ToraraCreatures.MOD_ID, name);
        return (EntityType<T>) builder.build(name).setRegistryName(regName);
    }
}
