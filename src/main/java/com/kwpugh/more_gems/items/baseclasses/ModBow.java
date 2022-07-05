package com.kwpugh.more_gems.items.baseclasses;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.kwpugh.more_gems.MoreGems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ModBow extends BowItem
{
	public static float speed = MoreGems.CONFIG.GENERAL.projectileSpeed;
	public static double damage1 = MoreGems.CONFIG.GENERAL.projectileDamageFactorFirst;
	public static double damage2 = MoreGems.CONFIG.GENERAL.projectileDamageFactorSecond;
	public static float divergence = MoreGems.CONFIG.GENERAL.projectileDivergence;

	public ModBow(Settings settings)
	{
		super(settings);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks)
	{
		if (user instanceof PlayerEntity)
		{
			PlayerEntity playerEntity = (PlayerEntity) user;
			boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemStack = playerEntity.getArrowType(stack);
			if (!itemStack.isEmpty() || bl)
			{
				if (itemStack.isEmpty())
				{
					itemStack = new ItemStack(Items.ARROW);
				}

				int i = getMaxUseTime(stack) - remainingUseTicks;
				float f = getPullProgress(i);
				if (f >= 0.1D)
				{
					boolean bl2 = bl && itemStack.getItem() == Items.ARROW;
					if (!world.isClient)
					{
						ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
						PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
						persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * speed, divergence);

						if (f == 1.0F)
						{
							persistentProjectileEntity.setCritical(true);
						}

						int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);

						if (j > 0)
						{
							persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + j * damage1 + damage2);
						}

						int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);

						if (k > 0) {
							persistentProjectileEntity.setPunch(k);
						}

						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0)
						{
							persistentProjectileEntity.setOnFireFor(100);
						}

						stack.damage(1, (LivingEntity) playerEntity, (Consumer<LivingEntity>) (p) -> {
							p.sendToolBreakStatus(playerEntity.getActiveHand());
						});

						if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW))
						{
							persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
						}

						world.spawnEntity(persistentProjectileEntity);
					}
					
					world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

					if (!bl2 && !playerEntity.getAbilities().creativeMode)
					{
						itemStack.decrement(1);
						if (itemStack.isEmpty())
						{
							playerEntity.getInventory().removeOne(itemStack);
						}
					}

					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
		}
	}


	public static float getPullProgress(int useTicks)
	{
		float f = (float)useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}

	public int getMaxUseTime(ItemStack stack)
	{
		return 72000;
	}

	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack itemStack = user.getStackInHand(hand);
		boolean bl = !user.getArrowType(itemStack).isEmpty();

		if (!user.getAbilities().creativeMode && !bl)
		{
			return TypedActionResult.fail(itemStack);
		}
		else
		{
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
	}

	public Predicate<ItemStack> getProjectiles()
	{
		return BOW_PROJECTILES;
	}

	public int getRange()
	{
		return 15;
	}
}