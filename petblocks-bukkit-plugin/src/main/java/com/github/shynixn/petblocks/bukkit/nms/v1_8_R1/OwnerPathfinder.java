package com.github.shynixn.petblocks.bukkit.nms.v1_8_R1;

import com.github.shynixn.petblocks.api.business.entity.PetBlock;
import com.github.shynixn.petblocks.bukkit.logic.business.configuration.ConfigPet;
import com.github.shynixn.petblocks.bukkit.nms.helper.PetBlockHelper;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.PathEntity;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class OwnerPathfinder extends PathfinderGoal {
    private final EntityInsentient entity;
    private PathEntity path;
    private final Player player;
    private int counter;
    private int counter2;
    private final PetBlock petBlock;

    public OwnerPathfinder(EntityInsentient entitycreature, PetBlock petBlock) {
        super();
        this.entity = entitycreature;
        this.player = (Player) petBlock.getPlayer();
        this.petBlock = petBlock;
    }

    @Override
    public boolean a() {
        if (this.player == null) {
            return this.path != null;
        }
        if (!this.entity.getWorld().getWorldData().getName().equals(this.player.getWorld().getName())) {
            this.entity.getBukkitEntity().teleport(this.player.getLocation());
        } else if (this.entity.getBukkitEntity().getLocation().distance(this.player.getLocation()) > ConfigPet.getInstance().getBlocksAwayFromPlayer()) {
            this.counter2 = PetBlockHelper.afraidWaterEffect(petBlock, this.counter2);
            final Location targetLocation = this.player.getLocation();
            this.entity.getNavigation().m();
            this.entity.getNavigation();
            this.path = this.entity.getNavigation().a(targetLocation.getX() + 1, targetLocation.getY(), targetLocation.getZ() + 1);
            this.entity.getNavigation();
            if (this.entity.getBukkitEntity().getLocation().distance(this.player.getLocation()) > ConfigPet.getInstance().getFollow_maxRangeTeleport())
                this.entity.getBukkitEntity().teleport(this.player.getLocation());
            if (Math.abs(this.entity.getBukkitEntity().getLocation().getY() - targetLocation.getY()) >= 2) {
                this.counter++;
            } else {
                this.counter = 0;
            }
            if (this.counter > 5) {
                this.entity.getBukkitEntity().setVelocity(new Vector(0.1, (float) ConfigPet.getInstance().getModifier_petclimbing() * 0.1, 0.1));
                this.counter = 0;
            }
            if (this.path != null) {
                this.c();
            }
        }
        return this.path != null;
    }

    @Override
    public void c() {
        this.entity.getNavigation().a(this.path, 1D);
    }
}
