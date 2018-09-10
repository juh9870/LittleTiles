package com.creativemd.littletiles.common.container;

import com.creativemd.creativecore.gui.opener.GuiHandler;
import com.creativemd.littletiles.common.items.ItemRecipeAdvanced;
import com.creativemd.littletiles.common.tiles.preview.LittlePreviews;
import com.creativemd.littletiles.common.tiles.preview.LittleTilePreview;
import com.creativemd.littletiles.common.utils.selection.mode.SelectionMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class SubContainerRecipeAdvanced extends SubContainerConfigure {
	
	public BlockPos second;
	
	public SubContainerRecipeAdvanced(EntityPlayer player, ItemStack stack, BlockPos pos) {
		super(player, stack);
		this.second = pos;
	}
	
	@Override
	public void onPacketReceive(NBTTagCompound nbt) {
		if(nbt.getBoolean("save_selection"))
		{
			SelectionMode mode = ItemRecipeAdvanced.getSelectionMode(stack);
			LittlePreviews previews = mode.getPreviews(player.world, stack, nbt.getBoolean("includeVanilla"), nbt.getBoolean("includeCB"), nbt.getBoolean("includeLT"));
			((ItemRecipeAdvanced) stack.getItem()).saveLittlePreview(stack, previews);
			mode.clearSelection(stack);
			
			sendNBTToGui(stack.getTagCompound());
			GuiHandler.openGui("recipeadvanced", new NBTTagCompound(), player);
		}
		if(nbt.getBoolean("clear_content"))
		{
			LittleTilePreview.removePreviewTiles(stack);
			sendNBTToGui(stack.getTagCompound());
			GuiHandler.openGui("recipeadvanced", new NBTTagCompound(), player);
		}
		if(nbt.getBoolean("set_structure"))
		{
			stack.setTagCompound(nbt.getCompoundTag("stack"));
		}
	}

}
