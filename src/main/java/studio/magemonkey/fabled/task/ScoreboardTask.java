/**
 * Fabled
 * studio.magemonkey.fabled.task.ScoreboardTask
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
package studio.magemonkey.fabled.task;

import studio.magemonkey.fabled.api.player.PlayerClass;
import studio.magemonkey.fabled.api.player.PlayerData;
import studio.magemonkey.fabled.hook.CitizensHook;
import studio.magemonkey.fabled.manager.ClassBoardManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTask extends BukkitRunnable {
    private final PlayerData data;

    /**
     * Prepares the task
     *
     * @param data data of player to update for
     */
    public ScoreboardTask(PlayerData data) {
        this.data = data;
    }

    /**
     * Applies the update
     */
    @Override
    public void run() {
        Player player = data.getPlayer();
        if (player == null || !player.isOnline() || player.isDead() || CitizensHook.isNPC(player))
            return;

        if (data.getMainClass() != null) {
            PlayerClass main = data.getMainClass();
            ClassBoardManager.update(data, main.getData().getPrefix(), main.getData().getPrefixColor());
        } else
            ClassBoardManager.clear(player);
    }
}
