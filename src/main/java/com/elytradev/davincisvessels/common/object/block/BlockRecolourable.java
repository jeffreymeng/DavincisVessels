package com.elytradev.davincisvessels.common.object.block;

import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class BlockRecolourable extends BlockColored {

    public BlockRecolourable(Material materialIn, SoundType soundType) {
        super(materialIn);
        this.setSoundType(soundType);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn != null && playerIn != null && !playerIn.isSneaking()) {
            IBlockState blockState = worldIn.getBlockState(pos);
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (blockState == null || heldItem.isEmpty()) return false;

            if (heldItem.getItem() != null && heldItem.getItem() instanceof ItemDye) {
                if (!Objects.equals(EnumDyeColor.byDyeDamage(heldItem.getItemDamage()), blockState.getValue(COLOR))) {
                    if (!worldIn.isRemote) {
                        blockState = blockState.withProperty(BlockColored.COLOR, EnumDyeColor.byDyeDamage(playerIn.getHeldItem(hand).getItemDamage()));
                        worldIn.setBlockState(pos, blockState);
                        if (!playerIn.capabilities.isCreativeMode)
                            playerIn.getHeldItem(hand).setCount(playerIn.getHeldItem(hand).getCount() - 1);
                    } else {
                        playerIn.swingArm(hand);
                    }
                    return true;
                }
            }
        }

        return false;
    }


}
