package dev.jessicastrong.jesstanks;

import com.google.common.collect.Lists;
import dev.jessicastrong.jesstanks.block.TankControllerBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JessTanks implements ModInitializer {
    public static final String MOD_ID = "jesstanks";

    public static Block IRON_TANK_WALL, IRON_TANK_WINDOW, IRON_TANK_CONTROLLER, IRON_TANK_OUTLET;
    public static Identifier IRON_TANK_WALL_IDENTIFIER, IRON_TANK_WINDOW_IDENTIFIER, IRON_TANK_CONTROLLER_IDENTIFIER, IRON_TANK_OUTLET_IDENTIFIER;

    public static ItemGroup JESS_TANKS_ITEM_GROUP = new FabricItemGroupBuilderImpl(new Identifier(MOD_ID, "generic"))
            .displayName(Text.translatable("itemGroup.jesstanks.generic"))
            .entries((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(IRON_TANK_WALL);
                entries.add(IRON_TANK_WINDOW);
                entries.add(IRON_TANK_CONTROLLER);
                entries.add(IRON_TANK_OUTLET);
            }).build();

    public JessTanks() {
        IRON_TANK_WALL_IDENTIFIER = new Identifier(MOD_ID, "iron_tank_wall");
        IRON_TANK_WINDOW_IDENTIFIER = new Identifier(MOD_ID, "iron_tank_window");
        IRON_TANK_CONTROLLER_IDENTIFIER = new Identifier(MOD_ID, "iron_tank_controller");
        IRON_TANK_OUTLET_IDENTIFIER = new Identifier(MOD_ID, "iron_tank_outlet");

        IRON_TANK_WALL = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL));
        IRON_TANK_WINDOW = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL).nonOpaque());
        IRON_TANK_OUTLET = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL));
        IRON_TANK_CONTROLLER = new TankControllerBlock(FabricBlockSettings.of(Material.METAL)
                .requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL),
                new Block[] {IRON_TANK_WALL, IRON_TANK_WINDOW, IRON_TANK_OUTLET},
                new Block[] {IRON_TANK_WALL});
    }

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, IRON_TANK_WALL_IDENTIFIER, IRON_TANK_WALL);
        Registry.register(Registries.ITEM, IRON_TANK_WALL_IDENTIFIER, new BlockItem(IRON_TANK_WALL, new FabricItemSettings()));

        Registry.register(Registries.BLOCK, IRON_TANK_WINDOW_IDENTIFIER, IRON_TANK_WINDOW);
        Registry.register(Registries.ITEM, IRON_TANK_WINDOW_IDENTIFIER, new BlockItem(IRON_TANK_WINDOW, new FabricItemSettings()));

        Registry.register(Registries.BLOCK, IRON_TANK_CONTROLLER_IDENTIFIER, IRON_TANK_CONTROLLER);
        Registry.register(Registries.ITEM, IRON_TANK_CONTROLLER_IDENTIFIER, new BlockItem(IRON_TANK_CONTROLLER, new FabricItemSettings()));

        Registry.register(Registries.BLOCK, IRON_TANK_OUTLET_IDENTIFIER, IRON_TANK_OUTLET);
        Registry.register(Registries.ITEM, IRON_TANK_OUTLET_IDENTIFIER, new BlockItem(IRON_TANK_OUTLET, new FabricItemSettings()));
    }
}
