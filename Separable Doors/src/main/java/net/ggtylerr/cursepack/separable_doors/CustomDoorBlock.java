package net.ggtylerr.cursepack.separable_doors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CustomDoorBlock extends Block {
    public static final DirectionProperty FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoorHinge> HINGE;
    public static final BooleanProperty POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;

    protected CustomDoorBlock(Settings block$Settings_1) {
        super(block$Settings_1);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateFactory.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false)).with(HINGE, DoorHinge.LEFT)).with(POWERED, false)));
    }

    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        Direction direction_1 = (Direction)blockState_1.get(FACING);
        boolean boolean_1 = !(Boolean)blockState_1.get(OPEN);
        boolean boolean_2 = blockState_1.get(HINGE) == DoorHinge.RIGHT;
        switch(direction_1) {
            case EAST:
            default:
                return boolean_1 ? WEST_SHAPE : (boolean_2 ? SOUTH_SHAPE : NORTH_SHAPE);
            case SOUTH:
                return boolean_1 ? NORTH_SHAPE : (boolean_2 ? WEST_SHAPE : EAST_SHAPE);
            case WEST:
                return boolean_1 ? EAST_SHAPE : (boolean_2 ? NORTH_SHAPE : SOUTH_SHAPE);
            case NORTH:
                return boolean_1 ? SOUTH_SHAPE : (boolean_2 ? EAST_SHAPE : WEST_SHAPE);
        }
    }

    public void afterBreak(World world_1, PlayerEntity playerEntity_1, BlockPos blockPos_1, BlockState blockState_1, BlockEntity blockEntity_1, ItemStack itemStack_1) {
        super.afterBreak(world_1, playerEntity_1, blockPos_1, Blocks.AIR.getDefaultState(), blockEntity_1, itemStack_1);
    }

