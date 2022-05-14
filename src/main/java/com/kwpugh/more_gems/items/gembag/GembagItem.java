package com.kwpugh.more_gems.items.gembag;

import com.kwpugh.more_gems.MoreGems;
import com.kwpugh.more_gems.init.ContainerInit;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class GembagItem extends Item
{
    public GembagItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public boolean canBeNested()
    {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        if(world.isClient()) return TypedActionResult.pass(player.getStackInHand(hand));

        ItemStack offHandStack = player.getOffHandStack();

        if(offHandStack.isOf(this))
        {
            if(MoreGems.CONFIG.GENERAL.enableSingleGemBagkOnHotbar)
            {
                if(onlyOneBackpack(world, player, ContainerInit.GEMBAG))
                {
                    openBackpack(world, player, hand);
                }
                else
                {
                    player.sendMessage((new TranslatableText("Must be only one Backpack in hotbar or offhand").formatted(Formatting.WHITE)), true);
                }
            }
            else
            {
                openBackpack(world, player, hand);
            }

            return TypedActionResult.success(player.getStackInHand(hand));
        }

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    public static void openBackpack(World world, PlayerEntity user, Hand hand)
    {
        ContainerProviderRegistry.INSTANCE.openContainer(ContainerInit.GEMBAG_IDENTIFIER, user, buf -> {
            ItemStack stack = user.getStackInHand(hand);
            buf.writeItemStack(stack);
            buf.writeInt(hand == Hand.MAIN_HAND ? 0 : 1);
            buf.writeString(stack.getName().asString());
        });
    }

    public static GembagInventory getInventory(ItemStack stack, Hand hand, PlayerEntity player)
    {
        if(!stack.hasNbt())
        {
            stack.setNbt(new NbtCompound());
        }

        if(!stack.getNbt().contains("gembag"))
        {
            stack.getNbt().put("gembag", new NbtCompound());
        }

        return new GembagInventory(stack.getNbt().getCompound("gembag"), hand, player);
    }

    public static boolean onlyOneBackpack(World world, PlayerEntity player, Item item)
    {
        //if(world.isClient) return false;

        /*
         * Slot Reference Info:
         * 0-8 = hotbar
         * 9-35 = main inventory
         * 36-39 = armor slots
         * 40 = shield slot
         */

        int backpackCount = 0;
        int minSlot = 0;
        int maxSlot = 8;

        PlayerInventory inv = player.getInventory();
        int size = inv.size();

        //Is the more than one item in the hotbar?
        for (int slot = minSlot; slot <= maxSlot; slot++)
        {
            ItemStack stack = inv.getStack(slot);
            if (stack.getItem() == item)
            {
                backpackCount++;
            }
        }

        if(player.getOffHandStack().isOf(item))
        {
            backpackCount++;
        }


        if(backpackCount > 1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(new TranslatableText("item.more_gems.gembag.tip1").formatted(Formatting.YELLOW));
        tooltip.add(new TranslatableText("item.more_gems.gembag.tip2").formatted(Formatting.YELLOW));
    }
}