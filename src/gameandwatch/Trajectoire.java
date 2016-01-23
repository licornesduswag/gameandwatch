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
import java.util.ArrayList;

/**
 * Représente une liste de sprites à certaines coordonnées
 * @author Romain Porte (MicroJoe) <microjoe at mailoo.org>
 */
public class Trajectoire implements Serializable  {
    private ArrayList<SpritePoint> points;

    public Trajectoire() {
    }

    public Trajectoire(ArrayList<SpritePoint> points) {
        this.points = points;
    }

    public ArrayList<SpritePoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<SpritePoint> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Trajectoire{" + "points=" + points + '}';
    }
}
