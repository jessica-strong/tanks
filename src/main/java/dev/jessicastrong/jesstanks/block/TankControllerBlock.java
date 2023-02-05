package dev.jessicastrong.jesstanks.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class TankControllerBlock extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public final List<Block> validTankWallBlocks;
    public final List<Block> validTankCornerBlocks;


    public TankControllerBlock(Settings settings, Block[] validTankWallBlocks, Block[] validTankCornerBlocks) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));

        this.validTankWallBlocks = Lists.newArrayList(validTankWallBlocks);
        this.validTankCornerBlocks = Lists.newArrayList(validTankCornerBlocks);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return super.rotate(state, rotation).with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return super.mirror(state, mirror).with(FACING, mirror.apply(state.get(FACING)));
    }

    public BlockPos findLowerCornerOfMultiBlock(World world, BlockPos startingPos) {
        BlockPos lowerCorner = startingPos;

        // Do this for Y
        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.down()).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.down();
            }
        }

        // Do this for X

        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.add(-1, 0, 0)).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.add(-1, 0, 0);
            }
        }

        // Do this for Z

        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.add(0, 0, -1)).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.add(0, 0, -1);
            }
        }

        return lowerCorner;
    }

    public BlockPos findUpperCornerOfMultiblock(World world, BlockPos startingPos) {
        BlockPos lowerCorner = startingPos;

        // Do this for Y
        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.up()).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.up();
            }
        }

        // Do this for X

        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.add(1, 0, 0)).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.add(1, 0, 0);
            }
        }

        // Do this for Z

        for(int i = 0; i < 16; i++) {
            Block block = world.getBlockState(lowerCorner.add(0, 0, 1)).getBlock();

            if(validTankWallBlocks.contains(block)) {
                lowerCorner = lowerCorner.add(0, 0, 1);
            }
        }

        return lowerCorner;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            boolean isTankValid = true;
            // Find the lower corner of the tank
            BlockPos lowerCorner = findLowerCornerOfMultiBlock(world, pos);
            BlockPos upperCorner = findUpperCornerOfMultiblock(world, pos);

            int tankSizeX = (upperCorner.getX() - lowerCorner.getX()) + 1;
            int tankSizeY = (upperCorner.getY() - lowerCorner.getY()) + 1;
            int tankSizeZ = (upperCorner.getZ() - lowerCorner.getZ()) + 1;

            boolean validTank = (tankSizeX > 2 && tankSizeX < 32 && tankSizeY > 2 && tankSizeY < 32 && tankSizeZ > 2 && tankSizeZ < 32);

            player.sendMessage(Text.of("Lower corner is " + lowerCorner.toShortString()));
            player.sendMessage(Text.of("Upper corner is " + upperCorner.toShortString()));
            player.sendMessage(Text.of("Tank size is " + tankSizeX + "x" + tankSizeY + "x" + tankSizeZ));
            player.sendMessage(Text.of("Tank size is " + (validTank ? "a valid size" : "an invalid size")));

            return ActionResult.PASS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
