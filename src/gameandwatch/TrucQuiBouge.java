/*
 * The MIT License
 *
 * Copyright 2016 Romain Porte (MicroJoe) <microjoe at mailoo.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gameandwatch;

import java.io.Serializable;

/**
 * Un objet qui peut se déplacer dans le jeu
 * @author Romain Porte (MicroJoe) <microjoe at mailoo.org>
 */
public class TrucQuiBouge implements Serializable  {
    private Trajectoire trajectoire;
    private transient int position = 0;

    public TrucQuiBouge() {
    }

    public TrucQuiBouge(Trajectoire trajectoire) {
        this.trajectoire = trajectoire;
    }

    public Trajectoire getTrajectoire() {
        return trajectoire;
    }

    public void setTrajectoire(Trajectoire trajectoire) {
        this.trajectoire = trajectoire;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Déplace l'objet à sa prochaine position
     */
    public void nextPosition() {
        if (position < trajectoire.getPoints().size() - 1) {
            position++;
        }
    }
    
    /**
     * Déplace l'objet à sa position précédente
     */
    public void prevPosition() {
        if (position > 0) {
            position--;
        }
    }

    @Override
    public String toString() {
        return "TrucQuiBouge{" + "trajectoire=" + trajectoire + '}';
    }
}
