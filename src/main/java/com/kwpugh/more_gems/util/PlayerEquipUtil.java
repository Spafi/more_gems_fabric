package com.kwpugh.more_gems.util;

import com.kwpugh.more_gems.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerEquipUtil
{
	public static boolean isWearingCrown(PlayerEntity player)
	{
		ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);

		//Full Set
		if(head.getItem() == ItemInit.CROWN_GEM)
		{
			return true;
		}

		return false;
	}

	public static boolean isWearingCrownNetherite(PlayerEntity player)
	{
		ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);

		//Full Set
		if(head.getItem() == ItemInit.CROWN_NETHERITE_GEM)
		{
			return true;
		}

		return false;
	}

	// Generalized check for itemstack in inventory
	public static boolean hasItemInInventory(PlayerEntity player, Item item)
	{
		PlayerInventory inv = player.getInventory();
		int size = inv.size();

		//Is the item in the player inventory?
		for (int slot = 0; slot < size; slot++)
		{
			ItemStack stack = inv.getStack(slot);
			if (stack.getItem() == item)
			{
				return true;
			}
		}

		return false;
	}

	// Generalized check for itemstack in off hand
	public static boolean hasItemInOffHand(PlayerEntity player, Item item)
	{
		ItemStack offHand = player.getOffHandStack();

		if(offHand.getItem() == item)
		{
			return true;
		}

		return false;
	}

	// Generalized check for itemstack in main hand
	public static boolean hasItemInMainHand(PlayerEntity player, Item item)
	{
		ItemStack offHand = player.getMainHandStack();

		if(offHand.getItem() == item)
		{
			return true;
		}

		return false;
	}

	// Generalized check for itemstack in ender chest
	public static boolean hasItemInEnder(PlayerEntity player, Item item)
	{
		if(!(player instanceof ServerPlayerEntity serverPlayer)) return false;

		EnderChestInventory inv = serverPlayer.getEnderChestInventory();
		int size = inv.size();

		//Is the item in the ender chest inventory?
		for (int slot = 0; slot < size; slot++)
		{
			ItemStack stack = inv.getStack(slot);
			if (stack.getItem() == item)
			{
				return true;
			}
		}

		return false;
	}

	// Return itemstack for item in ender chest
	public static ItemStack getItemStackInEnder(PlayerEntity player, Item item)
	{
		if(!(player instanceof ServerPlayerEntity serverPlayer)) return ItemStack.EMPTY;

		EnderChestInventory inv = serverPlayer.getEnderChestInventory();
		int size = inv.size();

		//Is the item in the ender chest inventory?
		for (int slot = 0; slot < size; slot++)
		{
			ItemStack stack = inv.getStack(slot);
			if (stack.getItem() == item)
			{
				return stack;
			}
		}

		return ItemStack.EMPTY;
	}
} 