package org.xiyu.spartanweaponryunofficial.capability;

import net.minecraft.world.item.ItemStack;

/**
 * Slot-based ammo storage of a quiver.
 */
public interface IQuiverItemHandler {
    int getSlots();

    ItemStack getStackInSlot(int slot);

    void setStackInSlot(int slot, ItemStack stack);

    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    default boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    /**
     * Resizes the stack list to the specified size. NOTE: If reducing the size of the stack list, any items over the specified size will be LOST
     */
    void resize(int size);

    boolean isEmpty();
}
