package com.kwpugh.more_gems.items.juju;

import com.kwpugh.more_gems.util.EnableUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FallingCorundumJuju extends BaseJujuItem
{
	public FallingCorundumJuju(Settings settings)
	{
		super(settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if(EnableUtil.isEnabled(stack))
		{
			StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.SLOW_FALLING, 8, 0, false, false);
			LivingEntity player = (LivingEntity) entity;
			{
				player.addStatusEffect(effect);
			}
		}
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack itemStack = user.getStackInHand(hand);

		if (!world.isClient)
		{
			EnableUtil.changeEnabled(user, hand);
			user.sendMessage((new TranslatableText("Status changed")), true);
		}

		return TypedActionResult.success(itemStack);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
		tooltip.add(new TranslatableText("item.more_gems.corundum_juju.tip1").formatted(Formatting.GREEN));
		tooltip.add(new TranslatableText("item.more_gems.juju.no_sneak").formatted(Formatting.BLUE));
		tooltip.add(new TranslatableText("itme.more_gems.enable_status", EnableUtil.isEnabled(itemStack)).formatted(Formatting.GOLD));
	}
}