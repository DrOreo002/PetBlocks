package com.github.shynixn.petblocks.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;

/**
 * Copyright 2017 Shynixn
 * <p>
 * Do not remove this header!
 * <p>
 * Version 1.0
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2016
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ParticleEffectBuilder implements ConfigurationSerializable {
    private String effect;
    private int amount;
    private double speed;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    private Integer material;
    private Byte data;

    /**
     * Initializes a new ParticleEffectBuilder
     */
    public ParticleEffectBuilder() {
        super();
    }

    /**
     * Initializes a new ParticleEffectBuilder with the given params
     *
     * @param effectName effect
     * @param amount     amount
     * @param speed      speed
     * @param offsetX    x
     * @param offsetY    y
     * @param offsetZ    z
     */
    public ParticleEffectBuilder(String effectName, int amount, double speed, double offsetX, double offsetY, double offsetZ) {
        super();
        if (effectName == null)
            throw new IllegalArgumentException("Effect cannot be null!");
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be less than 0");
        if (getParticleEffectFromName(effectName) == null)
            throw new IllegalArgumentException("Cannot find particleEffect for name!");
        this.effect = effectName;
        this.amount = amount;
        this.speed = speed;
        this.offsetX = (float) offsetX;
        this.offsetY = (float) offsetY;
        this.offsetZ = (float) offsetZ;
    }

    /**
     * Parses the potioneffect out of the map
     *
     * @param items items
     * @throws Exception mapParseException
     */
    public ParticleEffectBuilder(Map<String, Object> items) throws Exception {
        super();
        this.effect = (String) items.get("effect");
        this.amount = (int) items.get("amount");
        this.speed = (double) items.get("speed");
        this.offsetX = (float) (double) items.get("size.x");
        this.offsetY = (float) (double) items.get("size.y");
        this.offsetZ = ((float) (double) items.get("size.z"));
        if (items.containsKey("block.material"))
            this.material = (Integer) items.get("block.material");
        if (items.containsKey("block.damage"))
            this.data = (Byte) items.get("block.damage");
    }

    /**
     * Sets the RGB colors of the particleEffect
     *
     * @param red   red
     * @param green green
     * @param blue  blue
     * @return builder
     */
    public ParticleEffectBuilder setColor(int red, int green, int blue) {
        this.setRed(red);
        this.setBlue(blue);
        this.setGreen(green);
        return this;
    }

    /**
     * Sets the color of the particleEffect
     *
     * @param particleColor particleColor
     * @return builder
     */
    public ParticleEffectBuilder setColor(ParticleColor particleColor) {
        if (particleColor == null)
            throw new IllegalArgumentException("Color cannot be null!");
        this.setColor(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue());
        return this;
    }

    /**
     * Sets the color for note particleEffect
     *
     * @param color color
     * @return builder
     */
    public ParticleEffectBuilder setNoteColor(int color) {
        if (color > 20 || color < 0) {
            this.offsetX = 5;
        } else {
            this.offsetX = color;
        }
        return this;
    }

    /**
     * Sets the amount of particles of the particleEffect
     *
     * @param amount amount
     * @return builder
     */
    public ParticleEffectBuilder setAmount(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be less than 0");
        this.amount = amount;
        return this;
    }

    /**
     * Sets the speed of the particleEffect
     *
     * @param speed speed
     * @return builder
     */
    public ParticleEffectBuilder setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Sets the offsetX of the particleEffect
     *
     * @param offsetX offsetX
     * @return builder
     */
    public ParticleEffectBuilder setOffsetX(double offsetX) {
        this.offsetX = (float) offsetX;
        return this;
    }

    /**
     * Sets the offsetY of the particleEffect
     *
     * @param offsetY offsetY
     * @return builder
     */
    public ParticleEffectBuilder setOffsetY(double offsetY) {
        this.offsetY = (float) offsetY;
        return this;
    }

    /**
     * Sets the offsetZ of the particleEffect
     *
     * @param offsetZ offsetZ
     * @return builder
     */
    public ParticleEffectBuilder setOffsetZ(double offsetZ) {
        this.offsetZ = (float) offsetZ;
        return this;
    }

    /**
     * Sets the offset of the particleEffect
     *
     * @param offsetX offsetX
     * @param offsetY offsetY
     * @param offsetZ offsetZ
     * @return instance
     */
    public ParticleEffectBuilder setOffset(double offsetX, double offsetY, double offsetZ) {
        this.setOffsetX(offsetX);
        this.setOffsetY(offsetY);
        this.setOffsetZ(offsetZ);
        return this;
    }

    /**
     * Sets the effectType of the particleEffect
     *
     * @param name name
     * @return builder
     */
    public ParticleEffectBuilder setEffectName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null!");
        this.effect = name;
        return this;
    }

    /**
     * Sets the effectType of the particlEffect
     *
     * @param type type
     * @return builder
     */
    public ParticleEffectBuilder setEffectType(ParticleEffectType type) {
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null!");
        this.effect = type.getSimpleName();
        return this;
    }

    /**
     * Sets the blue of the RGB color
     *
     * @param blue blue
     * @return builder
     */
    public ParticleEffectBuilder setBlue(int blue) {
        this.offsetZ = blue / 255.0F;
        return this;
    }

    /**
     * Sets the red of the RGB color
     *
     * @param red red
     * @return builder
     */
    public ParticleEffectBuilder setRed(int red) {
        this.offsetX = red / 255.0F;
        if (red == 0) {
            this.offsetX = Float.MIN_NORMAL;
        }
        return this;
    }

    /**
     * Sets the green of the RGB color
     *
     * @param green green
     * @return builder
     */
    public ParticleEffectBuilder setGreen(int green) {
        this.offsetY = green / 255.0F;
        return this;
    }

    /**
     * Sets the material of the particleEffect
     *
     * @param material material
     * @return builder
     */
    public ParticleEffectBuilder setMaterial(Material material) {
        if (material != null) {
            this.material = material.getId();
        } else {
            this.material = null;
        }
        return this;
    }

    /**
     * Sets the data of the material of the particleEffect
     *
     * @param data data
     * @return builder
     */
    public ParticleEffectBuilder setData(Byte data) {
        this.data = data;
        return this;
    }

    /**
     * Returns the effect of the particleEffect
     *
     * @return effectName
     */
    public String getEffectName() {
        return this.effect;
    }

    /**
     * Returns the particleEffectType of the particleEffect
     *
     * @return effectType
     */
    public ParticleEffectType getEffectType() {
        return getParticleEffectFromName(this.effect);
    }

    /**
     * Returns the amount of particles of the particleEffect
     *
     * @return amount
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Returns the speed of the particleEffect
     *
     * @return speed
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Returns the offsetX of the particleEffect
     *
     * @return offsetX
     */
    public double getOffsetX() {
        return this.offsetX;
    }

    /**
     * Returns the offsetY of the particleEffect
     *
     * @return offsetY
     */
    public double getOffsetY() {
        return this.offsetY;
    }

    /**
     * Returns the offsetZ of the particleEffect
     *
     * @return offsetZ
     */
    public double getOffsetZ() {
        return this.offsetZ;
    }

    /**
     * Returns the RGB color blue of the particleEffect
     *
     * @return blue
     */
    public int getBlue() {
        return (int) this.offsetZ * 255;
    }

    /**
     * Returns the RGB color red of the particleEffect
     *
     * @return red
     */
    public int getRed() {
        return (int) this.offsetX * 255;
    }

    /**
     * Returns the RGB color green of the particleEffect
     *
     * @return green
     */
    public int getGreen() {
        return (int) this.offsetY * 255;
    }

    /**
     * Returns the material of the particleEffect
     *
     * @return material
     */
    public Material getMaterial() {
        if (this.material == null || Material.getMaterial(this.material) == null)
            return null;
        return Material.getMaterial(this.material);
    }

    /**
     * Returns the data of the particleEffect
     *
     * @return data
     */
    public Byte getData() {
        return this.data;
    }

    /**
     * Copies the current builder
     *
     * @return copyOfBuilder
     */
    public ParticleEffectBuilder clone() {
        final ParticleEffectBuilder particle = new ParticleEffectBuilder();
        particle.effect = this.effect;
        particle.amount = this.amount;
        particle.offsetX = this.offsetX;
        particle.offsetY = this.offsetY;
        particle.offsetZ = this.offsetZ;
        particle.speed = this.speed;
        particle.material = this.material;
        particle.data = this.data;
        return particle;
    }

    /**
     * Converts the effect to a bukkitParticle
     *
     * @param clazz Clazz to be given for compatibility
     * @param <T>   Particle
     * @return bukkitParticle
     */
    public <T extends Enum<T>> T toParticle(Class<?> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("Class cannot be null!");
        for (final Object item : clazz.getEnumConstants()) {
            final Enum<T> eItem = (Enum<T>) item;
            if (eItem.name().equalsIgnoreCase(this.effect))
                return (T) eItem;
        }
        return null;
    }

    /**
     * Returns if the particleEffect is a color particleEffect
     *
     * @return isColor
     */
    public boolean isColorParticleEffect() {
        return this.effect.equalsIgnoreCase(ParticleEffectType.SPELL_MOB.getSimpleName())
                || this.effect.equalsIgnoreCase(ParticleEffectType.SPELL_MOB_AMBIENT.getSimpleName())
                || this.effect.equalsIgnoreCase(ParticleEffectType.REDSTONE.getSimpleName())
                || this.effect.equalsIgnoreCase(ParticleEffectType.NOTE.getSimpleName());
    }

    /**
     * Returns if the particleEffect is a note particleEffect
     *
     * @return isNote
     */
    public boolean isNoteParticleEffect() {
        return this.effect.equalsIgnoreCase(ParticleEffectType.NOTE.getSimpleName());
    }

    /**
     * Returns if the particleEffect is a materialParticleEffect
     *
     * @return isMaterial
     */
    public boolean isMaterialParticleEffect() {
        return this.effect.equalsIgnoreCase(ParticleEffectType.BLOCK_CRACK.getSimpleName())
                || this.effect.equalsIgnoreCase(ParticleEffectType.BLOCK_DUST.getSimpleName())
                || this.effect.equalsIgnoreCase(ParticleEffectType.ITEM_CRACK.getSimpleName());
    }

    /**
     * Plays the effect at the given location to the given players.
     *
     * @param location location
     * @param players  players
     */
    public void apply(Location location, Collection<Player> players) {
        if (players == null)
            throw new IllegalArgumentException("Players cannot be null!");
        this.apply(location, players.toArray(new Player[players.size()]));
    }

    /**
     * Plays the effect at the given location to the given players.
     *
     * @param location location
     * @param players  players
     */
    public void apply(Location location, Player... players) {
        try {
            if (location == null)
                throw new IllegalArgumentException("Location cannot be null!");
            final Player[] playingPlayers;
            if (players.length == 0) {
                playingPlayers = location.getWorld().getPlayers().toArray(new Player[location.getWorld().getPlayers().size()]);
            } else {
                playingPlayers = players;
            }
            final float speed;
            final int amount;
            if (this.effect.equals(ParticleEffectType.REDSTONE.getSimpleName()) ||this.effect.equals(ParticleEffectType.NOTE.getSimpleName())) {
                amount = 0;
                speed = 1.0F;
            } else {
                amount = this.getAmount();
                speed = (float) this.getSpeed();
            }
            final Object enumParticle = invokeMethod(null, findClass("net.minecraft.server.VERSION.EnumParticle"), "valueOf", new Class[]{String.class}, new Object[]{this.getEffectType().name().toUpperCase()});
            int[] additionalInfo = null;
            if (this.getMaterial() != null) {
                if (this.getEffectType() == ParticleEffectBuilder.ParticleEffectType.ITEM_CRACK) {
                    additionalInfo = new int[]{this.getMaterial().getId(), this.getData()};
                } else {
                    additionalInfo = new int[]{this.getMaterial().getId(), this.getData() << 12};
                }
            }
            final Object packet = invokeConstructor(findClass("net.minecraft.server.VERSION.PacketPlayOutWorldParticles"), new Class[]{
                    enumParticle.getClass(),
                    boolean.class,
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    float.class,
                    int.class,
                    int[].class
            }, new Object[]{
                    enumParticle,
                    isLongDistance(location, players),
                    (float) location.getX(),
                    (float) location.getY(),
                    (float) location.getZ(),
                    (float) this.getOffsetX(),
                    (float) this.getOffsetY(),
                    (float) this.getOffsetZ(),
                    speed,
                    amount,
                    additionalInfo
            });
            for (final Player player : playingPlayers) {
                sendPacket(player, packet);
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to send packet.", e);
        }
    }

    /**
     * Checks if 2 builders are equal
     *
     * @param o secondBuilder
     * @return isSame
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ParticleEffectBuilder that = (ParticleEffectBuilder) o;
        return this.amount == that.amount
                && Double.compare(that.speed, this.speed) == 0
                && Double.compare(that.offsetX, this.offsetX) == 0
                && Double.compare(that.offsetY, this.offsetY) == 0
                && Double.compare(that.offsetZ, this.offsetZ) == 0
                & (this.effect != null ? this.effect.equals(that.effect) : that.effect == null)
                && Objects.equals(this.material, that.material) && (this.data != null ? this.data.equals(that.data) : that.data == null);
    }

    /**
     * Displays the builder as string
     *
     * @return string
     */
    @Override
    public String toString() {
        return "effect {" + "name " + this.effect + " amound " + this.amount + " speed " + this.speed + "}";
    }

    /**
     * Serializes the particleEffect data to be stored to the filesystem
     *
     * @return serializedContent
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("effect", this.effect.toUpperCase());
        map.put("amount", this.amount);
        map.put("speed", this.speed);
        final Map<String, Object> tmp3 = new LinkedHashMap<>();
        tmp3.put("x", this.offsetX);
        tmp3.put("y", this.offsetY);
        tmp3.put("z", this.offsetZ);
        map.put("size", tmp3);
        final Map<String, Object> tmp2 = new LinkedHashMap<>();
        if (this.material != null)
            tmp2.put("material", this.material);
        else
            tmp2.put("material", null);
        tmp2.put("damage", this.data);
        map.put("block", tmp2);
        return map;
    }

    /**
     * Returns a text of all particleEffects to let the user easily view them
     *
     * @return potionEffects
     */
    public static String getParticlesText() {
        final StringBuilder builder = new StringBuilder();
        for (final ParticleEffectType particleEffect : ParticleEffectType.values()) {
            if (builder.length() != 0) {
                builder.append(", ");
            }
            builder.append(particleEffect.getSimpleName());
        }
        return builder.toString();
    }

    /**
     * Returns the particleEffectType from name
     *
     * @param name name
     * @return particleEffectType
     */
    public static ParticleEffectType getParticleEffectFromName(String name) {
        for (final ParticleEffectType particleEffect : ParticleEffectType.values()) {
            if (name != null && particleEffect.getSimpleName().equalsIgnoreCase(name))
                return particleEffect;
        }
        return null;
    }

    /**
     * Sends a packet to the client player
     *
     * @param player player
     * @param packet packet
     * @throws ClassNotFoundException    exception
     * @throws IllegalAccessException    exception
     * @throws NoSuchMethodException     exception
     * @throws InvocationTargetException exception
     * @throws NoSuchFieldException      exception
     */

    private static void sendPacket(Player player, Object packet) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        final Object craftPlayer = findClass("org.bukkit.craftbukkit.VERSION.entity.CraftPlayer").cast(player);
        final Object entityPlayer = invokeMethod(craftPlayer, craftPlayer.getClass(), "getHandle", new Class[]{}, new Object[]{});
        final Field field = entityPlayer.getClass().getDeclaredField("playerConnection");
        field.setAccessible(true);
        final Object connection = field.get(entityPlayer);
        invokeMethod(connection, connection.getClass(), "sendPacket", new Class[]{packet.getClass().getInterfaces()[0]}, new Object[]{packet});
    }

    /**
     * Invokes a constructor by the given parameters
     *
     * @param clazz      clazz
     * @param paramTypes paramTypes
     * @param params     params
     * @return instance
     * @throws NoSuchMethodException     exception
     * @throws IllegalAccessException    exception
     * @throws InvocationTargetException exception
     * @throws InstantiationException    exception
     */
    private static Object invokeConstructor(Class<?> clazz, Class[] paramTypes, Object[] params) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Constructor constructor = clazz.getDeclaredConstructor(paramTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }

    /**
     * Invokes a method by the given parameters
     *
     * @param instance   instance
     * @param clazz      clazz
     * @param name       name
     * @param paramTypes paramTypes
     * @param params     params
     * @return returnedObject
     * @throws InvocationTargetException exception
     * @throws IllegalAccessException    exception
     * @throws NoSuchMethodException     exception
     */
    private static Object invokeMethod(Object instance, Class<?> clazz, String name, Class[] paramTypes, Object[]
            params) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        final Method method = clazz.getDeclaredMethod(name, paramTypes);
        method.setAccessible(true);
        return method.invoke(instance, params);
    }

    /**
     * Finds a class regarding of the server Version
     *
     * @param name name
     * @return clazz
     * @throws ClassNotFoundException exception
     */
    private static Class<?> findClass(String name) throws ClassNotFoundException {
        return Class.forName(name.replace("VERSION", Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]));
    }

    /**
     * Checks if longDistance attribute is necessary
     *
     * @param location location
     * @param players  players
     * @return isNecessary
     */
    private static boolean isLongDistance(Location location, Player[] players) {
        for (final Player player : players) {
            if (location.getWorld().getName().equals(player.getLocation().getWorld().getName())
                    && player.getLocation().distanceSquared(location) > 65536) {
                return true;
            }
        }
        return false;
    }

    /**
     * ParticleColors
     */
    public enum ParticleColor {
        BLACK(0, 0, 0),
        DARK_BLUE(0, 0, 170),
        DARK_GREEN(0, 170, 0),
        DARK_AQUA(0, 170, 170),
        DARK_RED(170, 0, 0),
        DARK_PURPLE(170, 0, 170),
        GOLD(255, 170, 0),
        GRAY(170, 170, 170),
        DARK_GRAY(85, 85, 85),
        BLUE(85, 85, 255),
        GREEN(85, 255, 85),
        AQUA(85, 255, 255),
        RED(255, 85, 85),
        LIGHT_PURPLE(255, 85, 255),
        YELLOW(255, 255, 85),
        WHITE(255, 255, 255);

        private final int red;
        private final int green;
        private final int blue;

        /**
         * Initializes a new particleColor
         *
         * @param red   red
         * @param green green
         * @param blue  blue
         */
        ParticleColor(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        /**
         * Returns the RGB value red
         *
         * @return red
         */
        public int getRed() {
            return this.red;
        }

        /**
         * Returns the RGB value green
         *
         * @return green
         */
        public int getGreen() {
            return this.green;
        }

        /**
         * Returns the RGB value blue
         *
         * @return blue
         */
        public int getBlue() {
            return this.blue;
        }
    }

    /**
     * ParticleEffectTypes
     */
    public enum ParticleEffectType {
        EXPLOSION_NORMAL("explode", 0),
        EXPLOSION_LARGE("largeexplode", 1),
        EXPLOSION_HUGE("hugeexplosion", 2),
        FIREWORKS_SPARK("fireworksSpark", 3),
        WATER_BUBBLE("bubble", 4),
        WATER_SPLASH("splash", 5),
        WATER_WAKE("wake", 6),
        SUSPENDED("suspended", 7),
        SUSPENDED_DEPTH("depthsuspend", 8),
        CRIT("crit", 9),
        CRIT_MAGIC("magicCrit", 10),
        SMOKE_NORMAL("smoke", 11),
        SMOKE_LARGE("largesmoke", 12),
        SPELL("spell", 13),
        SPELL_INSTANT("instantSpell", 14),
        SPELL_MOB("mobSpell", 15),
        SPELL_MOB_AMBIENT("mobSpellAmbient", 16),
        SPELL_WITCH("witchMagic", 17),
        DRIP_WATER("dripWater", 18),
        DRIP_LAVA("dripLava", 19),
        VILLAGER_ANGRY("angryVillager", 20),
        VILLAGER_HAPPY("happyVillager", 21),
        TOWN_AURA("townaura", 22),
        NOTE("note", 23),
        PORTAL("portal", 24),
        ENCHANTMENT_TABLE("enchantmenttable", 25),
        FLAME("flame", 26),
        LAVA("lava", 27),
        FOOTSTEP("footstep", 28),
        CLOUD("cloud", 29),
        REDSTONE("reddust", 30),
        SNOWBALL("snowballpoof", 31),
        SNOW_SHOVEL("snowshovel", 32),
        SLIME("slime", 33),
        HEART("heart", 34),
        BARRIER("barrier", 35),
        ITEM_CRACK("iconcrack", 36),
        BLOCK_CRACK("blockcrack", 37),
        BLOCK_DUST("blockdust", 38),
        WATER_DROP("droplet", 39),
        ITEM_TAKE("take", 40),
        MOB_APPEARANCE("mobappearance", 41),
        DRAGON_BREATH("dragonbreath", 42),
        END_ROD("endRod", 43),
        DAMAGE_INDICATOR("damageIndicator", 44),
        SWEEP_ATTACK("sweepAttack", 45),
        FALLING_DUST("fallingdust", 46),
        TOTEM("totem", 47),
        SPIT("spit", 48);

        private final String simpleName;
        private final int id;

        /**
         * Initializes a new particleEffectType
         *
         * @param name name
         * @param id   id
         */
        ParticleEffectType(String name, int id) {
            this.simpleName = name;
            this.id = id;
        }

        /**
         * Returns the id of the particleEffectType
         *
         * @return id
         */
        public int getId() {
            return this.id;
        }

        /**
         * Returns the name of the particleEffectType
         *
         * @return name
         */
        public String getSimpleName() {
            return this.simpleName;
        }
    }
}