/**
 * Fabled
 * studio.magemonkey.fabled.api.particle.EffectInstance
 * <p>
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2024 MageMonkeyStudio
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package studio.magemonkey.fabled.api.particle;

import studio.magemonkey.fabled.api.particle.target.EffectTarget;

import java.util.function.Consumer;

/**
 * An instanced particle effect
 */
public class EffectInstance {
    private final IParticleEffect effect;
    private final EffectTarget    target;

    private final int level;
    private       int life;
    private       int tick;
    private       int frame;

    /**
     * @param effect the effect to play
     * @param target target to play an effect for
     * @param level  the level of the effect
     */
    public EffectInstance(IParticleEffect effect, EffectTarget target, int level) {
        this.effect = effect;
        this.target = target;
        this.level = level;

        life = 0;
        tick = -1;
        frame = 0;
    }

    /**
     * @return true if the target is still valid
     */
    public boolean isValid() {
        return target.isValid() && life > 0;
    }

    public EffectInstance ifValid(Consumer<EffectInstance> consumer) {
        if (isValid()) {
            consumer.accept(this);
        }
        return this;
    }

    public EffectInstance ifValidOrElse(Consumer<EffectInstance> consumer, Consumer<EffectInstance> orElse) {
        if (isValid()) {
            consumer.accept(this);
        } else {
            orElse.accept(this);
        }
        return this;
    }

    /**
     * Extends the effect duration
     *
     * @param duration effect duration
     */
    public void extend(int duration) {
        life = Math.max(life, duration);
    }

    /**
     * Ticks the effect
     */
    public void tick() {
        tick++;
        if (tick % effect.getInterval() == 0) {
            effect.play(target.getLocation(), frame, level);
            frame++;
            tick = 0;
        }
        life--;
    }
}
