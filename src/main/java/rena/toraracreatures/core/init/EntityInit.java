package rena.toraracreatures.core.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import rena.toraracreatures.ToraraCreatures;
import rena.toraracreatures.entities.WallFossilEntity;
import rena.toraracreatures.entities.mobs.*;

import java.util.Random;

public class EntityInit {

    public static EntityType<GreenlandSharkEntity> GREENLAND_SHARK;
    public static EntityType<DickinsoniaRexEntity> DICKINSONIA_REX;
    public static EntityType<AgujaceratopsEntity> AGUJACERATOPS;
    public static EntityType<LampreyEntity> LAMPREY;
    public static EntityType<ManateeEntity> MANATEE;
    public static EntityType<CaracalEntity> CARACAL;

    public static EntityType<WallFossilEntity> WALL_FOSSIL_ENTITY;

    public static void register(){

        GREENLAND_SHARK = register("greenland_shark", EntityType.Builder.of(GreenlandSharkEntity::new, EntityClassification.WATER_CREATURE).sized(1.5f, 1.0f));
        DICKINSONIA_REX = register("dickinsonia_rex", EntityType.Builder.of(DickinsoniaRexEntity::new, EntityClassification.WATER_CREATURE).sized(0.5f, 0.5f));
        AGUJACERATOPS = register("agujaceratops", EntityType.Builder.of(AgujaceratopsEntity::new, EntityClassification.CREATURE).sized(0.5f, 0.5f));
        LAMPREY = register("lamprey", EntityType.Builder.of(LampreyEntity::new, EntityClassification.WATER_CREATURE).sized(0.5f, 0.5f));
        MANATEE = register("manatee", EntityType.Builder.of(ManateeEntity::new, EntityClassification.WATER_CREATURE).sized(1.5f, 1.0f));
        CARACAL = register("caracal", EntityType.Builder.of(CaracalEntity::new, EntityClassification.CREATURE).sized(1.5f, 1.0f));

        WALL_FOSSIL_ENTITY = register("wall_fossil_entity", EntityType.Builder.<WallFossilEntity>of(WallFossilEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE));

        ForgeRegistries.ENTITIES.registerAll(
                GREENLAND_SHARK,
                DICKINSONIA_REX,
                AGUJACERATOPS,
                LAMPREY,
                MANATEE,
                CARACAL,

                WALL_FOSSIL_ENTITY
        );

    }


    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceLocation regName = new ResourceLocation(ToraraCreatures.MOD_ID, name);
        return (EntityType<T>) builder.build(name).setRegistryName(regName);
    }

    public static boolean rollSpawn(int rolls, Random random, SpawnReason reason){
        if(reason == SpawnReason.SPAWNER){
            return true;
        }else{
            return rolls <= 0 || random.nextInt(rolls) == 0;
        }
    }
}
