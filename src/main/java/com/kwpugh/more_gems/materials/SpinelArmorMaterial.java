package com.kwpugh.more_gems.materials;

import com.kwpugh.more_gems.MoreGems;
import com.kwpugh.more_gems.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SpinelArmorMaterial implements ArmorMaterial
{
    private static int durabilityMultiplier = MoreGems.CONFIG.GENERAL.spinelDurabilityMultiplier;
    private static int enchantability = MoreGems.CONFIG.GENERAL.spinelArmorEnchantability;
    private static float toughness = MoreGems.CONFIG.GENERAL.spinelToughness;
    private static float knochback = MoreGems.CONFIG.GENERAL.spinelKnockbackResistance;

    private static int spinelHead = MoreGems.CONFIG.GENERAL.spinelHeadProtection;
    private static int spinelChest = MoreGems.CONFIG.GENERAL.spinelChestProtection;
    private static int spinelLeggings = MoreGems.CONFIG.GENERAL.spinelLeggingsProtection;
    private static int spinelBoots = MoreGems.CONFIG.GENERAL.spinelBootsProtection;

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNT = new int[]{spinelHead, spinelLeggings, spinelChest, spinelBoots};

    @Override
    public int getDurability(EquipmentSlot slot)
    {
        return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot)
    {
        return PROTECTION_AMOUNT[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability()
    {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.ofItems(ItemInit.SPINEL);
    }

    @Override
    public String getName()
    {
        return "spinel";
    }

    @Override
    public float getToughness()
    {
        return toughness;
    }

    @Override
    public float getKnockbackResistance()
    {
        return knochback;
    }
}
