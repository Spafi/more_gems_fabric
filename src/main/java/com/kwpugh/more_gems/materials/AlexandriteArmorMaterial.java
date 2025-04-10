package com.kwpugh.more_gems.materials;

import com.kwpugh.more_gems.MoreGems;
import com.kwpugh.more_gems.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class AlexandriteArmorMaterial implements ArmorMaterial
{
    private static int durabilityMultiplier = MoreGems.CONFIG.GENERAL.alexandriteDurabilityMultiplier;
    private static int enchantability = MoreGems.CONFIG.GENERAL.alexandriteArmorEnchantability;
    private static float toughness = MoreGems.CONFIG.GENERAL.alexandriteToughness;
    private static float knochback = MoreGems.CONFIG.GENERAL.alexandriteKnockbackResistance;

    private static int alexandriteHead = MoreGems.CONFIG.GENERAL.citrineHeadProtection;
    private static int alexandriteChest = MoreGems.CONFIG.GENERAL.citrineChestProtection;
    private static int alexandriteLeggings = MoreGems.CONFIG.GENERAL.citrineLeggingsProtection;
    private static int alexandriteBoots = MoreGems.CONFIG.GENERAL.citrineBootsProtection;

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNT = new int[]{alexandriteHead, alexandriteLeggings, alexandriteChest, alexandriteBoots};

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
        return Ingredient.ofItems(ItemInit.ALEXANDRITE);
    }

    @Override
    public String getName()
    {
        return "alexandrite";
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
