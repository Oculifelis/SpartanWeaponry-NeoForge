package org.xiyu.spartanweaponryunofficial.capability;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.xiyu.spartanweaponryunofficial.item.QuiverBaseItem;
import org.xiyu.spartanweaponryunofficial.util.ItemStackDataHelper;

public class QuiverItemStackHandler implements IQuiverItemHandler {
    private final ItemStack quiverStack;
    protected NonNullList<ItemStack> stacks;

    public QuiverItemStackHandler(ItemStack stack, int size) {
        this.quiverStack = stack;
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
        CompoundTag tag = ItemStackDataHelper.getTag(stack).getCompoundOrEmpty(QuiverBaseItem.NBT_AMMO);
        if (!tag.isEmpty())
            this.deserialize(TagValueInput.create(ProblemReporter.DISCARDING, getRegistryAccess(), tag));
    }

    @Override
    public int getSlots() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);
        return this.stacks.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Item.ABSOLUTE_MAX_STACK_SIZE;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        if (!isItemValid(slot, stack))
            return stack;
        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);
        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing))
                return stack;
            limit -= existing.getCount();
        }
        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;
        if (!simulate) {
            if (existing.isEmpty())
                this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            else
                existing.grow(reachedLimit ? limit : stack.getCount());
            onContentsChanged(slot);
        }
        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;
        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);
        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return existing;
            }
            return existing.copy();
        }
        if (!simulate) {
            this.stacks.set(slot, existing.copyWithCount(existing.getCount() - toExtract));
            onContentsChanged(slot);
        }
        return existing.copyWithCount(toExtract);
    }

    /**
     * Resizes the stack list to the specified size. NOTE: If reducing the size of the stack list, any items over the specified size will be LOST
     */
    @Override
    public void resize(int size) {
        NonNullList<ItemStack> newStacks = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < newStacks.size(); i++) {
            if (i < this.stacks.size())
                newStacks.set(i, this.stacks.get(i));
        }
        this.stacks = newStacks;
        this.syncToStack();
    }

    protected void onContentsChanged(int slot) {
        this.syncToStack();
    }

    // Same layout the older handler wrote ("Items" with slots, plus "Size"), so existing quivers keep their ammo.
    public void serialize(ValueOutput output) {
        ValueOutput.TypedOutputList<ItemStackWithSlot> itemList = output.list("Items", ItemStackWithSlot.CODEC);
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (!stack.isEmpty())
                itemList.add(new ItemStackWithSlot(i, stack));
        }
        output.putInt("Size", this.stacks.size());
    }

    public void deserialize(ValueInput input) {
        this.stacks = NonNullList.withSize(input.getIntOr("Size", this.stacks.size()), ItemStack.EMPTY);
        input.listOrEmpty("Items", ItemStackWithSlot.CODEC).forEach(slot -> {
            if (slot.isValidInContainer(this.stacks.size()))
                this.stacks.set(slot.slot(), slot.stack());
        });
    }

    private void syncToStack() {
        ItemStackDataHelper.updateTag(this.quiverStack, tag -> {
            TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, getRegistryAccess());
            this.serialize(output);
            tag.put(QuiverBaseItem.NBT_AMMO, output.buildResult());
        });
    }

    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.stacks.size() + ")");
    }

    private static RegistryAccess getRegistryAccess() {
        return RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (stack.isEmpty())
                return false;
        }
        return true;
    }
}