//    public void onBreak(World world_1, BlockPos blockPos_1, BlockState blockState_1, PlayerEntity playerEntity_1) {
//        DoubleBlockHalf doubleBlockHalf_1 = (DoubleBlockHalf)blockState_1.get(HALF);
//        BlockPos blockPos_2 = doubleBlockHalf_1 == DoubleBlockHalf.LOWER ? blockPos_1.up() : blockPos_1.down();
//        BlockState blockState_2 = world_1.getBlockState(blockPos_2);
//        if (blockState_2.getBlock() == this && blockState_2.get(HALF) != doubleBlockHalf_1) {
//            world_1.setBlockState(blockPos_2, Blocks.AIR.getDefaultState(), 35);
//            world_1.playLevelEvent(playerEntity_1, 2001, blockPos_2, Block.getRawIdFromState(blockState_2));
//            ItemStack itemStack_1 = playerEntity_1.getMainHandStack();
//            if (!world_1.isClient && !playerEntity_1.isCreative()) {
//                Block.dropStacks(blockState_1, world_1, blockPos_1, (BlockEntity)null, playerEntity_1, itemStack_1);
//                Block.dropStacks(blockState_2, world_1, blockPos_2, (BlockEntity)null, playerEntity_1, itemStack_1);
//            }
//        }
//
//        super.onBreak(world_1, blockPos_1, blockState_1, playerEntity_1);
//    }

    public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
        switch(blockPlacementEnvironment_1) {
            case LAND:
                return (Boolean)blockState_1.get(OPEN);
            case WATER:
                return false;
            case AIR:
                return (Boolean)blockState_1.get(OPEN);
            default:
                return false;
        }
    }

    private int getOpenSoundEventId() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getCloseSoundEventId() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        if (blockPos_1.getY() < 255 && itemPlacementContext_1.getWorld().getBlockState(blockPos_1.up()).canReplace(itemPlacementContext_1)) {
            World world_1 = itemPlacementContext_1.getWorld();
            boolean boolean_1 = world_1.isReceivingRedstonePower(blockPos_1) || world_1.isReceivingRedstonePower(blockPos_1.up());
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing())).with(HINGE, this.getHinge(itemPlacementContext_1))).with(POWERED, boolean_1)).with(OPEN, boolean_1)).with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        world_1.setBlockState(blockPos_1.up(), (BlockState)blockState_1.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    private DoorHinge getHinge(ItemPlacementContext itemPlacementContext_1) {
        BlockView blockView_1 = itemPlacementContext_1.getWorld();
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        Direction direction_1 = itemPlacementContext_1.getPlayerFacing();
        BlockPos blockPos_2 = blockPos_1.up();
        Direction direction_2 = direction_1.rotateYCounterclockwise();
        BlockPos blockPos_3 = blockPos_1.offset(direction_2);
        BlockState blockState_1 = blockView_1.getBlockState(blockPos_3);
        BlockPos blockPos_4 = blockPos_2.offset(direction_2);
        BlockState blockState_2 = blockView_1.getBlockState(blockPos_4);
        Direction direction_3 = direction_1.rotateYClockwise();
        BlockPos blockPos_5 = blockPos_1.offset(direction_3);
        BlockState blockState_3 = blockView_1.getBlockState(blockPos_5);
        BlockPos blockPos_6 = blockPos_2.offset(direction_3);
        BlockState blockState_4 = blockView_1.getBlockState(blockPos_6);
        int int_1 = (blockState_1.method_21743(blockView_1, blockPos_3) ? -1 : 0) + (blockState_2.method_21743(blockView_1, blockPos_4) ? -1 : 0) + (blockState_3.method_21743(blockView_1, blockPos_5) ? 1 : 0) + (blockState_4.method_21743(blockView_1, blockPos_6) ? 1 : 0);
        boolean boolean_1 = blockState_1.getBlock() == this && blockState_1.get(HALF) == DoubleBlockHalf.LOWER;
        boolean boolean_2 = blockState_3.getBlock() == this && blockState_3.get(HALF) == DoubleBlockHalf.LOWER;
        if ((!boolean_1 || boolean_2) && int_1 <= 0) {
            if ((!boolean_2 || boolean_1) && int_1 >= 0) {
                int int_2 = direction_1.getOffsetX();
                int int_3 = direction_1.getOffsetZ();
                Vec3d vec3d_1 = itemPlacementContext_1.getHitPos();
                double double_1 = vec3d_1.x - (double)blockPos_1.getX();
                double double_2 = vec3d_1.z - (double)blockPos_1.getZ();
                return (int_2 >= 0 || double_2 >= 0.5D) && (int_2 <= 0 || double_2 <= 0.5D) && (int_3 >= 0 || double_1 <= 0.5D) && (int_3 <= 0 || double_1 >= 0.5D) ? DoorHinge.LEFT : DoorHinge.RIGHT;
            } else {
                return DoorHinge.LEFT;
            }
        } else {
            return DoorHinge.RIGHT;
        }
    }

    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        if (this.material == Material.METAL) {
            return false;
        } else {
            blockState_1 = (BlockState)blockState_1.cycle(OPEN);
            world_1.setBlockState(blockPos_1, blockState_1, 10);
            world_1.playLevelEvent(playerEntity_1, (Boolean)blockState_1.get(OPEN) ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), blockPos_1, 0);
            return true;
        }
    }

    public void setOpen(World world_1, BlockPos blockPos_1, boolean boolean_1) {
        BlockState blockState_1 = world_1.getBlockState(blockPos_1);
        if (blockState_1.getBlock() == this && (Boolean)blockState_1.get(OPEN) != boolean_1) {
            world_1.setBlockState(blockPos_1, (BlockState)blockState_1.with(OPEN, boolean_1), 10);
            this.playOpenCloseSound(world_1, blockPos_1, boolean_1);
        }
    }

    public void neighborUpdate(BlockState blockState_1, World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1) {
        boolean boolean_2 = world_1.isReceivingRedstonePower(blockPos_1);
//        boolean boolean_2 = world_1.isReceivingRedstonePower(blockPos_1) || world_1.isReceivingRedstonePower(blockPos_1.offset(blockState_1.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (block_1 != this && boolean_2 != (Boolean)blockState_1.get(POWERED)) {
            if (boolean_2 != (Boolean)blockState_1.get(OPEN)) {
                this.playOpenCloseSound(world_1, blockPos_1, boolean_2);
            }

            world_1.setBlockState(blockPos_1, (BlockState)((BlockState)blockState_1.with(POWERED, boolean_2)).with(OPEN, boolean_2), 2);
        }

    }

//    public boolean canPlaceAt(BlockState blockState_1, ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
//        BlockPos blockPos_2 = blockPos_1.down();
//        BlockState blockState_2 = viewableWorld_1.getBlockState(blockPos_2);
//        if (blockState_1.get(HALF) == DoubleBlockHalf.LOWER) {
//            return blockState_2.method_20827(viewableWorld_1, blockPos_2, Direction.UP);
//        } else {
//            return blockState_2.getBlock() == this;
//        }
//    }

    private void playOpenCloseSound(World world_1, BlockPos blockPos_1, boolean boolean_1) {
        world_1.playLevelEvent((PlayerEntity)null, boolean_1 ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), blockPos_1, 0);
    }

//    public PistonBehavior getPistonBehavior(BlockState blockState_1) {
//        return PistonBehavior.DESTROY;
//    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
        return (BlockState)blockState_1.with(FACING, blockRotation_1.rotate((Direction)blockState_1.get(FACING)));
    }

    public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
        return blockMirror_1 == BlockMirror.NONE ? blockState_1 : (BlockState)blockState_1.rotate(blockMirror_1.getRotation((Direction)blockState_1.get(FACING))).cycle(HINGE);
    }

    @Environment(EnvType.CLIENT)
    public long getRenderingSeed(BlockState blockState_1, BlockPos blockPos_1) {
        return MathHelper.hashCode(blockPos_1.getX(), blockPos_1.down(blockState_1.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos_1.getZ());
    }

    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(new Property[]{HALF, FACING, OPEN, HINGE, POWERED});
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        OPEN = Properties.OPEN;
        HINGE = Properties.DOOR_HINGE;
        POWERED = Properties.POWERED;
        HALF = Properties.DOUBLE_BLOCK_HALF;
        NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        EAST_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    }
}
